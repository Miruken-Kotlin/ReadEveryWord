package research.app1.infrastructure

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.View

open class Controller {

    fun inflate(view: Int) : View {
        val inflater = activity.layoutInflater
        val view     = inflater.inflate(view, null)
        region.addView(view)
        return view
    }

    fun show(view: Int) {
        inflate(view)
    }

    fun <T : ViewDataBinding?>show(view: Int) : T {
        return DataBindingUtil.bind<T>(inflate(view))
    }

    companion object {
        lateinit var activity: Activity
        lateinit var region: Region
    }
}