package com.example.firebase___kotlin.tab1

interface Tab1FragmentContract {
    interface View{
        fun onShowResult(result: String, input: String)

        fun updateInput(input: String)
        fun showResult(result: String)
    }

    interface Presenter{
        fun handleCalculations(input: String)

        fun onNumberClick(number: String)
        fun onClearAllClick()
        fun onDeleteClick()
        fun onEvaluateClick()
    }
}