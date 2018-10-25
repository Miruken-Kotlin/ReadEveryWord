package com.readEveryWord.features.books

import com.android.databinding.library.baseAdapters.BR
import com.miruken.callback.Provides
import com.miruken.context.Scoped
import com.miruken.mvc.android.AndroidController
import com.readEveryWord.R
import com.readEveryWord.domain.Bible
import com.readEveryWord.domain.OldTestamentProgress

class OldTestamentProgressController
    @Provides @Scoped
    constructor(val bible: Bible): AndroidController() {

    val progress = OldTestamentProgress(bible)

    fun show() = show(R.layout.old_testament_progress, BR.ctrl)
}

