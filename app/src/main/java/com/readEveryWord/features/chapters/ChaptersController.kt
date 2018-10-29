package com.readEveryWord.features.chapters

import com.miruken.callback.Provides
import com.miruken.context.Scoped
import com.miruken.mvc.android.AndroidController
import com.miruken.mvc.android.component.table
import com.miruken.mvc.partial
import com.readEveryWord.R
import com.readEveryWord.BR
import com.readEveryWord.databinding.ChaptersBinding
import com.readEveryWord.domain.Book

class ChaptersController
    @Provides @Scoped
    constructor() : AndroidController() {

    lateinit var book: Book

    fun showChapters(book: Book) {
        this.book = book

        bind<ChaptersBinding>(R.layout.chapters, BR.ctrl) { b ->
            table(b.chapterTable, 6).apply {
                 book.chapters.forEach { chapter ->
                    add().partial<ChapterController> {
                        showChapter(book, chapter)
                    }
                 }
            }
        }
    }
}
