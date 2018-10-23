package com.readEveryWord.features.books

import android.content.Context
import android.widget.TableLayout
import android.widget.TableRow
import com.android.databinding.library.baseAdapters.BR
import com.miruken.callback.Provides
import com.miruken.context.Scoped
import com.miruken.mvc.android.AndroidController
import com.miruken.mvc.android.ViewRegion
import com.miruken.mvc.next
import com.readEveryWord.R
import com.readEveryWord.data.queries.getAllReadingRecords
import com.readEveryWord.domain.Bible
import kotlin.math.ceil

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
            var ot = TableComponent(this@BooksController, findViewById(R.id.ot_table), 6)
            bible.oldTestament.forEach {
                ot.add()?.next<BookController> { showBook(it) }
            }

            var nt = TableComponent(this@BooksController, findViewById(R.id.nt_table), 6)
            bible.newTestament.forEach {
                nt.add()?.next<BookController> { showBook(it) }
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

class TableComponent(private val contextual: com.miruken.context.Contextual, val table: TableLayout, val columns: Int) {

    private val _rows: ArrayList<TableRow> = ArrayList()
    private var _count = 0

    init{
        table.removeAllViews()
    }

    fun add() : com.miruken.context.Context?{
        _count++
        var row = currentRow()
        val region = ViewRegion(row.context)
        row.addView(region)
        val childContext = contextual.context?.createChild()
        childContext?.addHandlers(region)
        return childContext
    }

    private fun currentRow() : TableRow {
        table.childCount

        if(_rows.count() === 0 || ceil(_count.toDouble()/columns) > _rows.count()){
            val row = TableRow(table.context)
            _rows.add(row)
            table.addView(row)
            return row
        }
        return _rows.last()
    }
}