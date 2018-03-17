package research.app1.features.books

import research.app1.R
import research.app1.databinding.OldTestamentProgressBinding
import research.app1.domain.Bible
import research.app1.domain.OldTestamentProgress
import research.app1.infrastructure.Controller

class OldTestamentProgressController : Controller() {

    lateinit var bible: Bible
    lateinit var progress: OldTestamentProgress

    fun showProgress(data: Bible){
        bible    = data
        progress = OldTestamentProgress(bible)
        push<OldTestamentProgressBinding>(R.layout.old_testament_progress).also {
            it.ctrl = this
        }
    }
}

