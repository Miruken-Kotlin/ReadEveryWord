package research.app1.a

import research.app1.infrastructure.Controller
import research.app1.messages.Message
import research.app1.R
import research.app1.b.BController
import research.app1.databinding.ABinding

class AController : Controller(), Message {

    fun showA() {
        push<ABinding>(R.layout.a).let {
            it.ctrl = this
        }
    }

    fun goToB() {
        BController().showB()
    }

    override var message: String = "Hello, from A Controller!"
}

