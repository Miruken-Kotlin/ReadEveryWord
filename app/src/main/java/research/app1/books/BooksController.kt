package research.app1.books

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import research.app1.R
import research.app1.chapters.ChaptersController
import research.app1.databinding.BooksNotstartedBinding
import research.app1.domain.Bible
import research.app1.domain.Book
import research.app1.infrastructure.Controller

class BooksController : Controller(), AdapterView.OnItemClickListener {
    fun showBooks(){
        var bible = Bible()

        var view = inflate(R.layout.books)

        view.findViewById<GridView>(R.id.ot_grid).also{
            it.adapter = BookAdapter(bible.oldTestament)
            it.onItemClickListener = this
        }

        view.findViewById<GridView>(R.id.nt_grid).also{
            it.adapter = BookAdapter(bible.newTestament)
            it.onItemClickListener = this
        }

        push(view)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val book = view?.tag as Book
        ChaptersController().showChapters(book)
    }
}

class BookAdapter(books: List<Book>) : ArrayAdapter<Book>(Controller.region.context, 0, books){
    private fun inflate(view: Int) : View {
        return (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(view, null)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var currentBook = getItem(position)
        return inflate(R.layout.books_notstarted).also {
            DataBindingUtil.bind<BooksNotstartedBinding>(it).apply {
                book = currentBook
            }
            it.tag = currentBook
        }
    }
}
