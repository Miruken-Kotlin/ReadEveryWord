package com.readEveryWord.data.queries

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.readEveryWord.data.DataContext
import com.readEveryWord.data.ReadingRecordData
import com.readEveryWord.data.ReadingRecordTable.Companion.BookId
import com.readEveryWord.data.ReadingRecordTable.Companion.ChapterId
import com.readEveryWord.data.ReadingRecordTable.Companion.Date
import com.readEveryWord.data.ReadingRecordTable.Companion.Name
import com.readEveryWord.data.ReadingRecordTable.Companion.RemoteId
import com.readEveryWord.data.ReadingRecordTable.Companion.TimesRead
import com.readEveryWord.data.ReadingRecordTable.Companion.UserId

fun getAllReadingRecords() : List<ReadingRecordData>{

    val sql = """
            SELECT
                $UserId,
                $BookId,
                $ChapterId,
                $Date,
                $TimesRead,
                $RemoteId
            FROM $Name
        """

    var db: SQLiteDatabase? = null
    var cursor: Cursor? = null
    val records = mutableListOf<ReadingRecordData>()
    try {
        db = DataContext(Controller.activity).readableDatabase

        cursor = db?.rawQuery(sql, null)
        if(cursor?.count!! > 0){
            while (cursor.moveToNext()) {
                records.add(ReadingRecordData(
                        userId    = cursor.getLong(0),
                        bookId    = cursor.getInt(1),
                        chapterId = cursor.getInt(2),
                        date      = cursor.getLong(3),
                        timesRead = cursor.getInt(4),
                        remoteId  = cursor.getLong(5)
                ))
            }
        }
    } catch(e: Exception) {
        print(e.message)
    } finally {
        cursor?.close()
        db?.close()
    }
    return records
}
