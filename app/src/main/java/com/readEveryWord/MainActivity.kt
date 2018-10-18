package com.readEveryWord

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.miruken.callback.TypeHandlers
import com.miruken.context.Context
import com.miruken.mvc.Navigator
import com.miruken.mvc.android.ViewRegion
import com.miruken.mvc.next
import com.readEveryWord.features.books.BooksController

class MainActivity : AppCompatActivity() {
    private val _appContext = Context()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainRegion = ViewRegion(this)

        _appContext.addHandlers(
                Navigator(mainRegion),
                TypeHandlers)

        setContentView(mainRegion)
        
        _appContext.next<BooksController> { showBooks() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _appContext.end()
    }
}
