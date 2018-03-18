package research.app1.data.queries

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import research.app1.data.DataContext
import research.app1.data.ReadingRecordData
import research.app1.data.ReadingRecordTable
import research.app1.infrastructure.Controller

fun createReadingRecord(data: ReadingRecordData){
    var db: SQLiteDatabase? = null
    try {
        db = DataContext(Controller.activity).writableDatabase
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
