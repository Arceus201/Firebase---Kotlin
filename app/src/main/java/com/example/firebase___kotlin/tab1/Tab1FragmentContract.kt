package com.example.firebase___kotlin.tab1

interface Tab1FragmentContract {
    interface View{
        fun onShowResult(result: String, input: String)
    }

    interface Presenter{
        fun handleCalculations(input: String)
    }
}