package com.readEveryWord.features

import com.readEveryWord.domain.Book

fun calculateProgress(book : Book) : String {
    return calculateProgress(book.readCount, book.chapterCount)
}

fun calculateProgress(books : List<Book>) : String {
   return calculateProgress(
           books.sumBy { it.readCount },
           books.sumBy { it.chapterCount })
}

fun calculateProgress(current: Int, total: Int) : String {
    val percent = current.toFloat() / total.toFloat() * 100
    return "%.0f".format(percent) + "%"
}
