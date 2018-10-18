package com.miruken.mvc.android

import android.view.View
import com.miruken.mvc.Controller

open class AndroidController : Controller() {
    protected fun show(
            layoutId:  Int,
            bindingId: Int? = null,
            init:      (View.() -> Unit)? = null
    ) = show(ViewLayout(layoutId, bindingId, init))
}