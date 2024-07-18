package pattern_testing

fun main(args: Array<String>) {

    val secureItems = listOf(
        SecureItem.Password,
        SecureItem.Password,
        SecureItem.Password,
        SecureItem.Key,
        SecureItem.Key,
        SecureItem.Password,
        SecureItem.Password,
        SecureItem.Note,
        SecureItem.Note,
        SecureItem.Card,
        SecureItem.Card
    )

    val exportVisitor = WriteToFileVisitor()
    val generateQrData = GenerateQRVisitor()
    val itemLogger = LogItemVisitor()

    val mappedQRData = secureItems.map { it.visit(generateQrData) + "\n" }
    val mappedFileData = secureItems.map { it.visit(exportVisitor) }.toHashSet().first()

    println("----- DATA LOG ------")
    secureItems.onEach {
        it.visit(itemLogger)
    }
    println("----- QR DATA ------")
    println(mappedQRData.joinToString())
    println("----- WRITE FILE ------")
    println("Status: $mappedFileData")
}


sealed interface SecureItem: SecureItemContract {

    data object Password: SecureItem {
        override fun <R> visit(visitor: ItemActionVisitor<R>): R {
            return visitor.accept(this)
        }
    }
    data object Key: SecureItem {
        override fun <R> visit(visitor: ItemActionVisitor<R>): R {
            return visitor.accept(this)
        }
    }
    data object Note: SecureItem {
        override fun <R> visit(visitor: ItemActionVisitor<R>): R {
            return visitor.accept(this)
        }
    }
    data object Card: SecureItem {
        override fun <R> visit(visitor: ItemActionVisitor<R>): R {
            return visitor.accept(this)
        }
    }
}

interface ItemActionVisitor<out T> {
    fun accept(contract: SecureItem.Card): T
    fun accept(contract: SecureItem.Key): T
    fun accept(contract: SecureItem.Password): T
    fun accept(contract: SecureItem.Note): T
}

interface SecureItemContract {
    fun <R> visit(visitor: ItemActionVisitor<R>): R
}

class LogItemVisitor: ItemActionVisitor<Unit> {
    override fun accept(contract: SecureItem.Card) {
        println("Logging: $contract")
    }

    override fun accept(contract: SecureItem.Key) {
        println("Logging: $contract")
    }

    override fun accept(contract: SecureItem.Password) {
        println("Logging: $contract")
    }

    override fun accept(contract: SecureItem.Note) {
        println("Logging: $contract")
    }
}

class GenerateQRVisitor: ItemActionVisitor<String> {
    override fun accept(contract: SecureItem.Card): String {
        println("Create QR for Card data: $contract")
        return "QR Payload for Card"
    }
    override fun accept(contract: SecureItem.Key): String {
        println("Create QR for Key data: $contract")
        return "QR Payload for Key"
    }
    override fun accept(contract: SecureItem.Password): String {
        println("Create QR for Password data: $contract")
        return "QR Payload for Password"
    }
    override fun accept(contract: SecureItem.Note): String {
        println("Create QR for Note data: $contract")
        return "QR Payload for Note"
    }
}

class WriteToFileVisitor: ItemActionVisitor<Boolean> {
    override fun accept(contract: SecureItem.Card): Boolean {
        println("Writing to cards file data: $contract")
        return true
    }
    override fun accept(contract: SecureItem.Key): Boolean {
        println("Writing to keys file data: $contract")
        return true
    }
    override fun accept(contract: SecureItem.Password): Boolean {
        println("Writing to passwords file data: $contract")
        return true
    }
    override fun accept(contract: SecureItem.Note): Boolean {
        println("Writing to notes file data: $contract")
        return true
    }
}