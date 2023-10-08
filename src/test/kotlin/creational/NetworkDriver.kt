package creational

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

object NetworkDriver {

    init {
        println("Initializing: $this")
    }

    fun log(): NetworkDriver = apply { println("Network driver: $this") }

}

class SingletonTest {
    @Test
    fun testSingleton() {
        println("Start")
        val networkDriver1 = NetworkDriver.log()
        val networkDriver2 = NetworkDriver.log()

        Assertions.assertSame(networkDriver1, NetworkDriver)
        Assertions.assertSame(networkDriver2, NetworkDriver)
    }
}