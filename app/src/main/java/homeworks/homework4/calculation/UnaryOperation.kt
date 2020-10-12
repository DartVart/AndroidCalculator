package homeworks.homework4.calculation

/**
 * An additional character is used if the designation is the same as another operator.
 * A unique symbol is written in the [sign] property.
 * */
interface UnaryOperation : Signed {
    val additionalSign: String
    fun calculate(value: Double): Double
}
