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
        val currentBook = getItem(position)
        return inflate(R.layout.books_book).also {
            DataBindingUtil.bind<BooksBookBinding>(it).apply {
                book = currentBook
            }
            it.tag = currentBook
        }
    }
}
