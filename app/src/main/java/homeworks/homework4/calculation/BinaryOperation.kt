package homeworks.homework4.calculation

interface BinaryOperation : Signed {
    val priority: Int
    fun calculate(first: Double, second: Double): Double
}
