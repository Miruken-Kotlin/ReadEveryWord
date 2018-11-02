package com.miruken.mvc.android

import androidx.databinding.ViewDataBinding
import android.view.View
import com.miruken.mvc.Controller

open class AndroidController : Controller() {
    protected fun show(
            layoutId: Int,
            init:     (View.() -> Unit)? = null
    ) = show(ViewLayout(layoutId, init))

    protected fun show(
            layoutId:    Int,
            viewModelId: Int,
            init:        (View.(binding: ViewDataBinding) -> Unit)? = null
    ) = show(ViewBindingLayout(layoutId, viewModelId, init))

    protected fun <B: ViewDataBinding> showb(
            layoutId:    Int,
            viewModelId: Int,
            init:        (View.(binding: B) -> Unit)? = null
    ) = show(ViewBindingLayout(layoutId, viewModelId, init))
}