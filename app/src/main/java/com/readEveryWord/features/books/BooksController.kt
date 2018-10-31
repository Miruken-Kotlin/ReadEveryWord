package com.readEveryWord.features.books

import com.miruken.callback.Provides
import com.miruken.callback.provide
import com.miruken.context.Scoped
import com.miruken.mvc.android.AndroidController
import com.miruken.mvc.android.component.table
import com.miruken.mvc.partial
import com.readEveryWord.R
import com.readEveryWord.BR
import com.readEveryWord.databinding.BooksBinding
import com.readEveryWord.domain.Bible

class BooksController
    @Provides @Scoped
    constructor(val bible: Bible) : AndroidController() {

    fun showBooks() {
        bind<BooksBinding>(R.layout.books, BR.ctrl) { b ->
            table(b.otTable, columns = 6).apply {
                bible.oldTestament.forEach { book ->
                    addCell().provide(book)
                        .partial<BookController> { showBook() }
                }
            }

            table(b.ntTable, columns = 6).apply {
                bible.newTestament.forEach { book ->
                    addCell().provide(book)
                        .partial<BookController> { showBook() }
                }
            }
        }
    }

    fun oldTestamentProgress() =
        push<OldTestamentProgressController> { show() }

    fun newTestamentProgress() =
        push<NewTestamentProgressController> { show() }
}
