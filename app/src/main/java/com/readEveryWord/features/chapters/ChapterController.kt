package com.readEveryWord.features.chapters

import android.content.Context
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.miruken.callback.Provides
import com.miruken.callback.resolve
import com.miruken.context.Scoped
import com.miruken.mvc.android.AndroidController
import com.readEveryWord.R
import com.readEveryWord.data.ReadingRecordData
import com.readEveryWord.data.queries.createReadingRecord
import com.readEveryWord.databinding.ChaptersChapterBinding
import com.readEveryWord.domain.Book
import com.readEveryWord.domain.Chapter
import java.util.*

class ChapterController
    @Provides @Scoped
    constructor()  : AndroidController() {

    private lateinit var book:    Book

    lateinit var chapter: Chapter

    fun showChapter(book: Book, chapter: Chapter) {
        this.book    = book
        this.chapter = chapter

        show(R.layout.chapters_chapter, BR.ctrl)
    }

    fun markAsRead(){
        createReadingRecord(ReadingRecordData(
                book.id,
                chapter.id,
                Date().time,
                0,
                null,
                null), context!!.resolve()!!)
        chapter.read = true
    }
}