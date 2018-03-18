package research.app1.features.chapters

import android.view.ViewGroup
import research.app1.R
import research.app1.data.ReadingRecordData
import research.app1.data.queries.createReadingRecord
import research.app1.databinding.ChaptersChapterBinding
import research.app1.domain.Book
import research.app1.domain.Chapter
import research.app1.infrastructure.Controller
import java.util.*

class ChapterController(val book: Book, val chapter: Chapter) : Controller() {
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
                null))
        chapter.read = true
    }
}