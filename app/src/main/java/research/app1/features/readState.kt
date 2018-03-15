package research.app1.features

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import research.app1.R

enum class ReadState {
    NOT_STARTED, STARTED, COMPLETED
}

@BindingAdapter("readState")
fun readStateAdapter(view: View, state: ReadState?) {

    fun drawableColor(color: Int) : Drawable? {
        return view.resources.getDrawable(color, null)
    }

    fun color(color: Int) : Int {
        return view.resources.getColor(color, null)
    }

    view.background = drawableColor(when (state) {
        ReadState.COMPLETED -> R.color.secondaryColor
        ReadState.STARTED -> R.color.darkBackground
        else -> R.color.lightBackground
    })

    if (view is TextView) {
        view.setTextColor(color(when (state) {
            ReadState.COMPLETED -> R.color.secondaryTextColor
            ReadState.STARTED -> R.color.lightBackgroundText
            else -> R.color.lightBackgroundText
        }))
    }
}
