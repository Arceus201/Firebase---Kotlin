package com.example.movies.utlis.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment <T : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater)->T
) : Fragment(){
    val binding: T by lazy {
        bindingInflater.invoke(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initView(savedInstanceState)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        handleEvent()
    }
    abstract fun initView( savedInstanceState: Bundle?)

    abstract fun initData()

    abstract fun handleEvent()

}