package com.readEveryWord.features.chapters

import com.android.databinding.library.baseAdapters.BR
import com.miruken.callback.Provides
import com.miruken.context.Scoped
import com.miruken.mvc.android.AndroidController
import com.readEveryWord.R
import com.readEveryWord.data.BibleRepository
import com.readEveryWord.domain.Book
import com.readEveryWord.domain.Chapter

class ChapterController
    @Provides @Scoped constructor(
            private val repository: BibleRepository
    ) : AndroidController() {

    private lateinit var book: Book

    lateinit var chapter: Chapter

    fun showChapter(book: Book, chapter: Chapter) {
        this.book    = book
        this.chapter = chapter

        show(R.layout.chapters_chapter, BR.ctrl)
    }

    fun markAsRead() {
        repository.markChapterAsRead(chapter, book)
    }
}