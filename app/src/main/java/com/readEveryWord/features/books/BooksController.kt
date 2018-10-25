package com.readEveryWord.features.books

import com.android.databinding.library.baseAdapters.BR
import com.miruken.callback.Provides
import com.miruken.context.Scoped
import com.miruken.mvc.android.AndroidController
import com.miruken.mvc.android.component.table
import com.miruken.mvc.partial
import com.readEveryWord.R
import com.readEveryWord.databinding.BooksBinding
import com.readEveryWord.domain.Bible

class BooksController
    @Provides @Scoped
    constructor(val bible: Bible) : AndroidController() {

    fun showBooks() {
        bind<BooksBinding>(R.layout.books, BR.ctrl) { b ->
            table(b.otTable, 6).apply {
                bible.oldTestament.forEach { book ->
                    add().partial<BookController> { show(book) }
                }
            }

            table(b.ntTable, 6).apply {
                bible.newTestament.forEach { book ->
                    add().partial<BookController> { show(book) }
                }
            }
        }
    }

    fun goToOldTestamentProgress() =
        push<OldTestamentProgressController> { show() }

    fun goToNewTestamentProgress() =
        push<NewTestamentProgressController> { show() }
}
