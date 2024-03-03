package com.example.firebase___kotlin.tab1

import android.os.Bundle
import com.example.firebase___kotlin.databinding.FragmentTab1Binding
import com.example.movies.utlis.base.BaseFragment
import kotlin.math.*


class Tab1Fragment : BaseFragment<FragmentTab1Binding>(
    FragmentTab1Binding::inflate
) ,Tab1FragmentContract.View{
    private lateinit var presenter: Tab1FragmentPresenter
    private var pi = "3.14159265"
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        presenter = Tab1FragmentPresenter(this)
    }

    override fun handleEvent() {
        binding.apply {
            buttonZero.setOnClickListener {
                showInput(textCalculator.text.toString() + "0")
            }

            buttonOne.setOnClickListener {
                showInput(textCalculator.text.toString() + "1")
            }

            buttonTwo.setOnClickListener {
                showInput(textCalculator.text.toString() + "2")
            }

            buttonThree.setOnClickListener {
                showInput(textCalculator.text.toString() + "3")
            }

            buttonFour.setOnClickListener {
                showInput(textCalculator.text.toString() + "4")
            }

            buttonFive.setOnClickListener {
                showInput(textCalculator.text.toString() + "5")
            }

            buttonSix.setOnClickListener {
                showInput(textCalculator.text.toString() + "6")
            }

            buttonSeven.setOnClickListener {
                showInput(textCalculator.text.toString() + "7")
            }

            buttonEight.setOnClickListener {
                showInput(textCalculator.text.toString() + "8")
            }

            buttonNine.setOnClickListener {
                showInput(textCalculator.text.toString() + "9")
            }

            bdot.setOnClickListener {
                showInput(textCalculator.text.toString() + ".")
            }
            buttonAC.setOnClickListener {
                showInput("")
                showHistory("")
            }
            buttonBack.setOnClickListener {
                var s: String = textCalculator.text.toString()
                s = s.substring(0, s.length - 1)
                showInput(s)
            }
            buttonPlus.setOnClickListener {
                showInput(textCalculator.text.toString() + "+")
            }
            buttonSubtraction.setOnClickListener {
                showInput(textCalculator.text.toString() + "-")
            }
            buttonMultiplication.setOnClickListener {
                showInput(textCalculator.text.toString() + "x")
            }
            buttonDivision.setOnClickListener {
                showInput(textCalculator.text.toString() + "÷")
            }
            buttonSquare.setOnClickListener {
                val `val`: String = textCalculator.text.toString()
                val r = sqrt(`val`.toDouble())
                showInput(r.toString())
            }

            buttonLeftParenthesis.setOnClickListener {
                showInput(textCalculator.text.toString() + "(")
            }
            buttonRightParenthesis.setOnClickListener {
                showInput(textCalculator.text.toString() + ")")
            }
            buttonPi.setOnClickListener {
                showHistory(buttonPi.text.toString())
                showInput(textCalculator.text.toString() + pi)
            }
            buttonSin.setOnClickListener {
                showInput(textCalculator.text.toString() + "sin")
            }
            buttonCos.setOnClickListener {
                showInput(textCalculator.text.toString() + "cos")
            }
            buttonTan.setOnClickListener {
                showInput(textCalculator.text.toString() + "tan")
            }
            buttonInverse.setOnClickListener {
                showInput(textCalculator.text.toString() + "^" + "(-1)")
            }
            buttonFactorial.setOnClickListener {
                val `val`: Int = textCalculator.text.toString().toInt()
                val fact = factorial(`val`)
                showInput(fact.toString())
                showHistory("$`val`!")
            }
            buttonSquareInv.setOnClickListener {
                val d: Double = textCalculator.text.toString().toDouble()
                val square = d * d
                showInput(square.toString())
                showHistory("$d²")
            }
            buttonNaturalLogarithm.setOnClickListener {
                showInput(textCalculator.text.toString() + "ln")
            }
            buttonLogarithm.setOnClickListener {
                showInput(textCalculator.text.toString() + "log")
            }
            buttonEqual.setOnClickListener {
                presenter.handleCalculations(binding.textCalculator.text.toString())
            }
        }

    }

    private fun showInput(input: String) {
        binding.textCalculator.text = input
    }

    private fun showHistory(input: String) {
        binding.textHistory.text = input
    }


    private fun factorial(n: Int): Int {
        return if (n == 1 || n == 0) 1 else n * factorial(n - 1)
    }


    override fun onShowResult(result: String, input: String) {
        showInput(result)
        showHistory(input)
    }

}