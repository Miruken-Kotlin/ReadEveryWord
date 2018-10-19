package com.miruken.mvc.android

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.IdRes
import android.view.LayoutInflater
import android.view.View
import com.miruken.mvc.view.ViewPolicy
import com.miruken.mvc.view.Viewing
import com.miruken.mvc.view.ViewingRegion

class ViewLayout(
        @IdRes private val layoutId: Int,
        private val bindingId: Int? = null,
        private val initView:  (View.() -> Unit)? = null
) : Viewing {
    private var _view: View? = null
    private var _viewModel: Any? = null
    private var _binding: ViewDataBinding? = null

    override var viewModel: Any?
        get() = _viewModel
        set(value) {
            if (_viewModel === value) return
            _viewModel = value
            if (_binding != null) {
                _binding!!.setVariable(bindingId!!, value)
            }
        }

    override val policy by lazy { ViewPolicy(this) }

    override fun display(region: ViewingRegion) =
            region.show(this)

    fun inflate(context: Context): View {
        if (_view != null) return _view!!
        _view = bindingId?.let {
            check(viewModel != null) {
                "Bindable views require a bindingId"
            }
            val inflater = context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)
                    as LayoutInflater
            _binding = DataBindingUtil.inflate(
                    inflater, layoutId, null, false)
            _binding!!.setVariable(bindingId, viewModel)
            _binding!!.root.also { view ->
                initView?.invoke(view)
            }
        } ?: View.inflate(context, layoutId, null)
        return _view!!
    }
}
