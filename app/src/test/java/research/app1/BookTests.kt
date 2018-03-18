package research.app1

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import research.app1.domain.Book
import research.app1.features.ReadState

class BookTests {

    private var book: Book? = null

    @Before
    fun setup(){
       book = Book(1, "Foo", "Fo", 2)
    }

    @Test
    fun creates_chapters(){
        assertEquals(2, book?.chapterCount)
        assertEquals(2, book?.chapters?.count())
    }

    @Test
    fun is_not_started(){
        assertEquals(ReadState.NOT_STARTED, book?.readState)
    }

    @Test
    fun is_started(){
        book?.chapters?.first()?.read = true
        assertEquals(ReadState.STARTED, book?.readState)
    }

    @Test
    fun is_completed(){
        book?.chapters?.first()?.read = true
        book?.chapters?.last()?.read = true
        assertEquals(ReadState.COMPLETED, book?.readState)
    }

    @Test
    fun progress_At_0(){
        assertEquals("0%", book?.progress)
    }

    @Test
    fun progress_At_50(){
        book?.chapters?.first()?.read = true
        assertEquals("50%", book?.progress)
    }

    @Test
    fun progress_At_100(){
        book?.chapters?.first()?.read = true
        book?.chapters?.last()?.read = true
        assertEquals("100%", book?.progress)
    }
}