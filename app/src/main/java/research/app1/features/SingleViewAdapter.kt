package research.app1.features

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import research.app1.infrastructure.Controller

open class SingleViewAdapter<T>(list: List<T>) :
    ArrayAdapter<T>(Controller.region.context, 0, list)
{
    fun inflate(view: Int) : View {
        return (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(view, null)
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }
}
