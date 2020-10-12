package homeworks.homework4.calculation.unaryOperations

import homeworks.homework4.calculation.UnaryOperation

object InverseByAddition : UnaryOperation {
    override val sign = "!"
    override val additionalSign = "-"
    override fun calculate(value: Double) = -value
}
