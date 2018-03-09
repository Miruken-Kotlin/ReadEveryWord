package research.app1.b

import research.app1.messages.Message
import research.app1.R
import research.app1.a.AController
import research.app1.infrastructure.Controller
import research.app1.databinding.BBinding

class BController : Controller(), Message {

    fun showB(){
        push<BBinding>(R.layout.b).let{
            it.ctrl = this
        }
    }

    fun goToA() {
        AController().showA()
    }

    override var message: String = "Hello, from B Controller!"
}