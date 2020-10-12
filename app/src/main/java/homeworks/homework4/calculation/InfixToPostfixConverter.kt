package homeworks.homework4.calculation

import homeworks.homework4.calculation.unaryOperations.InverseByAddition
import java.util.Stack

/**
 * Works with a list of string tokens.
 * */
class InfixToPostfixConverter(private val calculatorUtilities: CalculatorUtilities) {
    fun convert(infixExpression: List<String>): MutableList<String> {
        val tokens = Stack<String>()
        val postfixExpression = mutableListOf<String>()
        infixExpression.forEachIndexed { index, token ->
            when {
                isUnaryOperator(infixExpression, index) -> handleUnaryOperator(token, tokens)
                calculatorUtilities.isBinaryOperator(token) -> handleOperator(
                    token,
                    tokens,
                    postfixExpression
                )
                calculatorUtilities.isClosingBracket(token) -> handleClosingBracket(
                    tokens,
                    postfixExpression
                )
                calculatorUtilities.isOpenBracket(token) -> tokens.push(token)
                calculatorUtilities.isPositiveNumber(token) -> postfixExpression.add(token)
                else -> throw CalculatorException("Unknown token")
            }
        }

        while (tokens.isNotEmpty()) {
            val currentToken = tokens.pop()
            if (calculatorUtilities.isOpenBracket(currentToken)) {
                throw CalculatorException("Brackets mismatch")
            }
            postfixExpression.add(currentToken)
        }

        return postfixExpression
    }

    private fun handleClosingBracket(
        tokens: Stack<String>,
        postfixExpression: MutableList<String>
    ) {
        if (tokens.isEmpty()) {
            throw CalculatorException("Incorrect expression")
        }
        var topOfStack = tokens.pop()
        while (tokens.isNotEmpty() && !calculatorUtilities.isOpenBracket(topOfStack)) {
            postfixExpression.add(topOfStack)
            topOfStack = tokens.pop()
        }
    }

    private fun isUnaryOperator(infixExpression: List<String>, index: Int) =
        calculatorUtilities.findUnaryOperationByMainSign(infixExpression[index]) != null ||
                isUnaryMinus(infixExpression, index)

    private fun isUnaryMinus(infixExpression: List<String>, index: Int): Boolean {
        val currentToken = infixExpression[index]
        val isSuitableSign = currentToken == InverseByAddition.additionalSign
        if (index == 0) {
            return isSuitableSign
        }
        val previousToken = infixExpression[index - 1]
        return (calculatorUtilities.isOpenBracket(previousToken) ||
                calculatorUtilities.isBinaryOperator(previousToken) ||
                calculatorUtilities.isUnaryOperator(previousToken)) &&
                isSuitableSign
    }

    private fun handleUnaryOperator(sign: String, tokens: Stack<String>) {
        val mainSign =
            calculatorUtilities.findUnaryOperationByAdditionalSign(sign)?.sign
                ?: calculatorUtilities.findUnaryOperationByMainSign(sign)?.sign
                ?: throw CalculatorException("Attempting to process a non-existing unary operation")
        tokens.push(mainSign)
    }

    private fun getPriority(operator: String) =
        calculatorUtilities.getPriority(operator)
            ?: throw CalculatorException("Taking priority from an unknown operator")

    private fun shouldAddOperatorToPostfix(operator: String, tokens: Stack<String>) =
        (tokens.isNotEmpty() &&
                !calculatorUtilities.isOpenBracket(tokens.peek()) &&
                (calculatorUtilities.findUnaryOperationByMainSign(tokens.peek()) != null ||
                        getPriority(tokens.peek()) >= getPriority(operator)))

    private fun handleOperator(
        operator: String,
        tokens: Stack<String>,
        postfixExpression: MutableList<String>
    ) {
        while (shouldAddOperatorToPostfix(operator, tokens)) {
            postfixExpression.add(tokens.pop())
        }
        tokens.push(operator)
    }
}
