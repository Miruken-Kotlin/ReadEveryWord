package research.app1.domain

class Book(val longName: String, val shortName: String, val chapterCount: Int){
    val chapters = (1..chapterCount).map { Chapter(it) }

    val started : Boolean
        get() = chapters.any { x -> x.read }

    val completed : Boolean
        get() = chapters.all { x -> x.read }
}
