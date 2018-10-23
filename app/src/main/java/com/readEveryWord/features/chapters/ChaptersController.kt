package com.readEveryWord.features.chapters

import android.widget.TableLayout
import android.widget.TableRow
import com.android.databinding.library.baseAdapters.BR
import com.miruken.mvc.android.AndroidController
import com.readEveryWord.R
import com.readEveryWord.domain.Book
import com.readEveryWord.domain.Chapter

class ChaptersController : AndroidController()
{
    lateinit var book: Book

    fun showChapters(selectedBook: Book){
        book = selectedBook

        show(R.layout.chapters_chapter, BR.ctrl){
            findViewById<TableLayout>(R.id.chapter_table).also {
                fillTable(it, book.chapters)
            }
        }
    }

    private fun fillTable(table: TableLayout, items: List<Chapter>){
        table.removeAllViews()
        val columnCount = 6
        val rowCount    = items.size/columnCount
        for (i in 0..rowCount) {
            val row = TableRow(table.context)
            table.addView(row)
            items.drop(i * columnCount).take(columnCount).forEach{
                ChapterController(book, it).addToView(row)
            }
        }
    }

    fun pop () {

    }
}
