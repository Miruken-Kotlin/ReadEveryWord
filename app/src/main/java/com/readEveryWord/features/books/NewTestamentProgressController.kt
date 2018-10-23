package com.readEveryWord.features.books

import com.android.databinding.library.baseAdapters.BR
import com.miruken.callback.Provides
import com.miruken.context.Scoped
import com.miruken.mvc.android.AndroidController
import com.readEveryWord.R
import com.readEveryWord.domain.Bible
import com.readEveryWord.domain.NewTestamentProgress

class NewTestamentProgressController
    @Provides @Scoped
    constructor(): AndroidController() {

    lateinit var bible: Bible
    lateinit var progress: NewTestamentProgress

    fun showProgress(data: Bible){
        bible    = data
        progress = NewTestamentProgress(bible)
        show(R.layout.new_testament_progress, BR.ctrl)
    }

    fun pop (){
        this.context?.end()
    }
}

