package com.miruken.mvc.android

import com.miruken.mvc.view.Viewing
import com.miruken.mvc.view.ViewPolicy
import com.miruken.mvc.view.ViewingRegion

class ViewLayout(
        val layoutId: Int,
        val bindingId: Int? = null
) : Viewing {
    override var viewModel: Any? = null

    override val policy by lazy { ViewPolicy(this) }

    override fun display(region: ViewingRegion) =
            region.show(this)
}