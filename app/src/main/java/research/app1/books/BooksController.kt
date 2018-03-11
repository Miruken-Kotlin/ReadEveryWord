package research.app1.books

import research.app1.R
import research.app1.infrastructure.Controller

class BooksController : Controller() {
    fun showBooks(){
        push(R.layout.books)
    }
}