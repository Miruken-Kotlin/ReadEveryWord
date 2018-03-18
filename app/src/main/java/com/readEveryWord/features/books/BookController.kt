package com.readEveryWord.features.books

import android.view.ViewGroup
import com.readEveryWord.R
import com.readEveryWord.databinding.BooksBookBinding
import com.readEveryWord.domain.Book
import com.readEveryWord.features.chapters.ChaptersController
import com.readEveryWord.infrastructure.Controller

class BookController(val book: Book) : Controller() {
    fun addToView(parent: ViewGroup) {
        bind<BooksBookBinding>(R.layout.books_book, parent).ctrl = this
    }

    fun goToChapters(){
        ChaptersController().showChapters(book)
    }
}