package com.readEveryWord.features.books

import android.widget.*
import com.readEveryWord.data.queries.getAllReadingRecords
import com.readEveryWord.R
import com.readEveryWord.databinding.BooksBinding
import com.readEveryWord.domain.Bible
import com.readEveryWord.domain.Book
import com.readEveryWord.infrastructure.Controller

class BooksController : Controller() {

    val bible: Bible = Bible()

    init {
        getAllReadingRecords().forEach{
            bible.books[it.bookId].chapters[it.chapterId].read = true
        }
    }

    fun showBooks(){
        val view = inflate(R.layout.books)
        bind<BooksBinding>(view)?.ctrl = this

        fun fillTable(table: TableLayout, items: List<Book>){
            table.removeAllViews()
            val columnCount = 6
            val rowCount    = items.size/columnCount
            for (i in 0..rowCount) {
                val row = TableRow(activity)
                table.addView(row)
                items.drop(i * columnCount).take(columnCount).forEach{
                    BookController(it).addToView(row)
                }
            }
        }

        view.findViewById<TableLayout>(R.id.ot_table).also {
            fillTable(it, bible.oldTestament)
        }

        view.findViewById<TableLayout>(R.id.nt_table).also {
            fillTable(it, bible.newTestament)
        }

        push(view)
    }

    fun goToOldTestamentProgress(){
        OldTestamentProgressController().showProgress(bible)
    }

    fun goToNewTestamentProgress(){
        NewTestamentProgressController().showProgress(bible)
    }
}

