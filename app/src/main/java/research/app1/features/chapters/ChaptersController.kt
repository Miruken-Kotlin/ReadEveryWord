package research.app1.features.chapters

import android.databinding.DataBindingUtil
import android.widget.TableLayout
import android.widget.TableRow
import research.app1.R
import research.app1.databinding.ChaptersBinding
import research.app1.domain.Book
import research.app1.domain.Chapter
import research.app1.infrastructure.Controller

class  ChaptersController : Controller()
{
    lateinit var book: Book

    fun showChapters(selectedBook: Book){
        book = selectedBook

        val view = inflate(R.layout.chapters)
        bind<ChaptersBinding>(view).ctrl = this

        fun fillTable(table: TableLayout, items: List<Chapter>){
            table.removeAllViews()
            val columnCount = 6
            val rowCount    = items.size/columnCount
            for (i in 0..rowCount) {
                val row = TableRow(activity)
                table.addView(row)
                items.drop(i * columnCount).take(columnCount).forEach{
                    ChapterController(it).addToView(row)
                }
            }
        }

        view.findViewById<TableLayout>(R.id.chapter_table).also {
            fillTable(it, book.chapters)
        }

        push(view)
    }
}
