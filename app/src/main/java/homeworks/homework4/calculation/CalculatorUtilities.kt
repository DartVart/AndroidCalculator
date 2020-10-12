package homeworks.homework4.calculation

class CalculatorUtilities(
    private val binaryOperations: List<BinaryOperation>,
    private val unaryOperations: List<UnaryOperation>
) {
    private val closingBracket = ")"
    private val openBracket = "("
    private val numberSeparator = "."

    fun findBinaryOperation(sign: String) = binaryOperations.find { it.sign == sign }
    fun findUnaryOperationByMainSign(sign: String) = unaryOperations.find { it.sign == sign }
    fun findUnaryOperationByAdditionalSign(sign: String) =
        unaryOperations.find { it.additionalSign == sign }

    fun isBinaryOperator(sign: String) = findBinaryOperation(sign) != null
    fun isUnaryOperator(sign: String) =
        findUnaryOperationByMainSign(sign) != null || findUnaryOperationByAdditionalSign(sign) != null

    fun getPriority(operator: String) = findBinaryOperation(operator)?.priority
    fun isClosingBracket(token: String) = token == closingBracket
    fun isOpenBracket(token: String) = token == openBracket
    fun isPositiveNumber(string: String) = Regex("""\d+(\.\d+)?""").matches(string)
    fun isNumericCharacter(symbol: Char) = symbol.isDigit() || symbol.toString() == numberSeparator
}
