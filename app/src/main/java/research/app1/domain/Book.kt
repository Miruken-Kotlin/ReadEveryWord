package research.app1.domain

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.Observable
import research.app1.BR
import research.app1.features.ReadState

class Book(val longName: String, val shortName: String, val chapterCount: Int) : BaseObservable()
{
    val chapters: List<Chapter> = (1..chapterCount).map { Chapter(it) }

    private val chapterCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: Observable, i: Int) {
            notifyPropertyChanged(BR.readState)
            notifyPropertyChanged(BR.progress)
        }
    }

    init {
        chapters.forEach{
            it.addOnPropertyChangedCallback(chapterCallback)
        }
    }

    @Bindable
    var progress: String = ""
        get(){
            val current =
                chapters.filter { x -> x.read }.count().toFloat() /
                chapterCount.toFloat() *
                100

            return "%.0f".format(current) + "%"
        }

    @Bindable
    var readState: ReadState = ReadState.NOT_STARTED
        get(){
            return if(chapters.all { x -> x.read })
                ReadState.COMPLETED
            else if (chapters.any { x -> x.read })
                ReadState.STARTED
            else
                ReadState.NOT_STARTED
        }
}
