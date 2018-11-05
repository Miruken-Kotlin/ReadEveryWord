package com.readEveryWord.features.chapters

import com.miruken.callback.Provides
import com.miruken.context.Scoped
import com.miruken.mvc.android.AndroidController
import com.readEveryWord.R
import com.readEveryWord.BR
import com.readEveryWord.data.BibleRepository
import com.readEveryWord.domain.Book
import com.readEveryWord.domain.Chapter

class ChapterController
    @Provides @Scoped constructor(
            val book: Book,
            @Provides val chapter: Chapter,
            private val repository: BibleRepository
    ) : AndroidController() {

    fun showChapter() {
        showR(R.layout.chapters_chapter, BR.ctrl)
    }

    fun markAsRead() {
        repository.markChapterAsRead(chapter, book)
    }
}