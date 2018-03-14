package research.app1.features.books

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import research.app1.R
import research.app1.databinding.BooksBookBinding
import research.app1.features.chapters.ChaptersController
import research.app1.domain.Bible
import research.app1.domain.Book
import research.app1.features.SingleViewAdapter
import research.app1.infrastructure.Controller

class BooksController : Controller() {
    fun showBooks(){
        val bible = Bible()
        bible.books[0].chapters.forEach{
            it.read = it.number % 2 == 0
        }

        val view = inflate(R.layout.books)

        view.findViewById<GridView>(R.id.ot_grid).also{
            it.adapter = BookAdapter(bible.oldTestament)
            it.onItemClickListener = goToChapters
        }

        view.findViewById<GridView>(R.id.nt_grid).also{
            it.adapter = BookAdapter(bible.newTestament)
            it.onItemClickListener = goToChapters
        }

        push(view)
    }

    private val goToChapters = AdapterView.OnItemClickListener { _, view, _, _ ->
        val book = (view?.tag as BooksBookBinding).book
        ChaptersController().showChapters(book!!)
    }
}

class BookAdapter(books: List<Book>) : SingleViewAdapter<Book>(books) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflate(R.layout.books_book).also {
            it.tag = DataBindingUtil.bind<BooksBookBinding>(it)
        }
        (view.tag as BooksBookBinding).book = getItem(position)
        return view
    }
}
