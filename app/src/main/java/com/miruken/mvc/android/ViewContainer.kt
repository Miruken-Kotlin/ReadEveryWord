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
import com.miruken.mvc.Controller
import com.miruken.mvc.Navigation
import com.miruken.mvc.dependsOn
import com.miruken.mvc.view.Viewing
import com.miruken.mvc.view.ViewingLayer
import com.miruken.mvc.view.ViewPolicy
import com.miruken.mvc.view.ViewingRegion
import kotlin.reflect.KClass
import kotlin.reflect.KType
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
            init:    (Viewing.() -> Unit)?
    ): Viewing {
        val composer = requireComposer()

        val view = when (viewKey) {
            is KType -> createView(viewKey.jvmErasure)
            is KClass<*> -> createView(viewKey)
            else -> null
        } ?: notHandled()

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

    override fun show(view: Viewing) = runOnMainThread {
        show(view, requireComposer())
    }

    abstract fun show(
            view:     Viewing,
            composer: Handling
    ): ViewingLayer

    protected fun inflateView(layout: ViewLayout): View {
        val layoutId = layout.layoutId
        val view = layout.bindingId?.let {
            val viewModel = layout.viewModel
            check(viewModel != null) {
                "Bindable layout require a view model"
            }
            val inflater = LayoutInflater.from(context)
            val binding  = DataBindingUtil.inflate<ViewDataBinding>(
                    inflater, layoutId, this, true)
            check(binding?.setVariable(
                    layout.bindingId, viewModel) == true) {
                "Unable to bind layout with id $layoutId"
            }
            binding.root
        } ?: View.inflate(context, layoutId, null)
        layout.initView?.invoke(view)
        return view
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
}