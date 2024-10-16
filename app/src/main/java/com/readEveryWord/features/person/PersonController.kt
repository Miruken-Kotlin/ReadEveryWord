package com.readEveryWord.features.person

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.miruken.callback.Provides
import com.miruken.context.Scoped
import com.miruken.mvc.android.AndroidController
import com.readEveryWord.R
import com.readEveryWord.BR
import com.readEveryWord.data.BibleRepository
import com.readEveryWord.domain.Book
import com.readEveryWord.domain.Chapter

class PersonController
    @Provides @Scoped constructor(
    ) : AndroidController() {

    val person = PersonData().apply {
        firstName.value = "Michael"
        lastName.value  = "Dudley"
    }

    fun editPerson() {
        showR(R.layout.person, BR.ctrl)
    }
}

class PersonData : ViewModel(){
    val firstName = MutableLiveData<String>()
    val lastName  = MutableLiveData<String>()
    val fullName  = Transformations.map(firstName) { firstName ->
        "$firstName lastName"
    }
}