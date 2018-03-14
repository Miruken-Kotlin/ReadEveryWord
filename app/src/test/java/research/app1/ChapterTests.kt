package research.app1

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import research.app1.domain.Chapter
import research.app1.features.ReadState

class ChapterTests {

    private var chapter: Chapter? = null

    @Before
    fun setup(){
       chapter = Chapter(1)
    }

    @Test
    fun is_not_read(){
        assertEquals(ReadState.NOT_STARTED, chapter?.readState)
    }

    @Test
    fun is_read(){
        chapter?.toggle()
        assertEquals(ReadState.COMPLETED, chapter?.readState)
    }
}