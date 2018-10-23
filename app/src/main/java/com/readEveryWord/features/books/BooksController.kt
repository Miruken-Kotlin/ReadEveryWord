package com.readEveryWord.features.books

import android.content.Context
import android.databinding.BindingAdapter
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import com.android.databinding.library.baseAdapters.BR
import com.miruken.callback.Next
import com.miruken.callback.Provides
import com.miruken.callback.toHandler
import com.miruken.context.Contextual
import com.miruken.context.Scoped
import com.miruken.mvc.android.AndroidController
import com.miruken.mvc.android.ViewRegion
import com.miruken.mvc.next
import com.readEveryWord.R
import com.readEveryWord.data.queries.getAllReadingRecords
import com.readEveryWord.domain.Bible
import com.readEveryWord.domain.Book

class BooksController
    @Provides @Scoped
    constructor(context: Context) : AndroidController() {

    val bible = Bible()

    init {
        getAllReadingRecords(context).forEach {
            bible.books[it.bookId].chapters[it.chapterId].read = true
        }
    }

    fun showBooks() {
        show(R.layout.books, BR.ctrl) {
            findViewById<TableLayout>(R.id.ot_table).also {
                fillTable(it, bible.oldTestament)
            }
//
//            findViewById<TableLayout>(R.id.nt_table).also {
//                fillTable(it, bible.newTestament)
//            }
        }
    }

    private fun fillTable(table: TableLayout, items: List<Book>){
        table.removeAllViews()
        val columnCount = 6
        val rowCount    = items.size/columnCount
        for (i in 0..rowCount) {
            val row = TableRow(table.context)
            table.addView(row)
            items.asSequence().drop(i * columnCount)
                    .take(columnCount)
                    .toList()
                    .forEach {
                        val region = ViewRegion(row.context)
                        row.addView(region)
                        region.toHandler().next<BookController> { showBook(it) }
                    }
        }
    }

    fun goToOldTestamentProgress(){
        OldTestamentProgressController().showProgress(bible)
    }

    fun goToNewTestamentProgress(){
        NewTestamentProgressController().showProgress(bible)
    }
}

@BindingAdapter("entries")
fun <T> setEntries(viewGroup: ViewGroup,
                   oldEntries: List<T>?,
                   newEntries: List<T>?) {

    newEntries?.forEach {
        viewGroup.addView(ViewRegion(viewGroup.context))
    }
}

class TableComponent(contextual: Contextual) {

}