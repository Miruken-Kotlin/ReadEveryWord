package com.readEveryWord.features.chapters

import com.miruken.callback.Provides
import com.miruken.callback.provide
import com.miruken.context.Scoped
import com.miruken.mvc.android.AndroidController
import com.miruken.mvc.android.component.table
import com.miruken.mvc.partial
import com.readEveryWord.BR
import com.readEveryWord.R
import com.readEveryWord.databinding.ChaptersBinding
import com.readEveryWord.domain.Book

class ChaptersController
    @Provides @Scoped
    constructor(@Provides val book: Book) : AndroidController() {

    fun showChapters() {
        bind<ChaptersBinding>(R.layout.chapters, BR.ctrl) { b ->
            table(b.chapterTable, columns = 6).apply {
                 book.chapters.forEach { chapter ->
                    addCell().provide(chapter)
                       .partial<ChapterController> { showChapter() }
                 }
            }
        }
    }
}
