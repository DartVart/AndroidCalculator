package homeworks.homework4.calculation.binaryOperations

import homeworks.homework4.calculation.BinaryOperation

object Subtraction : BinaryOperation {
    override val sign = "-"
    override val priority = 0
    override fun calculate(first: Double, second: Double) = first - second
}
