package com.miruken.mvc.android

import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.view.View
import com.miruken.mvc.view.ViewPolicy
import com.miruken.mvc.view.Viewing
import com.miruken.mvc.view.ViewingRegion

class ViewLayout(
        @IdRes @LayoutRes val layoutId: Int,
        @IdRes val bindingId: Int? = null,
        val initView:  (View.() -> Unit)? = null
) : Viewing {
    override var viewModel: Any? = null

    override val policy by lazy { ViewPolicy(this) }

    override fun display(region: ViewingRegion) =
            region.show(this)
}
