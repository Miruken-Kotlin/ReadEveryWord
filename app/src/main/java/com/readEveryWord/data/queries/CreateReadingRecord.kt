package com.readEveryWord.data.queries

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.readEveryWord.data.DataContext
import com.readEveryWord.data.ReadingRecordData
import com.readEveryWord.data.ReadingRecordTable

fun createReadingRecord(
        data:    ReadingRecordData,
        context: Context
) {
    var db: SQLiteDatabase? = null
    try {
        db = DataContext(context).writableDatabase
        db.insert(ReadingRecordTable.Name, null, ContentValues().apply {
            put(ReadingRecordTable.UserId,    data.userId)
            put(ReadingRecordTable.BookId,    data.bookId)
            put(ReadingRecordTable.ChapterId, data.chapterId)
            put(ReadingRecordTable.Date,      data.date)
            put(ReadingRecordTable.TimesRead, data.timesRead)
            put(ReadingRecordTable.RemoteId,  data.remoteId)
        })
    } catch(e: Exception) {
      print(e.message)
    } finally {
        db?.close()
    }
}
