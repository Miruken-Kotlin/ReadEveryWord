package research.app1.chapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import research.app1.R
import research.app1.databinding.ChaptersBinding
import research.app1.databinding.ChaptersNotReadBinding
import research.app1.domain.Book
import research.app1.domain.Chapter
import research.app1.infrastructure.Controller

class  ChaptersController : Controller(), AdapterView.OnItemClickListener{

    fun showChapters(selectedBook: Book){
        val view = inflate(R.layout.chapters)
        DataBindingUtil.bind<ChaptersBinding>(view).also {
            it.book = selectedBook
            it.ctrl = this
        }
//        view.findViewById<GridView>(R.id.ot_grid).also{
//            it.adapter = ChapterAdapter(selectedBook.chapters)
//            it.onItemClickListener = this
//        }
        push(view)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
}

class ChapterAdapter(chapters: List<Chapter>) : ArrayAdapter<Chapter>(Controller.region.context, 0, chapters){
    private fun inflate(view: Int) : View {
        return (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(view, null)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val currentChapter = getItem(position)
        return inflate(R.layout.books_notstarted).also {
            DataBindingUtil.bind<ChaptersNotReadBinding>(it).apply {
                chapter = currentChapter
            }
        }
    }
}
