package research.app1.features

fun calculateProgress(current: Int, total: Int) : String {
    val percent = current.toFloat() / total.toFloat() * 100
    return "%.0f".format(percent) + "%"
}
