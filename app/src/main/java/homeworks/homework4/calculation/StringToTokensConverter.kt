package homeworks.homework4.calculation

class StringToTokensConverter(private val calculatorUtilities: CalculatorUtilities) {
    private fun isAvailableNonNumericCharacter(symbol: Char): Boolean {
        val symbolAsString = symbol.toString()
        return (calculatorUtilities.isClosingBracket(symbolAsString) ||
                calculatorUtilities.isOpenBracket(symbolAsString) ||
                calculatorUtilities.isBinaryOperator(symbolAsString) ||
                calculatorUtilities.isUnaryOperator(symbolAsString) ||
                symbol == ' ')
    }

    private fun checkAndAddNumber(numberAsString: String, tokens: MutableList<String>) {
        if (calculatorUtilities.isPositiveNumber(numberAsString)) {
            tokens.add(numberAsString)
        } else {
            throw CalculatorException("Number reading error")
        }
    }

    fun convert(infixExpression: String): List<String> {
        val tokens = mutableListOf<String>()
        var currentNumber = ""
        infixExpression.forEach {
            when {
                calculatorUtilities.isNumericCharacter(it) -> currentNumber += it
                isAvailableNonNumericCharacter(it) -> {
                    if (currentNumber.isNotEmpty()) {
                        checkAndAddNumber(currentNumber, tokens)
                        currentNumber = ""
                    }
                    if (it != ' ') {
                        tokens.add(it.toString())
                    }
                }
                else -> throw CalculatorException("Unknown symbol")
            }
        }

        if (currentNumber.isNotEmpty()) {
            checkAndAddNumber(currentNumber, tokens)
        }

        return tokens
    }
}
