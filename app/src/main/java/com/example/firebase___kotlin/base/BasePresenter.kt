package com.example.movies.utlis.base


interface BasePresenter<T> {
    fun onStart()
    fun onStop()
    fun setView(view: T?)
}