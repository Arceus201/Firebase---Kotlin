package com.example.firebase___kotlin.tab1

import kotlin.math.*

class Tab1FragmentPresenter(private val view: Tab1FragmentContract.View) :
    Tab1FragmentContract.Presenter
{
    override fun handleCalculations(inputDisplay: String) {
        val input = inputDisplay.replace('÷', '/').replace('×', '*')
        var result: Any = ""
        try {
            result = object : Any() {
                var pos = -1
                var ch = 0

                fun nextChar() {
                    ch = if (++pos < input.length) input[pos].code else -1
                }

                fun eat(charToEat: Int): Boolean {
                    while (ch == ' '.code) nextChar()
                    if (ch == charToEat) {
                        nextChar()
                        return true
                    }
                    return false
                }

                fun parse(): Double {
                    nextChar()
                    val x = parseExpression()
                    if (pos < input.length) throw RuntimeException("Unexpected: " + ch.toChar())
                    return x
                }

                // Grammar:
                // expression = term | expression `+` term | expression `-` term
                // term = factor | term `*` factor | term `/` factor
                // factor = `+` factor | `-` factor | `(` expression `)`
                //        | number | functionName factor | factor `^` factor
                fun parseExpression(): Double {
                    var x = parseTerm()
                    while (true) {
                        if (eat('+'.code)) x += parseTerm() // addition
                        else if (eat('-'.code)) x -= parseTerm() // subtraction
                        else return x
                    }
                }

                fun parseTerm(): Double {
                    var x = parseFactor()
                    while (true) {
                        if (eat('*'.code)) x *= parseFactor() // multiplication
                        else if (eat('/'.code)) x /= parseFactor() // division
                        else return x
                    }
                }

                fun parseFactor(): Double {
                    if (eat('+'.code)) return parseFactor() // unary plus
                    if (eat('-'.code)) return -parseFactor() // unary minus
                    var x: Double
                    val startPos = pos
                    if (eat('('.code)) { // parentheses
                        x = parseExpression()
                        eat(')'.code)
                    } else if (ch >= '0'.code && ch <= '9'.code || ch == '.'.code) { // numbers
                        while (ch >= '0'.code && ch <= '9'.code || ch == '.'.code) nextChar()
                        x = input.substring(startPos, pos).toDouble()
                    } else if (ch >= 'a'.code && ch <= 'z'.code) { // functions
                        while (ch >= 'a'.code && ch <= 'z'.code) nextChar()
                        val func = input.substring(startPos, pos)
                        x = parseFactor()
                        x =
                            when (func) {
                                "sqrt" -> sqrt(x)
                                "sin" -> sin(
                                    Math.toRadians(
                                        x
                                    )
                                )
                                "cos" -> cos(
                                    Math.toRadians(x)
                                )
                                "tan" -> tan(Math.toRadians(x))
                                "log" -> log10(
                                    x
                                )
                                "ln" -> ln(x)
                                else -> throw RuntimeException(
                                    "Unknown function: $func"
                                )
                            }
                    } else {
                        throw RuntimeException("Unexpected: " + ch.toChar())
                    }
                    if (eat('^'.code)) x = x.pow(parseFactor())
                    return x
                }
            }.parse()
        }catch (e: Exception) {
            result = "Error:"
        }
        view.onShowResult(result.toString(),inputDisplay)
    }

    private val model = CalculatorModel()

    override fun onNumberClick(number: String) {
        model.appendInput(number)
        view.updateInput(model.input)
    }

    override fun onClearAllClick() {
        model.clearAll()
        view.updateInput(model.input)
    }

    override fun onDeleteClick() {
        model.deleteLastInput()
        view.updateInput(model.input)
    }

    override fun onEvaluateClick() {
        val result = model.evaluate()
        view.showResult(result)
    }
}