package homeworks.homework4.calculation.binaryOperations

import homeworks.homework4.calculation.BinaryOperation
import homeworks.homework4.calculation.CalculatorException

object Division : BinaryOperation {
    override val sign = "÷"
    override val priority = 1
    override fun calculate(first: Double, second: Double): Double {
        if (second == 0.0) {
            throw CalculatorException("Division by zero")
        }
        return first / second
    }
}
