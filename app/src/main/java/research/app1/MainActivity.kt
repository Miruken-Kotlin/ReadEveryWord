package research.app1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import research.app1.books.BooksController
import research.app1.infrastructure.Controller
import research.app1.infrastructure.Region

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Controller.activity = this
        Controller.region   = Region(this)
        setContentView(Controller.region)
        BooksController().showBooks()
    }
}
