package com.readEveryWord

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import com.readEveryWord.domain.Book
import com.readEveryWord.features.calculateProgress

class ProgressTests {

    private var book: Book = Book(1,"Foo", "Fo", 2)

    @Before
    fun setup(){
       book = Book(1,"Foo", "Fo", 2)
    }

    @Test
    fun progress_At_0(){
        val expected = "0%"
        assertEquals(expected, book.progress)
        assertEquals(expected, calculateProgress(book))
        assertEquals(expected, calculateProgress(listOf(book)))
    }

    @Test
    fun progress_At_50(){
        book.chapters.first().read = true
        val expected = "50%"
        assertEquals(expected, book.progress)
        assertEquals(expected, calculateProgress(book))
        assertEquals(expected, calculateProgress(listOf(book)))
    }

    @Test
    fun progress_At_100(){
        book.chapters.first().read = true
        book.chapters.last().read = true
        val expected = "100%"
        assertEquals(expected, book.progress)
        assertEquals(expected, calculateProgress(book))
        assertEquals(expected, calculateProgress(listOf(book)))
    }
}