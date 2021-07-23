package homeworks.homework4.calculation

import homeworks.homework4.calculation.binaryOperations.Addition
import homeworks.homework4.calculation.binaryOperations.Division
import homeworks.homework4.calculation.binaryOperations.Multiplication
import homeworks.homework4.calculation.binaryOperations.Subtraction
import homeworks.homework4.calculation.unaryOperations.InverseByAddition
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class InfixCalculatorTest {
    private val calculatorUtilities = CalculatorUtilities(
        listOf(
            Addition,
            Division,
            Multiplication,
            Subtraction
        ),
        listOf(InverseByAddition)
    )
    private val stringToTokensConverter = StringToTokensConverter(calculatorUtilities)
    private val infixCalculator = InfixCalculator(calculatorUtilities)

    @Test
    fun negativeNumber_mustWork() {
        val tokens = stringToTokensConverter.convert("-108.8")
        val result = infixCalculator.calculate(tokens)
        assertEquals(-108.8, result)
    }

    @Test
    fun onlyOneNumber_mustWork() {
        val tokens = stringToTokensConverter.convert("108.8")
        val result = infixCalculator.calculate(tokens)
        assertEquals(108.8, result)
    }

    @Test
    fun unaryMinusBeforeBrackets_mustWork() {
        val tokens = stringToTokensConverter.convert("-(15+10)")
        val result = infixCalculator.calculate(tokens)
        assertEquals(-25.0, result)
    }

    @Test
    fun expressionWithBrackets_mustWork() {
        val tokens = stringToTokensConverter.convert("((((-15)×(45×-(1+1))+(-109-90)+-10)))")
        val result = infixCalculator.calculate(tokens)
        assertEquals(1141.0, result)
    }

    @Test
    fun expressionWithBracketsAndOneNumber_mustWork() {
        val tokens = stringToTokensConverter.convert("((((((((10.7))))))))")
        val result = infixCalculator.calculate(tokens)
        assertEquals(10.7, result)
    }

    @Test
    fun manyUnaryMinuses_mustWork() {
        val tokens = stringToTokensConverter.convert("1+-----------------4")
        val result = infixCalculator.calculate(tokens)
        assertEquals(-3.0, result)
    }

    @Test
    fun divisionByZero_ExceptionThrows() {
        val tokens = stringToTokensConverter.convert("1÷0")
        assertThrows(CalculatorException::class.java) {
            infixCalculator.calculate(tokens)
        }
    }

    @Test
    fun tooManyNumbers_ExceptionThrows() {
        val tokens = stringToTokensConverter.convert("9+9+9 3")
        assertThrows(CalculatorException::class.java) {
            infixCalculator.calculate(tokens)
        }
    }

    @Test
    fun motEnoughNumbers_ExceptionThrows() {
        val tokens = stringToTokensConverter.convert("9+")
        assertThrows(CalculatorException::class.java) {
            infixCalculator.calculate(tokens)
        }
    }

    @Test
    fun bracketsMismatch_ExceptionThrows() {
        val tokens = stringToTokensConverter.convert("((((((((89)")
        assertThrows(CalculatorException::class.java) {
            infixCalculator.calculate(tokens)
        }
    }
}
