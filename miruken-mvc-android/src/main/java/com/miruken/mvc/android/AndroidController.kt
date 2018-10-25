package com.miruken.mvc.android

import android.databinding.ViewDataBinding
import android.view.View
import com.miruken.mvc.Controller

open class AndroidController : Controller() {
    protected fun show(
            layoutId:    Int,
            viewModelId: Int,
            init:      (View.(binding: ViewDataBinding) -> Unit)? = null
    ) = show(ViewLayout(layoutId, viewModelId, init))

    protected fun <B: ViewDataBinding> bind(
            layoutId:    Int,
            viewModelId: Int,
            init:      (View.(binding: B) -> Unit)? = null
    ) = show(ViewLayout(layoutId, viewModelId, init))
}