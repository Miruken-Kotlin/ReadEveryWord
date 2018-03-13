package research.app1.domain

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.Observable
import research.app1.BR

class Book(val longName: String, val shortName: String, val chapterCount: Int) : BaseObservable()
{
    val chapters: List<Chapter> = (1..chapterCount).map { Chapter(it) }

    private val chapterCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: Observable, i: Int) {
            notifyPropertyChanged(BR.started)
            notifyPropertyChanged(BR.completed)
            notifyPropertyChanged(BR.progress)
        }
    }

    init {
        chapters.forEach{
            it.addOnPropertyChangedCallback(chapterCallback)
        }
    }

    @Bindable
    var started : Boolean = false
        get() = chapters.any { x -> x.read }

    @Bindable
    var completed : Boolean = false
        get() = chapters.all { x -> x.read }

    @Bindable
    var progress: String = ""
        get(){
            val current =
                chapters.filter { x -> x.read }.count().toFloat() /
                chapterCount.toFloat() *
                100

            return "%.0f".format(current) + "%"
        }
}
