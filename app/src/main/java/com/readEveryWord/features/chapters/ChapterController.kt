package com.readEveryWord.features.chapters

import android.view.ViewGroup
import com.miruken.mvc.android.AndroidController
import com.readEveryWord.Hack
import com.readEveryWord.R
import com.readEveryWord.data.ReadingRecordData
import com.readEveryWord.data.queries.createReadingRecord
import com.readEveryWord.databinding.ChaptersChapterBinding
import com.readEveryWord.domain.Book
import com.readEveryWord.domain.Chapter
import java.util.*

class ChapterController(val book: Book, val chapter: Chapter) : AndroidController() {
    fun addToView(parent: ViewGroup) {
        bind<ChaptersChapterBinding>(R.layout.chapters_chapter, parent).ctrl = this
    }
    fun markAsRead(){
        createReadingRecord(ReadingRecordData(
                book.id,
                chapter.id,
                Date().time,
                0,
                null,
                null), Hack.context)
        chapter.read = true
    }
}