package pattern_testing



fun main(args: Array<String>) {


}


interface ActionChainSAP {
    fun process(action: SecureItemAction)
}

enum class SecureItemActionStateSAP {
    NEW,
    PROCESSING,
    PENDING_USER_INPUT,
    COMPLETE,
    CANCELLED
}

interface SecureItemAction {
    val secureItem: SecureItemSAP
    var state: SecureItemActionStateSAP
}


//Need some sort of chain processor that will execute a chain of events that some might happen to be async
//Having to combine in with a state processor

sealed interface SecureItemSAP {

    val requiresMasterPassword: Boolean
    data class Password(override val requiresMasterPassword: Boolean): SecureItemSAP {
    }
    data class Key(override val requiresMasterPassword: Boolean): SecureItemSAP {
    }
    data class Note(override val requiresMasterPassword: Boolean): SecureItemSAP {
    }
    data class Card(override val requiresMasterPassword: Boolean): SecureItemSAP {
    }
}

class PasswordAuthAction(next: ActionChainSAP): ActionChainSAP {
    override fun process(action: SecureItemAction) {

    }
}

