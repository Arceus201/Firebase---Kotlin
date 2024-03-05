package com.example.firebase___kotlin.tab1
import java.lang.Math
import java.math.BigDecimal
import kotlin.math.*

class CalculatorModel {

   var input: String = ""

    fun appendInput(text: String) {
        input += text
    }
    fun deleteLastInput2() {
        if (input.isNotEmpty()) {
            val lastChar = input.last()

            if (lastChar == '(' || lastChar == ')') {
                // Kiểm tra xem ký tự cuối cùng là mở hoặc đóng ngoặc
                // Xóa cả hàm nếu là trường hợp đặc biệt
                var index = input.length - 1
                while (index >= 0 && input[index] != '(') {
                    index--
                }
                input = if (index >= 0) {
                    input.substring(0, index)
                } else {
                    ""
                }
            } else if (lastChar.isLetter() && input.length >= 3) {
                // Kiểm tra nếu ký tự cuối cùng là chữ cái và có ít nhất 3 ký tự trong input
                val lastThreeChars = input.takeLast(3)
                val secondLastChar = input[input.length - 2]
                if ((lastThreeChars == "sin" || lastThreeChars == "cos" ||
                            lastThreeChars == "tan" || lastThreeChars == "log" ||
                            lastThreeChars == "ln" || lastThreeChars == "√(" || lastThreeChars == "^(") ||
                    (secondLastChar == '^' && lastChar == '(')) {
                    // Xóa cả hàm nếu là các trường hợp đặc biệt
                    var index = input.length - 1
                    while (index >= 0 && input[index] != '(') {
                        index--
                    }
                    input = if (index >= 0) {
                        input.substring(0, index)
                    } else {
                        ""
                    }
                } else {
                    // Nếu không phải trường hợp đặc biệt, chỉ cần xóa một ký tự
                    input = input.substring(0, input.length - 1)
                }
            } else {
                // Nếu không phải là các trường hợp trên, chỉ cần xóa một ký tự
                input = input.substring(0, input.length - 1)
            }
        }
    }
    fun deleteLastInput() {
        if (input.isNotEmpty()) {
            val lastChar = input.last()

            // Kiểm tra xem ký tự cuối cùng có phải là một trong các hàm có 1 chữ số hoặc các trường hợp đặc biệt khác không
            if (lastChar == '(' || lastChar == ')' || lastChar == 'n' || lastChar == '^') {
                // Kiểm tra các trường hợp đặc biệt như sin(, cos(, tan(, log(, ln(, ^(-1)
                val secondLastChar = if (input.length >= 2) input[input.length - 2] else ' '
                if ((secondLastChar == 'n' && lastChar == 'i') || (secondLastChar == 's' && lastChar == 'o') ||
                    (secondLastChar == 'c' && lastChar == 'o') || (secondLastChar == 't' && lastChar == 'a') ||
                    (secondLastChar == 'g' && lastChar == 'o') || (secondLastChar == 'n' && lastChar == 'l') ||
                    (secondLastChar == '^' && lastChar == '(')) {
                    // Xóa cả hàm nếu là các trường hợp đặc biệt
                    var index = input.length - 1
                    while (index >= 0 && input[index] != '(') {
                        index--
                    }
                    input = if (index >= 0) {
                        input.substring(0, index)
                    } else {
                        ""
                    }
                } else {
                    // Nếu không phải các trường hợp đặc biệt, chỉ cần xóa 1 ký tự
                    input = input.substring(0, input.length - 1)
                }
            } else {
                // Nếu ký tự cuối cùng không phải là một trong các hàm có 1 chữ số hoặc các trường hợp đặc biệt khác, chỉ cần xóa 1 ký tự
                input = input.substring(0, input.length - 1)
            }
        }
    }



    fun clearAll() {
        input = ""
    }

    fun evaluate(): String {
        try {
            var expression = input.replace("sin", "Math.sin")
                .replace("cos", "Math.cos")
                .replace("tan", "Math.tan")
                .replace("log", "Math.log10")
                .replace("ln", "Math.log")
                .replace("^", ".pow")
                .replace("√", "Math.sqrt")
                .replace("!", ".factorial()")
                .replace("e", "Math.E.toString()")
                .replace("π", "Math.PI.toString()")
                .replace("x", "*")
                .replace("÷", "/")
            expression = addMissingParenthesis(expression)

            val result = BigDecimal(eval(expression).toString()).stripTrailingZeros().toPlainString()
            clearAll()
            input = result
            return result
        } catch (e: Exception) {
            clearAll()
            return "Error"
        }
    }

    private fun addMissingParenthesis(expression: String): String {
        var balance = 0
        for (c in expression) {
            if (c == '(') balance++
            if (c == ')') balance--
        }
        return expression + ")".repeat(balance)
    }

    private fun Double.factorial(): Double {
        if (this < 0) throw IllegalArgumentException("Factorial is not defined for negative numbers")
        var result = 1.0
        for (i in 2..this.toInt()) {
            result *= i
        }
        return result
    }

    private fun eval(expression: String): Double {
        return object : Any() {
            var pos = -1
            var ch = 0

            fun nextChar() {
                ch = if (++pos < expression.length) expression[pos].code else -1
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
                if (pos < expression.length) throw RuntimeException("Unexpected: " + ch.toChar())
                return x
            }

            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    if (eat('+'.code)) x += parseTerm()
                    else if (eat('-'.code)) x -= parseTerm()
                    else return x
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    if (eat('*'.code)) x *= parseFactor()
                    else if (eat('/'.code)) x /= parseFactor()
                    else return x
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.code)) return parseFactor()
                if (eat('-'.code)) return -parseFactor()

                var x: Double
                val startPos = pos
                if (eat('('.code)) {
                    x = parseExpression()
                    eat(')'.code)
                } else if (ch in '0'.code..'9'.code || ch == '.'.code) {
                    while (ch in '0'.code..'9'.code || ch == '.'.code) nextChar()
                    x = expression.substring(startPos, pos).toDouble()
                } else if (ch >= 'a'.code && ch <= 'z'.code) {
                    while (ch >= 'a'.code && ch <= 'z'.code) nextChar()
                    val func = expression.substring(startPos, pos)
                    x = parseFactor()
                    x = when (func) {
                        "sqrt" -> sqrt(x)
                        "sin" -> sin(Math.toRadians(x))
                        "cos" -> cos(Math.toRadians(x))
                        "tan" -> tan(Math.toRadians(x))
                        "log" -> log10(x)
                        "ln" -> ln(x)
                        "!" -> x.factorial()
                        "E" -> Math.E
                        "PI" -> Math.PI
                        else -> throw RuntimeException("Unknown function: $func")
                    }
                } else {
                    throw RuntimeException("Unexpected: " + ch.toChar())
                }

                if (eat('^'.code)) x = x.pow(parseFactor())
                return x
            }
        }.parse()
    }
}
