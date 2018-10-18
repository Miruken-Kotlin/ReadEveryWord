package com.readEveryWord.features.books

import com.readEveryWord.R
import com.readEveryWord.databinding.NewTestamentProgressBinding
import com.readEveryWord.domain.Bible
import com.readEveryWord.domain.NewTestamentProgress

class NewTestamentProgressController : Controller() {

    lateinit var bible: Bible
    lateinit var progress: NewTestamentProgress

    fun showProgress(data: Bible){
        bible    = data
        progress = NewTestamentProgress(bible)
        push<NewTestamentProgressBinding>(R.layout.new_testament_progress).also {
            it?.ctrl = this
        }
    }
}

