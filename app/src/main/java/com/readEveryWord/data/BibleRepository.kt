package com.readEveryWord.data

import android.content.Context
import com.miruken.callback.Provides
import com.miruken.callback.Singleton
import com.readEveryWord.data.queries.getAllReadingRecords
import com.readEveryWord.domain.Bible

object BibleRepository {
    @Provides @Singleton
    fun getBible(context: Context) = Bible().apply {
        getAllReadingRecords(context).forEach {
            books[it.bookId].chapters[it.chapterId].read = true
        }
    }
}