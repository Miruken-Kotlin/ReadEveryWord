package com.readEveryWord.domain

import com.readEveryWord.features.calculateProgress

class NewTestamentProgress(bible: Bible){
    val gospels:    String = calculateProgress(bible.gospels)
    val acts:       String = calculateProgress(bible.acts)
    val epistles:   String = calculateProgress(bible.epistles)
    val revelation: String = calculateProgress(bible.revelation)
}
