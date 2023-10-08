package behavioural

import org.junit.jupiter.api.Test

interface Command {
    fun execute()
}

class OrderAddCommand(val id: Long): Command {
    override fun execute() {
        println("Adding order with id: $id")
    }
}

class OrderPayCommand(val id: Long): Command {
    override fun execute() {
        println("Pay order with id: $id")
    }
}

class CommandProcessor {
    private val queue = arrayListOf<Command>()

    fun addToQueue(command: Command): CommandProcessor = apply { queue.add(command) }
    fun processCommand(): CommandProcessor = apply {
        queue.forEach { it.execute() }
        queue.clear()
    }
}

class CommandTest {
    @Test
    fun testCommand() {
        CommandProcessor()
            .addToQueue(OrderAddCommand(13445L))
            .addToQueue(OrderAddCommand(12445L))
            .addToQueue(OrderAddCommand(345L))
            .addToQueue(OrderPayCommand(13445L))
            .addToQueue(OrderPayCommand(345L))
            .addToQueue(OrderPayCommand(12445L))
            .processCommand()
    }
}