package com.readEveryWord.features.books

import com.readEveryWord.R
import com.readEveryWord.databinding.OldTestamentProgressBinding
import com.readEveryWord.domain.Bible
import com.readEveryWord.domain.OldTestamentProgress

class OldTestamentProgressController : Controller() {

    lateinit var bible: Bible
    lateinit var progress: OldTestamentProgress

    fun showProgress(data: Bible){
        bible    = data
        progress = OldTestamentProgress(bible)
        push<OldTestamentProgressBinding>(R.layout.old_testament_progress).also {
            it?.ctrl = this
        }
    }
}

