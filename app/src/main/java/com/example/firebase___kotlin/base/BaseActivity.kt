package com.example.firebase___kotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

//abc
abstract class BaseActivity<T : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater)->T
) : AppCompatActivity(){
    val binding: T by lazy {
        bindingInflater.invoke(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initData()
        handleEvent()
    }

    abstract fun initView()

    abstract fun initData()

    abstract fun handleEvent()
}