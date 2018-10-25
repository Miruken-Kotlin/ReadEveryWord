package com.miruken.mvc.android

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.miruken.callback.Handling
import com.miruken.callback.notHandled
import com.miruken.callback.requireComposer
import com.miruken.callback.resolve
import com.miruken.context.dispose
import com.miruken.mvc.Navigation
import com.miruken.mvc.view.Viewing
import com.miruken.mvc.view.ViewingLayer
import com.miruken.mvc.view.ViewingRegion
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

abstract class ViewContainer(context: Context) :
        RelativeLayout(context), ViewingRegion, Viewing {
    override var viewModel: Any? = null

    override fun display(region: ViewingRegion) =
            region.show(this)

    override fun view(
            viewKey: Any,
            init:    (Viewing.() -> Unit)?
    )= (when (viewKey) {
            is KType -> createView(viewKey.jvmErasure)
            is KClass<*> -> createView(viewKey)
            else -> null
        } ?: notHandled()).also { init?.invoke(it) }

    override fun show(view: Viewing): ViewingLayer {
        val composer = requireComposer()
        bindController(view, composer)
        return runOnMainThread { show(view, composer) }
    }

    abstract fun show(
            view:     Viewing,
            composer: Handling
    ): ViewingLayer

    protected fun inflateView(layout: ViewLayout<*>): View {
        val inflater = LayoutInflater.from(context)
        val binding  = DataBindingUtil.inflate<ViewDataBinding>(
                inflater, layout.layoutId, this, false)
        layout.bind(binding.root, binding)
        return binding.root
    }

    private fun createView(viewClass: KClass<*>): Viewing? {
        if (!viewClass.isSubclassOf(Viewing::class)) {
            return null
        }
        val viewImplClass = when {
            viewClass.java.isInterface ||
            viewClass.isAbstract -> this::class.takeIf {
                it.isSubclassOf(viewClass)
            } ?: return null
            else -> viewClass
        }
        return viewImplClass.constructors.firstOrNull {
            it.parameters.size == 1 &&
            it.parameters[0].type.classifier == Context::class
        }?.let {
            runOnMainThread {
                it.call(context) as Viewing
            }
        }
    }

    private fun bindController(view: Viewing, composer: Handling) {
        if (view.viewModel != null) return
        val navigation = composer.resolve<Navigation<*>>()
        navigation?.controller?.also { controller ->
            view.viewModel = controller
            (view as? AutoCloseable)?.also {
                controller.context?.dispose(it)
            }
        }
    }
}