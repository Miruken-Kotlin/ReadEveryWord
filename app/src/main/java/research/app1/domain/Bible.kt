package research.app1.domain

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.Observable
import research.app1.BR
import research.app1.features.calculateProgress

class Bible : BaseObservable() {

    val books = arrayListOf(
        Book("Genesis",         "Gen", 50),
        Book("Exodus",          "Exo", 40),
        Book("Leviticus",       "Lev", 27),
        Book("Numbers",         "Num", 36),
        Book("Deuteronomy",     "Deu", 34),
        Book("Joshua",          "Jos", 24),
        Book("Judges",          "Jdg", 21),
        Book("Ruth",            "Rth", 4),
        Book("1 Samuel",        "1Sa", 31),
        Book("2 Samuel",        "2Sa", 24),
        Book("1 Kings",         "1Ki", 22),
        Book("2 Kings",         "2Ki", 25),
        Book("1 Chronicles",    "1Ch", 29),
        Book("2 Chronicles",    "2Ch", 36),
        Book("Ezra",            "Eza", 10),
        Book("Nehemiah",        "Neh", 13),
        Book("Esther",          "Est", 10),
        Book("Job",             "Job", 42),
        Book("Psalm",           "Psa", 150),
        Book("Proverbs",        "Pro", 31),
        Book("Ecclesiastes",    "Ecc", 12),
        Book("Song of Solomon", "SOS", 8),
        Book("Isaiah",          "Isa", 66),
        Book("Jeremiah",        "Jer", 52),
        Book("Lamentations",    "Lam", 5),
        Book("Ezekiel",         "Ezk", 48),
        Book("Daniel",          "Dan", 12),
        Book("Hosea",           "Hos", 14),
        Book("Joel",            "Joe", 3),
        Book("Amos",            "Amo", 9),
        Book("Obadiah",         "Obd", 1),
        Book("Jonah",           "Jon", 4),
        Book("Micah",           "Mic", 7),
        Book("Nahum",           "Nah", 3),
        Book("Habakkuk",        "Hab", 3),
        Book("Zephaniah",       "Zep", 3),
        Book("Haggai",          "Hag", 2),
        Book("Zechariah",       "Zch", 14),
        Book("Malachi",         "Mal", 4),
        Book("Matthew",         "Mat", 28),
        Book("Mark",            "Mar", 16),
        Book("Luke",            "Luk", 24),
        Book("John",            "Joh", 21),
        Book("Acts",            "Act", 28),
        Book("Romans",          "Rom", 16),
        Book("1 Corinthians",   "1Co", 16),
        Book("2 Corinthians",   "2Co", 13),
        Book("Galations",       "Gal", 6),
        Book("Ephesians",       "Eph", 6),
        Book("Philippians",     "Phi", 4),
        Book("Colossians",      "Col", 4),
        Book("1 Thessalonians", "1Th", 5),
        Book("2 Thessalonians", "2Th", 3),
        Book("1 Timothy",       "1Ti", 6),
        Book("2 Timothy",       "2Ti", 4),
        Book("Titus",           "Tit", 3),
        Book("Philemon",        "Phm", 1),
        Book("Hebrews",         "Heb", 13),
        Book("James",           "Jam", 5),
        Book("1 Peter",         "1Pe", 5),
        Book("2 Peter",         "2Pe", 3),
        Book("1 John",          "1Jo", 5),
        Book("2 John",          "2Jo", 1),
        Book("3 John",          "3Jo", 1),
        Book("Jude",            "Jud", 1),
        Book("Revelation",      "Rev", 22)
    )

    private val chapterCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: Observable, i: Int) {
            notifyPropertyChanged(BR.oldTestamentProgress)
            notifyPropertyChanged(BR.newTestamentProgress)
        }
    }

    init {
        books.flatMap {
            it.chapters
        }.forEach{
            it.addOnPropertyChangedCallback(chapterCallback)
        }
    }

    val oldTestament  = books.take(39)
    val law           = oldTestament.take(5)
    val history       = oldTestament.drop(5).take(12)
    val wisdom        = oldTestament.drop(17).take(5)
    val majorProphets = oldTestament.drop(22).take(5)
    val minorProphets = oldTestament.takeLast(12)

    val newTestament = books.takeLast(27)
    val gospels      = newTestament.take(4)
    val acts         = newTestament.drop(4).take(1)
    val epistles     = newTestament.drop(5).take(21)
    val revelation   = newTestament.last()

    @Bindable
    var oldTestamentProgress: String = ""
        get(){
            return calculateProgress(oldTestament)
        }

    @Bindable
    var newTestamentProgress: String = ""
        get(){
            return calculateProgress(newTestament)
        }
}

class OldTestamentProgress(bible: Bible){
    val law:           String = calculateProgress(bible.law)
    val history:       String = calculateProgress(bible.history)
    val wisdom:        String = calculateProgress(bible.wisdom)
    val majorProphets: String = calculateProgress(bible.majorProphets)
    val minorProphets: String = calculateProgress(bible.minorProphets)
}

class NewTestamentProgress(bible: Bible){
    val gospels:    String = calculateProgress(bible.gospels)
    val acts:       String = calculateProgress(bible.acts)
    val epistles:   String = calculateProgress(bible.epistles)
    val revelation: String = calculateProgress(bible.revelation)
}

