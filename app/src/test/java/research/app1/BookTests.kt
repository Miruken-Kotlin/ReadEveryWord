package research.app1

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import research.app1.domain.Book

class BookTests {

    private var book: Book? = null

    @Before
    fun setup(){
       book = Book("Foo", "Fo", 2)
    }

    @Test
    fun creates_chapters(){
        assertEquals(2, book?.chapterCount)
        assertEquals(2, book?.chapters?.count())
    }

    @Test
    fun is_not_started(){
        assertEquals(false, book?.started)
    }

    @Test
    fun is_started(){
        book?.chapters?.first()?.read = true
        assertEquals(true, book?.started)
    }

    @Test
    fun is_completed(){
        book?.chapters?.first()?.read = true
        book?.chapters?.last()?.read = true
        assertEquals(true, book?.completed)
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