package com.readEveryWord.features.books

import com.miruken.callback.Provides
import com.miruken.context.Scoped
import com.miruken.mvc.android.AndroidController
import com.readEveryWord.R
import com.readEveryWord.features.chapters.ChaptersController
import com.android.databinding.library.baseAdapters.BR
import com.readEveryWord.domain.Book

class BookController
    @Provides @Scoped
    constructor() : AndroidController() {

    lateinit var book: Book

    fun showBook(book: Book) {
        this.book = book
        show(R.layout.books_book, BR.ctrl)
    }

    fun goToChapters() {
        push<ChaptersController>{ showChapters(this.book) }
    }
}