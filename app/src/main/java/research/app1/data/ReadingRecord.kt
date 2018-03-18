package research.app1.data

data class ReadingRecordData(
    val bookId:    Int,
    val chapterId: Int,
    val date:      Long,
    val timesRead: Int,
    val userId:    Long?,
    val remoteId:  Long?
)
