package com.readEveryWord.features.chapters

import android.widget.TableLayout
import android.widget.TableRow
import com.android.databinding.library.baseAdapters.BR
import com.miruken.mvc.android.AndroidController
import com.miruken.mvc.android.component.table
import com.miruken.mvc.push
import com.readEveryWord.R
import com.readEveryWord.domain.Book
import com.readEveryWord.domain.Chapter

class ChaptersController : AndroidController()
{
    lateinit var book: Book

    fun showChapters(selectedBook: Book){
        book = selectedBook

        show(R.layout.chapters_chapter, BR.ctrl){
            table(this, R.id.chapter_table, 6).apply {
                 book.chapters.forEach {
                    add().push<ChaptersController> { showChapters(this.book) }
                 }
            }
        }
    }

    fun pop () {
        this.context?.end()
    }
}
