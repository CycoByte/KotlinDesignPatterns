package structural

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

interface Device {
    var volume: Int
    fun getName(): String
}

class Radio: Device {
    override var volume: Int = 0

    override fun getName(): String = "Radio: $this"
}

class TV: Device {
    override var volume: Int = 0

    override fun getName(): String = "TV: $this"
}

interface Remote {
    fun volumeUp()
    fun volumeDown()
}

class BasicRemote(val device: Device): Remote {
    override fun volumeUp() {
        device.volume++
        println("Device ${device.getName()} volume up: ${device.volume}")
    }

    override fun volumeDown() {
        device.volume--
        println("Device ${device.getName()} volume down: ${device.volume}")
    }
}

class BridgeTest {
    @Test
    fun testBridge() {
        val tv = TV()
        val radio = Radio()

        val tvRemote = BasicRemote(tv)
        val radioRemote = BasicRemote(radio)

        tvRemote.volumeUp()
        tvRemote.volumeUp()
        tvRemote.volumeDown()


        radioRemote.volumeUp()
        radioRemote.volumeUp()
        radioRemote.volumeDown()
        radioRemote.volumeDown()

        Assertions.assertEquals(1, tv.volume)
        Assertions.assertEquals(0, radio.volume)
    }
}