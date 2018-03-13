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
import research.app1.databinding.ChaptersNotReadBinding
import research.app1.databinding.ChaptersReadBinding
import research.app1.domain.Book
import research.app1.domain.Chapter
import research.app1.infrastructure.Controller

class  ChaptersController :
        Controller(),
        AdapterView.OnItemClickListener
{

    lateinit var book:    Book
    lateinit var adapter: ChapterAdapter

    fun showChapters(selectedBook: Book){
        book = selectedBook

        val view = inflate(R.layout.chapters)
        DataBindingUtil.bind<ChaptersBinding>(view).also {
            it.ctrl = this
        }
        adapter = ChapterAdapter(selectedBook.chapters)
        view.findViewById<GridView>(R.id.chapter_grid).also {
            it.adapter = adapter
            it.onItemClickListener = this
        }

        push(view)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val chapter = when {
            view?.tag is ChaptersNotReadBinding ->
                (view.tag as ChaptersNotReadBinding).chapter
            view?.tag is ChaptersReadBinding ->
                (view.tag as ChaptersReadBinding).chapter
            else -> throw Exception("binding must be stored in the tag")
        }

        chapter?.toggle()
        adapter.notifyDataSetChanged()
    }
}

class ChapterAdapter(chapters: List<Chapter>) :
        ArrayAdapter<Chapter>(Controller.region.context, 0, chapters)
{
    private fun inflate(view: Int) : View {
        return (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(view, null)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val chapter = getItem(position)

        val currentState = when {
            convertView?.tag is ChaptersNotReadBinding ->
                R.layout.chapters_not_read
            convertView?.tag is ChaptersReadBinding ->
                R.layout.chapters_read
            else -> -1
        }

        val nextState = if(chapter.read) R.layout.chapters_read else R.layout.chapters_not_read

        val correctState = currentState == nextState

        return when(nextState){
            R.layout.chapters_not_read -> {
                return if(convertView == null || !correctState){
                    val view    = inflate(R.layout.chapters_not_read)
                    val binding = DataBindingUtil.bind<ChaptersNotReadBinding>(view)
                    binding.chapter = chapter
                    view.tag = binding
                    view
                } else {
                    (convertView.tag as ChaptersNotReadBinding).chapter = chapter
                    convertView
                }
            }
            R.layout.chapters_read -> {
                return if(convertView == null || !correctState){
                    val view    = inflate(R.layout.chapters_read)
                    val binding = DataBindingUtil.bind<ChaptersReadBinding>(view)
                    binding.chapter = chapter
                    view.tag = binding
                    view
                } else {
                    (convertView.tag as ChaptersReadBinding).chapter = chapter
                    convertView
                }
            }
            else -> throw Exception("Unexpected state")
        }
    }

}
