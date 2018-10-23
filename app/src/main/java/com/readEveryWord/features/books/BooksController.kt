package com.readEveryWord.features.books

import android.content.Context
import com.android.databinding.library.baseAdapters.BR
import com.miruken.callback.Provides
import com.miruken.context.Scoped
import com.miruken.mvc.android.AndroidController
import com.miruken.mvc.android.component.TableComponent
import com.miruken.mvc.android.component.table
import com.miruken.mvc.next
import com.readEveryWord.R
import com.readEveryWord.data.queries.getAllReadingRecords
import com.readEveryWord.domain.Bible

class BooksController
    @Provides @Scoped
    constructor(context: Context) : AndroidController() {

    private lateinit var _oldTestament: TableComponent
    private lateinit var _newTestament: TableComponent

    val bible = Bible().apply {
        getAllReadingRecords(context).forEach {
            books[it.bookId].chapters[it.chapterId].read = true
        }
    }


    fun showBooks() {
        show(R.layout.books, BR.ctrl) {
            _oldTestament = table(this, R.id.ot_table, 6).apply {
                bible.oldTestament.forEach { book ->
                    add().next<BookController> { showBook(book) }
                }
            }

            _newTestament = table(this, R.id.nt_table, 6).apply {
                bible.newTestament.forEach { book ->
                    add().next<BookController> { showBook(book) }
                }
            }
        }
    }

    fun goToOldTestamentProgress() {
        push<OldTestamentProgressController>{ showProgress(bible) }
    }

    fun goToNewTestamentProgress() {
        push<OldTestamentProgressController>{ showProgress(bible)}
    }
}
