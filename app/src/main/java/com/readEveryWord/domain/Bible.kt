package com.readEveryWord.domain

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.readEveryWord.features.calculateProgress
import com.readEveryWord.BR

class Bible : BaseObservable() {

    val books = arrayListOf(
        Book(0,  "Genesis",         "Gen", 50),
        Book(1,  "Exodus",          "Exo", 40),
        Book(2,  "Leviticus",       "Lev", 27),
        Book(3,  "Numbers",         "Num", 36),
        Book(4,  "Deuteronomy",     "Deu", 34),
        Book(5,  "Joshua",          "Jos", 24),
        Book(6,  "Judges",          "Jdg", 21),
        Book(7,  "Ruth",            "Rth", 4),
        Book(8,  "1 Samuel",        "1Sa", 31),
        Book(9,  "2 Samuel",        "2Sa", 24),
        Book(10, "1 Kings",         "1Ki", 22),
        Book(11, "2 Kings",         "2Ki", 25),
        Book(12, "1 Chronicles",    "1Ch", 29),
        Book(13, "2 Chronicles",    "2Ch", 36),
        Book(14, "Ezra",            "Eza", 10),
        Book(15, "Nehemiah",        "Neh", 13),
        Book(16, "Esther",          "Est", 10),
        Book(17, "Job",             "Job", 42),
        Book(18, "Psalm",           "Psa", 150),
        Book(19, "Proverbs",        "Pro", 31),
        Book(20, "Ecclesiastes",    "Ecc", 12),
        Book(21, "Song of Solomon", "SOS", 8),
        Book(22, "Isaiah",          "Isa", 66),
        Book(23, "Jeremiah",        "Jer", 52),
        Book(24, "Lamentations",    "Lam", 5),
        Book(25, "Ezekiel",         "Ezk", 48),
        Book(26, "Daniel",          "Dan", 12),
        Book(27, "Hosea",           "Hos", 14),
        Book(28, "Joel",            "Joe", 3),
        Book(29, "Amos",            "Amo", 9),
        Book(30, "Obadiah",         "Obd", 1),
        Book(31, "Jonah",           "Jon", 4),
        Book(32, "Micah",           "Mic", 7),
        Book(33, "Nahum",           "Nah", 3),
        Book(34, "Habakkuk",        "Hab", 3),
        Book(35, "Zephaniah",       "Zep", 3),
        Book(36, "Haggai",          "Hag", 2),
        Book(37, "Zechariah",       "Zch", 14),
        Book(38, "Malachi",         "Mal", 4),
        Book(39, "Matthew",         "Mat", 28),
        Book(40, "Mark",            "Mar", 16),
        Book(41, "Luke",            "Luk", 24),
        Book(42, "John",            "Joh", 21),
        Book(43, "Acts",            "Act", 28),
        Book(44, "Romans",          "Rom", 16),
        Book(45, "1 Corinthians",   "1Co", 16),
        Book(46, "2 Corinthians",   "2Co", 13),
        Book(47, "Galations",       "Gal", 6),
        Book(48, "Ephesians",       "Eph", 6),
        Book(49, "Philippians",     "Phi", 4),
        Book(50, "Colossians",      "Col", 4),
        Book(51, "1 Thessalonians", "1Th", 5),
        Book(52, "2 Thessalonians", "2Th", 3),
        Book(53, "1 Timothy",       "1Ti", 6),
        Book(54, "2 Timothy",       "2Ti", 4),
        Book(55, "Titus",           "Tit", 3),
        Book(56, "Philemon",        "Phm", 1),
        Book(57, "Hebrews",         "Heb", 13),
        Book(58, "James",           "Jam", 5),
        Book(59, "1 Peter",         "1Pe", 5),
        Book(60, "2 Peter",         "2Pe", 3),
        Book(61, "1 John",          "1Jo", 5),
        Book(62, "2 John",          "2Jo", 1),
        Book(63, "3 John",          "3Jo", 1),
        Book(64, "Jude",            "Jud", 1),
        Book(65, "Revelation",      "Rev", 22)
    )

    private val chapterCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: Observable, i: Int) {
            notifyPropertyChanged(BR.oldTestamentProgress)
            notifyPropertyChanged(BR.newTestamentProgress)
        }
    }

    init {
        books.flatMap { it.chapters }.forEach {
            it.addOnPropertyChangedCallback(chapterCallback)
        }
    }

    val oldTestament  = books.take(39)
    val law           = oldTestament.take(5)
    val history       = oldTestament.asSequence().drop(5).take(12).toList()
    val wisdom        = oldTestament.asSequence().drop(17).take(5).toList()
    val majorProphets = oldTestament.asSequence().drop(22).take(5).toList()
    val minorProphets = oldTestament.takeLast(12)

    val newTestament = books.takeLast(27)
    val gospels      = newTestament.take(4)
    val acts         = newTestament.asSequence().drop(4).take(1).toList()
    val epistles     = newTestament.asSequence().drop(5).take(21).toList()
    val revelation   = newTestament.last()

    @Bindable
    var oldTestamentProgress = ""
        get() = calculateProgress(oldTestament)

    @Bindable
    var newTestamentProgress = ""
        get() = calculateProgress(newTestament)
}
