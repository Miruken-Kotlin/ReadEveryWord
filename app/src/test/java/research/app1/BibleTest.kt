package research.app1.tests

import org.junit.Test

import org.junit.Assert.*
import research.app1.domain.Bible

class BibleTest {
    @Test
    fun bible_has_66_books() {
        assertEquals(66, Bible().books.count())
    }

    @Test
    fun oldTestament_has_39_books() {
        assertEquals(39, Bible().oldTestament.count())
    }

    @Test
    fun oldTestament_starts_with_genesis() {
        assertEquals("Genesis", Bible().oldTestament.first().longName)
    }

    @Test
    fun oldTestament_ends_with_malachi() {
        assertEquals("Malachi", Bible().oldTestament.last().longName)
    }

    @Test
    fun newTestament_has_27_books() {
        assertEquals(27, Bible().newTestament.count())
    }

    @Test
    fun newTestament_starts_with_matthew() {
        assertEquals("Matthew", Bible().newTestament.first().longName)
    }

    @Test
    fun oldTestament_ends_with_revelation() {
        assertEquals("Revelation", Bible().newTestament.last().longName)
    }
}
