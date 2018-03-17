package research.app1.features.books

import research.app1.R
import research.app1.databinding.NewTestamentProgressBinding
import research.app1.domain.Bible
import research.app1.domain.NewTestamentProgress
import research.app1.infrastructure.Controller

class NewTestamentProgressController : Controller() {

    lateinit var bible: Bible
    lateinit var progress: NewTestamentProgress

    fun showProgress(data: Bible){
        bible    = data
        progress = NewTestamentProgress(bible)
        push<NewTestamentProgressBinding>(R.layout.new_testament_progress).also {
            it.ctrl = this
        }
    }
}

