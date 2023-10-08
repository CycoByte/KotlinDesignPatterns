package behavioural

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


interface ReportElement {
    fun <R> accept(visitor: ReportVisitor<R>): R
}

interface ReportVisitor<out R> {
    fun visit(contract: FixedFixedPriceContract): R
    fun visit(contract: TimeAndMaterialsContract): R
    fun visit(contract: SupportContract): R
}

//Contracts
class FixedFixedPriceContract(val costPErYear:Long): ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

class TimeAndMaterialsContract(val costPerHour: Long, val hours: Long): ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

class SupportContract(val costPerMonth: Long): ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

//Visitors
class MonthlyCostReportVisitor: ReportVisitor<Long> {
    override fun visit(contract: FixedFixedPriceContract): Long = contract.costPErYear / 12

    override fun visit(contract: TimeAndMaterialsContract): Long = contract.costPerHour * contract.hours

    override fun visit(contract: SupportContract): Long = contract.costPerMonth
}

class YearlyCostReportVisitor: ReportVisitor<Long> {
    override fun visit(contract: FixedFixedPriceContract): Long = contract.costPErYear

    override fun visit(contract: TimeAndMaterialsContract): Long = contract.costPerHour * contract.hours

    override fun visit(contract: SupportContract): Long = contract.costPerMonth * 12
}

class VisitorTest {
    @Test
    fun testVisitor() {
        val projectAlpha = FixedFixedPriceContract(10_000)
        val projectBeta = SupportContract(500)
        val projectGamma = TimeAndMaterialsContract(150, 10)
        val projectKappa = TimeAndMaterialsContract(50, 50)

        val projects = arrayListOf(projectAlpha, projectBeta, projectGamma, projectKappa)

        val monthlyCostReportVisitor = MonthlyCostReportVisitor()
        val monthlyCost = projects.map { it.accept(monthlyCostReportVisitor) }.sum()
        println("Monthly cost: $monthlyCost")
        Assertions.assertEquals(5333, monthlyCost)

        val yearlyCostVisitor = YearlyCostReportVisitor()
        val yearlyCost = projects.map { it.accept(yearlyCostVisitor) }.sum()
        println("Yearly cost: $yearlyCost")
        Assertions.assertEquals(20_000, yearlyCost)
    }
}