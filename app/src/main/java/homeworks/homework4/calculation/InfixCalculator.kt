package homeworks.homework4.calculation

import java.lang.NumberFormatException
import java.util.Stack

/**
 * Works with a list of string tokens.
 * */
class InfixCalculator(private val calculatorUtilities: CalculatorUtilities) {
    private val infixToPostfixConverter = InfixToPostfixConverter(calculatorUtilities)

    fun calculate(infixExpression: List<String>): Double {
        val postfixExpression = infixToPostfixConverter.convert(infixExpression)
        return calculatePostfixExpression(postfixExpression)
    }

    private fun calculatePostfixExpression(postfixExpression: List<String>): Double {
        val numbersInExpression = Stack<Double>()
        postfixExpression.forEach {
            when {
                calculatorUtilities.isBinaryOperator(it) -> handleBinaryOperator(
                    it,
                    numbersInExpression
                )
                calculatorUtilities.isUnaryOperator(it) -> handleUnaryOperator(
                    it,
                    numbersInExpression
                )
                calculatorUtilities.isPositiveNumber(it) -> handleNumber(it, numbersInExpression)
                else -> throw CalculatorException("Unknown token")
            }
        }
        if (numbersInExpression.size != 1) {
            throw CalculatorException("Calculating error. Possibly mismatched brackets")
        }
        return numbersInExpression.pop()
    }

    private fun handleBinaryOperator(operator: String, numbersInExpression: Stack<Double>) {
        if (numbersInExpression.size < 2) {
            throw CalculatorException("Not enough numbers")
        }
        val secondNumber = numbersInExpression.pop()
        val firstNumber = numbersInExpression.pop()
        val result =
            calculatorUtilities.findBinaryOperation(operator)?.calculate(firstNumber, secondNumber)
                ?: throw CalculatorException("Calculating by unknown operator")
        numbersInExpression.push(result)
    }

    private fun handleUnaryOperator(operator: String, numbersInExpression: Stack<Double>) {
        if (numbersInExpression.size < 1) {
            throw CalculatorException("Not enough numbers")
        }
        val number = numbersInExpression.pop()
        val result = calculatorUtilities.findUnaryOperationByMainSign(operator)?.calculate(number)
            ?: throw CalculatorException("Calculating by unknown operator")
        numbersInExpression.push(result)
    }

    private fun handleNumber(number: String, numbersInExpression: Stack<Double>) {
        try {
            numbersInExpression.push(number.toDouble())
        } catch (exception: NumberFormatException) {
            throw CalculatorException("Incorrect number")
        }
    }
}
