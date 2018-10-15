package com.readEveryWord.features.chapters

import android.widget.TableLayout
import android.widget.TableRow
import com.readEveryWord.R
import com.readEveryWord.databinding.ChaptersBinding
import com.readEveryWord.domain.Book
import com.readEveryWord.domain.Chapter
import com.readEveryWord.infrastructure.Controller

class  ChaptersController : Controller()
{
    lateinit var book: Book

    fun showChapters(selectedBook: Book){
        book = selectedBook

        val view = inflate(R.layout.chapters)
        bind<ChaptersBinding>(view)?.ctrl = this

        fun fillTable(table: TableLayout, items: List<Chapter>){
            table.removeAllViews()
            val columnCount = 6
            val rowCount    = items.size/columnCount
            for (i in 0..rowCount) {
                val row = TableRow(activity)
                table.addView(row)
                items.drop(i * columnCount).take(columnCount).forEach{
                    ChapterController(book, it).addToView(row)
                }
            }
        }

        view.findViewById<TableLayout>(R.id.chapter_table).also {
            fillTable(it, book.chapters)
        }

        push(view)
    }
}
