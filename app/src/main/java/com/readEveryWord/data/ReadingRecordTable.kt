package com.readEveryWord.data

class ReadingRecordTable{
    companion object {
        const val Name      = "ReadingRecord"
        const val UserId    = "userId"
        const val BookId    = "bookId"
        const val ChapterId = "chapterId"
        const val Date      = "date"
        const val TimesRead = "timesRead"
        const val RemoteId  = "remoteId"

        const val CREATE_TABLE: String = """
            CREATE TABLE IF NOT EXISTS $Name (
                $BookId    INTEGER NOT NULL,
                $ChapterId INTEGER NOT NULL,
                $Date      INTEGER NOT NULL,
                $TimesRead INTEGER NOT NULL,
                $UserId    INTEGER,
                $RemoteId  INTEGER,
                PRIMARY KEY($BookId, $ChapterId, $Date, $TimesRead))
        """
    }
}
