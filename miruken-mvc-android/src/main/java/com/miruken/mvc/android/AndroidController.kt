package com.miruken.mvc.android

import androidx.databinding.ViewDataBinding
import android.view.View
import com.miruken.callback.Handling
import com.miruken.mvc.Controller

open class AndroidController : Controller() {
    protected fun showR(
            layoutId: Int,
            init:     (View.() -> Unit)? = null
    ) = show(ViewLayout(layoutId, init))

    protected fun showR(
            handler:  Handling,
            layoutId: Int,
            init:     (View.() -> Unit)? = null
    ) = show(handler, ViewLayout(layoutId, init))

    protected fun showR(
            layoutId:    Int,
            viewModelId: Int,
            init:        (View.(binding: ViewDataBinding) -> Unit)? = null
    ) = show(ViewBindingLayout(layoutId, viewModelId, init))

    protected fun showR(
            handler:     Handling,
            layoutId:    Int,
            viewModelId: Int,
            init:        (View.(binding: ViewDataBinding) -> Unit)? = null
    ) = show(handler, ViewBindingLayout(layoutId, viewModelId, init))

    protected fun <B: ViewDataBinding> show(
            layoutId:    Int,
            viewModelId: Int,
            init:        (View.(binding: B) -> Unit)? = null
    ) = show(ViewBindingLayout(layoutId, viewModelId, init))

    protected fun <B: ViewDataBinding> show(
            handler:     Handling,
            layoutId:    Int,
            viewModelId: Int,
            init:        (View.(binding: B) -> Unit)? = null
    ) = show(handler, ViewBindingLayout(layoutId, viewModelId, init))
}