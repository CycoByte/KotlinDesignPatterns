package behavioural

import org.junit.jupiter.api.Test


class Printer(private val stringFormatterStrategy: (String)->String) {
    fun printString(string: String) {
        println(stringFormatterStrategy(string))
    }
}

val lowerCaseFormatter = { it: String -> it.lowercase() }
val upperCaseFormatter = { it: String -> it.uppercase() }

class StrategyTest {
    @Test
    fun testStrategy() {
        val inputString = "LOREM ipsum DOLOR sit amet"

        val lowerCasePrinter = Printer(lowerCaseFormatter)
        val upperCasePrinter = Printer(upperCaseFormatter)

        lowerCasePrinter.printString(inputString)
        upperCasePrinter.printString(inputString)
    }
}