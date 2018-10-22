package com.miruken.mvc.android

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miruken.mvc.Controller

open class AndroidController : Controller() {
    protected fun show(
            layoutId:  Int,
            bindingId: Int? = null,
            init:      (View.() -> Unit)? = null
    ) = show(ViewLayout(layoutId, bindingId, init))

    //Expecting this to go away
    protected fun <T: ViewDataBinding>bind(view: Int, parent: ViewGroup) : T {
        val layoutInflater = parent.context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater
        return DataBindingUtil
                .inflate(layoutInflater, view, parent, true)
    }
}