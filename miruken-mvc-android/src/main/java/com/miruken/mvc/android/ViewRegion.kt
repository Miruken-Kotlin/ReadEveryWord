package com.miruken.mvc.android

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.miruken.callback.Handling
import com.miruken.concurrent.Promise
import com.miruken.event.Event
import com.miruken.mvc.option.RegionOptions
import com.miruken.mvc.view.*
import java.time.Duration

class ViewRegion(
        context: Context
) : ViewContainer(context), ViewingStackView {
    private val _layers      = mutableListOf<ViewLayer>()
    private var _unwinding   = false

    override fun display(
            region: ViewingRegion
    ): ViewingLayer {

    }

    override fun show(
            view:     Viewing,
            composer: Handling
    ): ViewingLayer {
    }

    // Layers

    override fun pushLayer() =
            createLayer(false).let {
                { it.close() }
            }

    fun pushOverlay() =
            createLayer(true).let {
                { it.close() }
            }

    override fun unwindLayers() {
        _unwinding = true
        while (_layers.isNotEmpty()) {
            _layers.last().close()
        }
        _unwinding = false
    }

    private fun dropLayer(layer: ViewLayer): ViewLayer? {
        val index = _layers.indexOf(layer)
        if (index <= 0) return null
        _layers.removeAt(index)
        return _layers[index - 1]
    }

    private fun removeLayer(layer: ViewLayer) {
        _layers.remove(layer)
        layer.transitionFrom()
    }

    private fun createLayer(overlay: Boolean): ViewLayer {
        val layer = ViewLayer(overlay)
        _layers.add(layer)
        return layer
    }

    private fun getLayerBelow(layer: ViewLayer): ViewLayer? {
        val index = getLayerIndex(layer)
        return if (index > 0) {
            _layers[index - 1]
        } else null
    }

    private fun getLayerIndex(layer: ViewLayer) =
            _layers.indexOf(layer)

    private fun addView(
            fromView:       Viewing?,
            view:           Viewing,
            options:        RegionOptions,
            removeFromView: Boolean,
            composer:       Handling
    ): Promise {
        val androidView = when (view){
            is View -> view
            is ViewLayout -> {
                view.bindingId?.let {
                    check(view.viewModel != null) {
                        "View $view is missing a viewModel"
                    }
                    val inflater = context.getSystemService(
                            Context.LAYOUT_INFLATER_SERVICE)
                            as LayoutInflater
                    val dbView = DataBindingUtil.inflate<ViewDataBinding>(
                            inflater, view.layoutId, this, true)
                    dbView.setVariable(view.bindingId, view.viewModel)
                    dbView
                } else {
                    View.inflate(context, view.layoutId, this)
                }
            }
            else -> error("Unrecognized view $view")
        }

        constrain(androidView)
        addView(androidView)

    }

    private fun constrain(view: View) : View {
        view.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT)

        return view
    }


    private fun removeView(
            fromView: Viewing,
            toView:   Viewing?,
            composer: Handling
    ): Promise {

    }

    // ViewLayer

    inner class ViewLayer(
            private val overlay: Boolean
    ) : ViewingLayer {
        private var _view: Viewing? = null
        private var _composer: Handling? = null

        var view get() = _view
            set(value) {
                val view = value as Viewing
                if (doesDependOn(view)) {
                    view.release()
                }
                _view = value
                if (view.policy.parent == null)
                    dependsOn(view)
            }

        override val index = getLayerIndex(this)

        override val closed = Event<ViewingLayer>()

        override val transitioned = Event<ViewingLayer>()

        fun transitionTo(
                newView:  Viewing,
                options:  RegionOptions,
                composer: Handling
        ): ViewingLayer {
            _composer = composer

            if (view === newView) {
                return this
            }

            var oldView = view

            oldView?.takeIf { overlay }?.also {
                val layer = dropLayer(this)
                if (layer != null) {
                    val actual = layer.transitionTo(
                            newView, options, composer)
                    removeView(oldView!!, null, composer)
                    return actual
                }
            }

            val removeFromView = oldView != null
            if (!removeFromView) {
                getLayerBelow(this)?.also {
                    oldView = it.view
                }
            }

            view = newView
            addView(oldView, newView, options,
                    removeFromView, composer)

            transitioned(this)
        }

        override fun duration(
                duration: Duration,
                complete: (Boolean) -> Unit
        ): () -> Unit {
        }

        override fun close() {

        }
    }
}