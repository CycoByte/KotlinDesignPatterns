package structural

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

open class Equipment(
    open val price: Int,
    val name: String
)

open class Composite(name: String): Equipment(0, name) {
    private  val equipments = ArrayList<Equipment>()

    override val price: Int
        get() = equipments.map { it.price }.sum()

    fun add(equipment: Equipment) = apply { equipments.add(equipment) }
}

class Computer: Composite("PC")
class Processor: Equipment(1000, "Processor")
class HardDrive: Equipment(250, "HardDrive")
class Memory: Composite("Memory")
class ROM: Equipment(100, "HDD")
class RAM: Equipment(75, "INTERRAM 16GB")

class CompositeTest {
    @Test
    fun testComposite() {
        val memory = Memory()
            .add(ROM())
            .add(RAM())
        val pc = Computer()
            .add(memory)
            .add(Processor())
            .add(HardDrive())
        println("Total pc price: ${pc.price}")
        Assertions.assertEquals(1425, pc.price)
    }
}