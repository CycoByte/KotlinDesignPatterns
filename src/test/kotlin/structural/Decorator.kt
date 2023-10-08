package structural

import org.junit.jupiter.api.Test

interface CoffeeMachine {
    fun makeSmallCoffee()
    fun makeLargeCoffee()
}

class NormalCoffeeMachine(): CoffeeMachine {
    override fun makeSmallCoffee() {
        println("NormalCoffeeMachine making small coffee")
    }

    override fun makeLargeCoffee() {
        println("NormalCoffeeMachine making large coffee")
    }
}

//Decorator
class AdvancedCoffeeMachine(private val coffeeMachine: CoffeeMachine): CoffeeMachine by coffeeMachine {
    override fun makeSmallCoffee() {
        println("The best coffee! making small coffee")
    }

    override fun makeLargeCoffee() {
        println("The best coffee! making large coffee")
    }
    //Extend behaviour
    fun makeCoffeeWithMilk() {
        println("The best coffee! making milk coffee")
        coffeeMachine.makeSmallCoffee()
        println("Adding milk")
    }
}

class DecoratorTest {
    @Test
    fun testDecorator() {
        val normalMachine = NormalCoffeeMachine()
        val advCoffeeMachine = AdvancedCoffeeMachine(normalMachine)

        advCoffeeMachine.makeLargeCoffee()
        advCoffeeMachine.makeSmallCoffee()
        advCoffeeMachine.makeCoffeeWithMilk()

        normalMachine.makeSmallCoffee()
        normalMachine.makeLargeCoffee()
    }
}