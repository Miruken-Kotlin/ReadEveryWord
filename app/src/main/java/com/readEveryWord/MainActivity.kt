package com.readEveryWord

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.miruken.callback.TypeHandlers
import com.miruken.context.Context
import com.miruken.mvc.Navigator
import com.miruken.mvc.android.ViewRegion
import com.readEveryWord.features.books.BooksController

class MainActivity : AppCompatActivity() {
    val _appContext = Context()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainRegion = ViewRegion(this)
        val navigator  = Navigator(mainRegion)
        _appContext.addHandlers(navigator, TypeHandlers)

        Controller.activity = this
        Controller.region   = Region(this)
        setContentView(Controller.region)
        BooksController().showBooks()
    }
}
