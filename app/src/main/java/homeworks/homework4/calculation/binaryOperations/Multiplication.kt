package homeworks.homework4.calculation.binaryOperations

import homeworks.homework4.calculation.BinaryOperation

object Multiplication : BinaryOperation {
    override val sign = "×"
    override val priority = 1
    override fun calculate(first: Double, second: Double) = first * second
}
