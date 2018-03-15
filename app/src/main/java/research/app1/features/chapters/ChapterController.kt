package research.app1.features.chapters

import android.view.ViewGroup
import research.app1.R
import research.app1.databinding.ChaptersChapterBinding
import research.app1.domain.Chapter
import research.app1.infrastructure.Controller

class ChapterController(val chapter: Chapter) : Controller() {
    fun addToView(parent: ViewGroup) {
        bind<ChaptersChapterBinding>(R.layout.chapters_chapter, parent).foo = this
    }
}