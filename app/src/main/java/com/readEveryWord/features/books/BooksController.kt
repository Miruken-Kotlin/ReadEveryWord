package com.readEveryWord.features.books

import com.android.databinding.library.baseAdapters.BR
import com.miruken.callback.Provides
import com.miruken.context.Scoped
import com.miruken.mvc.android.AndroidController
import com.miruken.mvc.android.component.TableComponent
import com.miruken.mvc.android.component.table
import com.miruken.mvc.next
import com.readEveryWord.R
import com.readEveryWord.domain.Bible

class BooksController
    @Provides @Scoped
    constructor(val bible: Bible) : AndroidController() {

    fun showBooks() {
        show(R.layout.books, BR.ctrl) {
            table(this, R.id.ot_table, 6).apply {
                bible.oldTestament.forEach { book ->
                    add().next<BookController> { show(book) }
                }
            }

            table(this, R.id.nt_table, 6).apply {
                bible.newTestament.forEach { book ->
                    add().next<BookController> { show(book) }
                }
            }
        }
    }

    fun goToOldTestamentProgress() {
        push<OldTestamentProgressController> { show() }
    }

    fun goToNewTestamentProgress() {
        push<NewTestamentProgressController> { show() }
    }
}
