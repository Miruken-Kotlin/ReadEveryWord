package com.miruken.mvc.android

import android.content.Context
import android.widget.RelativeLayout
import com.miruken.callback.Handling
import com.miruken.callback.notHandled
import com.miruken.callback.requireComposer
import com.miruken.callback.resolve
import com.miruken.mvc.Controller
import com.miruken.mvc.Navigation
import com.miruken.mvc.dependsOn
import com.miruken.mvc.view.Viewing
import com.miruken.mvc.view.ViewingLayer
import com.miruken.mvc.view.ViewPolicy
import com.miruken.mvc.view.ViewingRegion
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

abstract class ViewContainer(
        context: Context
) : RelativeLayout(context), ViewingRegion, Viewing {

    override var viewModel: Any? = null

    override val policy by lazy { ViewPolicy(this) }

    override fun display(
            region: ViewingRegion
    ) = region.show(this)

    override fun view(
            viewKey: Any,
            init: (Viewing.() -> Unit)?
    ): Viewing {
        val composer = requireComposer()

        val view = when (viewKey) {
            is KType -> {
                val clazz = viewKey.jvmErasure
                if (clazz.java.isInterface) {
                    if (!this::class.isSubclassOf(clazz)) {
                        notHandled()
                    }
                    this::class.createInstance()
                } else {
                    viewKey.jvmErasure.createInstance()
                }
            }
            is KClass<*> -> viewKey.createInstance()
            else -> notHandled()
        } as Viewing

        init?.invoke(view)

        if (view.viewModel == null) {
            val navigation = composer.resolve<Navigation<*>>()
            view.viewModel = navigation?.controller
        }

        val controller = view.viewModel as? Controller
        controller?.dependsOn(view)

        view.policy.track()
        return view
    }

    override fun show(view: Viewing) =
            show(view, requireComposer())

    abstract fun show(
            view:     Viewing,
            composer: Handling
    ): ViewingLayer
}