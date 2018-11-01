package com.readEveryWord.features.books

import com.miruken.callback.Provides
import com.miruken.context.Scoped
import com.miruken.mvc.android.AndroidController
import com.readEveryWord.BR
import com.readEveryWord.R
import com.readEveryWord.domain.Book
import com.readEveryWord.features.chapters.ChaptersController

class BookController
    @Provides @Scoped
    constructor(@Provides val book: Book) : AndroidController() {

    fun showBook() {
        show(R.layout.books_book, BR.ctrl)
    }

    fun goToChapters() {
        push<ChaptersController> { showChapters() }
    }
}