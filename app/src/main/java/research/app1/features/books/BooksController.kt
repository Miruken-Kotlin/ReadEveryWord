package research.app1.features.books

import android.widget.*
import research.app1.R
import research.app1.databinding.BooksBinding
import research.app1.domain.Bible
import research.app1.domain.Book
import research.app1.infrastructure.Controller

class BooksController : Controller() {

    val bible: Bible = Bible()
    init {
        fun readAll(book: Book){
            book.chapters.forEach {
                it.toggle()
            }
        }
        fun readSome(book: Book){
            book.chapters.take(3).forEach {
                it.toggle()
            }
        }

        bible.oldTestament.also {
            readAll(it[0])
            readSome(it[1])
        }

        bible.newTestament.also{
            readSome(it[0])
            readSome(it[1])
            readSome(it[2])
            readAll(it[3])
            readAll(it.last())
        }
    }

    fun showBooks(){
        val view = inflate(R.layout.books)
        bind<BooksBinding>(view).ctrl = this

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

