package com.readEveryWord.features.books

import android.widget.TableLayout
import android.widget.TableRow
import com.android.databinding.library.baseAdapters.BR
import com.miruken.callback.Provides
import com.miruken.context.Scoped
import com.miruken.mvc.android.AndroidController
import com.readEveryWord.Hack
import com.readEveryWord.R
import com.readEveryWord.data.queries.getAllReadingRecords
import com.readEveryWord.domain.Bible
import com.readEveryWord.domain.Book

class BooksController
    @Provides @Scoped
    constructor(): AndroidController() {

    val bible = Bible()

    init {
        getAllReadingRecords(Hack.context).forEach{
            bible.books[it.bookId].chapters[it.chapterId].read = true
        }
    }

    fun showBooks() {
        show(R.layout.books, BR.ctrl) {
            findViewById<TableLayout>(R.id.ot_table).also {
                fillTable(it, bible.oldTestament)
            }

            findViewById<TableLayout>(R.id.nt_table).also {
                fillTable(it, bible.newTestament)
            }
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
                        BookController(it).addToView(row)
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

