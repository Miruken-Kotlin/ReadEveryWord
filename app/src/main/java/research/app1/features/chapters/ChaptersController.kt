package research.app1.features.chapters

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
import research.app1.databinding.ChaptersChapterBinding
import research.app1.domain.Book
import research.app1.domain.Chapter
import research.app1.infrastructure.Controller

class  ChaptersController : Controller()
{
    lateinit var book:    Book

    fun showChapters(selectedBook: Book){
        book = selectedBook

        val view = inflate(R.layout.chapters).also{
            DataBindingUtil.bind<ChaptersBinding>(it).also {
                it.ctrl = this
            }
        }

        view.findViewById<GridView>(R.id.chapter_grid).also {
            it.adapter = ChapterAdapter(book.chapters)
            it.onItemClickListener = toggleRead
        }

        push(view)
    }

    private val toggleRead = AdapterView.OnItemClickListener { _, view: View, _, _ ->
        val chapter = (view.tag as ChaptersChapterBinding).chapter
        chapter?.toggle()
    }
}

class ChapterAdapter(chapters: List<Chapter>) :
        ArrayAdapter<Chapter>(Controller.region.context, 0, chapters)
{
    private fun inflate(view: Int) : View {
        return (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(view, null)
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflate(R.layout.chapters_chapter).also {
            it.tag = DataBindingUtil.bind<ChaptersChapterBinding>(it)
        }
        (view.tag as ChaptersChapterBinding).chapter = getItem(position)
        return view
    }
}
