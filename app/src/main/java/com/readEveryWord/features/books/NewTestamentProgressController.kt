package com.readEveryWord.features.books

import com.miruken.callback.Provides
import com.miruken.context.Scoped
import com.miruken.mvc.android.AndroidController
import com.readEveryWord.R
import com.readEveryWord.BR
import com.readEveryWord.domain.Bible
import com.readEveryWord.domain.NewTestamentProgress

class NewTestamentProgressController
    @Provides @Scoped
    constructor(val bible: Bible): AndroidController() {

    val progress = NewTestamentProgress(bible)

    fun show() = showR(R.layout.new_testament_progress, BR.ctrl)
}

