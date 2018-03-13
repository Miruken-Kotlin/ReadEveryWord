package research.app1.domain

import android.databinding.BaseObservable
import android.databinding.Bindable
import research.app1.BR

class Chapter(val number: Int) : BaseObservable() {

    @Bindable
    var read: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.read)
        }

    val numberText: String
        get() = number.toString()

    fun toggle(){
        read = !read
    }
}
