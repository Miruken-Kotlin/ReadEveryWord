package research.app1.infrastructure

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.View

open class Controller {

    var views: MutableList<View> = mutableListOf()

    fun inflate(view: Int) : View {
        return activity.layoutInflater.inflate(view, null)
    }

    fun push(view: Int) : View {
        return push(inflate(view))
    }

    fun push(view: View) : View {
        views.add(view)
        region.addView(view)
        return view
    }

    fun <T : ViewDataBinding?>push(view: Int) : T {
        return DataBindingUtil.bind<T>(push(view))
    }

    companion object {
        lateinit var activity: Activity
        lateinit var region:   Region
    }
}