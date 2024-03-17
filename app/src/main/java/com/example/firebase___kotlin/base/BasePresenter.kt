package com.example.firebase___kotlin.base


interface BasePresenter<T> {
    fun onStart()
    fun onStop()
    fun setView(view: T?)
}