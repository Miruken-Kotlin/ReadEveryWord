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

    var book: Book? = null

    fun showBook(selectedBook: Book) {
        book = selectedBook
        show(R.layout.books_book, BR.ctrl)
    }

    fun goToChapters() {
        book?.apply {
            ChaptersController().showChapters(this)
        }
    }
}