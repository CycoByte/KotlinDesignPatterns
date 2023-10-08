package structural

import org.junit.jupiter.api.Test


interface Image {
    fun display()
}

class RealImage(private val filename: String): Image {
    override fun display() {
        println("RealImage: Displaying: $filename")
    }
    private fun loadFromDisk(filename: String) {
        println("RealImage: Loading image: $filename")
    }

    init {
        loadFromDisk(filename)
    }
}

class ProxyImage(private val filename: String): Image {
    private var readImage: Image? = null

    override fun display() {
        println("ProxyImage: Displaying $filename")
        if(readImage == null) {
            readImage = RealImage(filename)
        }
        readImage!!.display()
    }
}

class ProxyTest() {
    @Test
    fun proxyTest() {
        val image = ProxyImage("test.jpg")
        image.display()
        println("-----------")
        image.display()
    }
}

