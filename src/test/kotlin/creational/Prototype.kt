package creational

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


abstract class Shape: Cloneable {
    var id: String? = null
    var type: String? = null

    abstract fun draw()

    public override fun clone(): Any {
        var clone: Any? = null
        try {
            clone = super.clone()
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }
        return clone!!
    }
}

class Rectangle: Shape() {
    override fun draw() {
        println("Inside rectangle draw")
    }

    init {
        type = "Rectangle"
    }
}

class Square: Shape() {
    override fun draw() {
        println("Inside square draw")
    }

    init {
        type = "Square"
    }
}

class Circle: Shape() {
    override fun draw() {
        println("Inside circle draw")
    }

    init {
        type = "Circle"
    }
}

object ShapeCache {
    private val shapeMap = hashMapOf<String?, Shape>()

    fun loadCache() {
        val circle = Circle()
        val square = Square()
        val rectangle = Rectangle()

        shapeMap.apply {
            put("1", circle)
            put("2", square)
            put("3", rectangle)
        }
    }

    fun getShape(shapeId: String): Shape {
        val cachedShape = shapeMap.get(shapeId)
        return cachedShape?.clone() as Shape
    }
}

class PrototypeTest {
    @Test
    fun testPrototype() {
        ShapeCache.loadCache()
        val clonedS1 = ShapeCache.getShape("1")
        val clonedS2 = ShapeCache.getShape("2")
        val clonedS3 = ShapeCache.getShape("3")

        clonedS1.draw()
        clonedS2.draw()
        clonedS3.draw()

        Assertions.assertEquals("Circle", clonedS1.type)
        Assertions.assertEquals("Square", clonedS2.type)
        Assertions.assertEquals("Rectangle", clonedS3.type)
    }
}