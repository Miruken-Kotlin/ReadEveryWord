package research.app1.features.books

import android.view.ViewGroup
import research.app1.R
import research.app1.databinding.BooksBookBinding
import research.app1.domain.Book
import research.app1.features.chapters.ChaptersController
import research.app1.infrastructure.Controller

class BookController(val book: Book) : Controller() {
    fun addToView(parent: ViewGroup) {
        bind<BooksBookBinding>(R.layout.books_book, parent).ctrl = this
    }

    fun goToChapters(){
        ChaptersController().showChapters(book)
    }
}