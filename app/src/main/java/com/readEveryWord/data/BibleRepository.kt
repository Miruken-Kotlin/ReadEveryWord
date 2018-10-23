package com.readEveryWord.data

import android.content.Context
import com.miruken.callback.Handler
import com.miruken.callback.Provides
import com.miruken.callback.Singleton
import com.readEveryWord.data.queries.createReadingRecord
import com.readEveryWord.data.queries.getAllReadingRecords
import com.readEveryWord.domain.Bible
import com.readEveryWord.domain.Book
import com.readEveryWord.domain.Chapter
import java.util.*

class BibleRepository
    @Provides @Singleton constructor(
            val context: Context
    ) : Handler() {

    @Provides @Singleton
    fun getBible() = Bible().apply {
        getAllReadingRecords(context).forEach {
            books[it.bookId].chapters[it.chapterId].read = true
        }
    }

    fun markChapterAsRead(chapter: Chapter, book: Book) {
        createReadingRecord(ReadingRecordData(
                book.id, chapter.id,
                Date().time, 0,
                null, null), context)
        chapter.read = true
    }
}