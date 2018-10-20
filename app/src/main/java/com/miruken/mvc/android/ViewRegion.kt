package com.miruken.mvc.android

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.miruken.callback.Handling
import com.miruken.callback.getOptions
import com.miruken.callback.notHandled
import com.miruken.callback.resolve
import com.miruken.concurrent.Promise
import com.miruken.event.Event
import com.miruken.mvc.option.RegionOptions
import com.miruken.mvc.view.*
import java.time.Duration

class ViewRegion(
        context: Context
) : ViewContainer(context), ViewingStackView {
    private val _layers    = mutableListOf<ViewLayer>()
    private var _unwinding = false

    private val activeView = activeLayer?.view

    override fun show(
            view:     Viewing,
            composer: Handling
    ): ViewingLayer {
        val newView = when (view) {
            is View -> view
            is ViewLayout -> inflateView(view)
            else -> notHandled()
        }
        return transitionTo(newView, view, composer)
    }

    private fun transitionTo(
            view:     View,
            viewing:  Viewing,
            composer: Handling
    ): ViewingLayer {
        var push         = false
        var overlay      = false
        val options      = composer.getOptions(RegionOptions())
        val layerOptions = options?.layer

        var layer: ViewLayer? = null

        if (_layers.isEmpty()) {
            push = true
        } else layerOptions?.also {
            when {
                it.push == true -> push = true
                it.overlay == true -> {
                    push    = true
                    overlay = true
                }
                it.unload == true -> {
                    unwindLayers()
                    push = true
                }
                else -> layer = it.choose(_layers) as ViewLayer
            }
        }

        if (push) {
            layer = if (overlay) {
                pushOverlay()
            } else {
                pushLayer()
            }
            composer.resolve<com.miruken.context.Context>()?.also {
                it.contextEnding += { _ ->
                    // allows ending animation
                    if (_layers.isNotEmpty()) {
                        layer!!.close()
                    }
                }
            }
        }

        return (layer ?: activeLayer)
                ?.transitionTo(viewing to view, options, composer)
                ?: error("Unable to determine the view layer")
    }

    // Layers

    private val activeLayer get() =
        if (_layers.isNotEmpty()) _layers.last() else null

    override fun pushLayer() = createLayer(false)

    private fun pushOverlay() = createLayer(true)

    override fun unwindLayers() {
        _unwinding = true
        while (_layers.isNotEmpty()) {
            _layers.last().close()
        }
        _unwinding = false
    }

    private fun createLayer(overlay: Boolean) =
            ViewLayer(overlay).apply { _layers.add(this) }

    private fun removeLayer(layer: ViewLayer) =
            layer.takeIf { _layers.remove(it) }
                    ?.transitionFrom()

    private fun dropLayer(layer: ViewLayer) =
            _layers.indexOf(layer).takeIf { it > 0 }?.let {
                _layers.removeAt(it)
                _layers[it - 1]
            }

    private fun getLayerBelow(layer: ViewLayer) =
        getLayerIndex(layer).takeIf { it > 0 }?.let {
            _layers[it - 1]
        }

    private fun getLayerIndex(layer: ViewLayer) =
            _layers.indexOf(layer)

    private fun addView(
            fromView:       View?,
            view:           View,
            options:        RegionOptions?,
            removeFromView: Boolean,
            composer:       Handling
    ): Promise<*> {
        if (_unwinding && indexOfChild(view) >= 0) {
            return Promise.EMPTY
        }

        constrain(view)

        val fromIndex = fromView?.let { indexOfChild(it) } ?: -1
        if (fromIndex >= 0) {
            addView(view, fromIndex)
        } else {
            addView(view)
        }

        fromView?.takeIf { removeFromView }?.apply {
            removeView(fromView, null, composer)
        }

        return Promise.EMPTY
    }

    private fun removeView(
            fromView: View,
            toView:   View?,
            composer: Handling?
    ): Promise<*> {
        removeView(fromView)
        return Promise.EMPTY
    }

    private fun constrain(view: View) = view.apply {
        layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT)
    }

    inner class ViewLayer(
            private val overlay: Boolean
    ) : ViewingLayer {
        private var _composer: Handling? = null
        private var _view: Pair<Viewing, View>? = null
        private var _closed = false

        var view: Pair<Viewing, View>?
            get() = _view
            set(value) {
                _view?.first?.takeIf {
                    doesDependOn(it)
                }?.release()
                _view = value
                view?.first?.takeIf {
                    it.policy.parent == null
                }?.also {
                    dependsOn(it)
                }
            }

        override val index = getLayerIndex(this)

        override val closed = Event<ViewingLayer>()

        override val transitioned = Event<ViewingLayer>()

        fun transitionTo(
                newView:  Pair<Viewing, View>,
                options:  RegionOptions?,
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
                    removeView(oldView!!.second, null, composer)
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
            addView(oldView?.second, newView.second, options,
                    removeFromView, composer)

            transitioned(this)
            return this
        }

        fun transitionFrom() = runOnMainThread {
            val activeView = activeView
            view?.takeUnless {
                it.second === activeView?.second
            }?.also {
                removeView(it.second, activeView?.second, _composer)
            }
            view = null
        }

        override fun duration(
                duration: Duration,
                complete: (Boolean) -> Unit
        ): () -> Unit {
            return {}
        }

        override fun close() {
            if (_closed) return
            try {
                removeLayer(this)
            } finally {
                _closed = true
                closed(this)
            }
        }
    }
}