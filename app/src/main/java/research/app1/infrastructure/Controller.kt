package research.app1.infrastructure

import android.annotation.SuppressLint
import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.View
import android.widget.RelativeLayout

open class Controller {

    fun inflate(view: Int) : View {
        return activity.layoutInflater.inflate(view, null)
    }

    fun <T : ViewDataBinding?>bind(view: View) : T {
        return DataBindingUtil.bind<T>(view)
    }

    private fun push(view: Int) : View {
        return push(inflate(view))
    }

    fun push(view: View) : View {
        constrain(view)
        animate(view)
        views.add(view)
        region.addView(view)
        return view
    }

    fun <T : ViewDataBinding?>push(view: Int) : T {
        return bind<T>(push(view))
    }

    private fun constrain(view: View) : View {

        val layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT)
        view.layoutParams = layoutParams

        return view
    }

    private fun animate(view: View) : View {

        val shortDuration = 500L

        //out with the old view if it exists
        if(views.any()){
            views.last().animate().apply {
                alpha(0f)
                duration = shortDuration
            }
        }

        //in with the new view
        view.alpha = 0f
        view.visibility = View.VISIBLE
        view.animate().apply {
            alpha(1f)
            duration = shortDuration
        }

        return view
    }

    fun pop() {
        if(views.any())
        {
            val last = views.last()
            region.removeView(last)
            views.remove(last)
        }
        if(views.any())
            views.last().alpha = 1f
    }

    companion object {
        var views: MutableList<View> = mutableListOf()
        @SuppressLint("StaticFieldLeak")
        lateinit var activity: Activity
        lateinit var region:   Region
    }
}