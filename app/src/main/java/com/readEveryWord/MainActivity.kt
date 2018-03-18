package com.readEveryWord

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.readEveryWord.features.books.BooksController
import com.readEveryWord.infrastructure.Controller
import com.readEveryWord.infrastructure.Region

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Controller.activity = this
        Controller.region   = Region(this)
        setContentView(Controller.region)
        BooksController().showBooks()
    }
}
