package homeworks.homework4.calculation

import homeworks.homework4.calculation.binaryOperations.Addition
import homeworks.homework4.calculation.binaryOperations.Division
import homeworks.homework4.calculation.binaryOperations.Multiplication
import homeworks.homework4.calculation.binaryOperations.Subtraction
import homeworks.homework4.calculation.unaryOperations.InverseByAddition
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Unary minus is converted to "!"
 * */
internal class InfixToPostfixConverterTest {
    private val calculatorUtilities = CalculatorUtilities(
        listOf(
            Addition,
            Division,
            Multiplication,
            Subtraction
        ),
        listOf(InverseByAddition)
    )
    private val infixToPostfixConverter = InfixToPostfixConverter(calculatorUtilities)
    private val stringToTokensConverter = StringToTokensConverter(calculatorUtilities)

    private fun convertTokensToString(tokens: List<String>) =
        tokens.fold("") { string, token -> "$string $token" }.trim()

    @Test
    fun negativeNumber_mustWork() {
        val tokens = stringToTokensConverter.convert("-108.8")
        assertEquals(
            "108.8 !",
            convertTokensToString(infixToPostfixConverter.convert(tokens))
        )
    }

    @Test
    fun onlyOneNumber_mustWork() {
        val tokens = stringToTokensConverter.convert("108.8")
        assertEquals(
            "108.8",
            convertTokensToString(infixToPostfixConverter.convert(tokens))
        )
    }

    @Test
    fun unaryMinusBeforeBrackets_mustWork() {
        val tokens = stringToTokensConverter.convert("-(15+10)")
        assertEquals(
            "15 10 + !",
            convertTokensToString(infixToPostfixConverter.convert(tokens))
        )
    }

    @Test
    fun whiteSpaces_mustWork() {
        val tokens =
            stringToTokensConverter.convert("      15  +                 10 × 90           ")
        assertEquals(
            "15 10 90 × +",
            convertTokensToString(infixToPostfixConverter.convert(tokens))
        )
    }

    @Test
    fun manyUnaryMinuses_mustWork() {
        val tokens =
            stringToTokensConverter.convert("1+-----5")
        assertEquals(
            "1 5 ! ! ! ! ! +",
            convertTokensToString(infixToPostfixConverter.convert(tokens))
        )
    }

    @Test
    fun manyBrackets_mustWork() {
        val tokens =
            stringToTokensConverter.convert("(((((((6×(2+3))))-((4+3))×(2)+1))))")
        assertEquals(
            "6 2 3 + × 4 3 + 2 × - 1 +",
            convertTokensToString(infixToPostfixConverter.convert(tokens))
        )
    }

    @Test
    fun justLongExpression_mustWork() {
        val tokens =
            stringToTokensConverter.convert("((100+-109)×(43+1)-(12+-((12×34)))+(102-2)÷90+23+2)")
        assertEquals(
            "100 109 ! + 43 1 + × 12 12 34 × ! + - 102 2 - 90 ÷ + 23 + 2 +",
            convertTokensToString(infixToPostfixConverter.convert(tokens))
        )
    }

    @Test
    fun unknownSymbol_ExceptionThrows() {
        val tokens = listOf("109.e3")
        assertThrows(CalculatorException::class.java) {
            convertTokensToString(infixToPostfixConverter.convert(tokens))
        }
    }

    @Test
    fun bracketsMismatch1_ExceptionThrows() {
        val tokens =
            stringToTokensConverter.convert("((((((90))")
        assertThrows(CalculatorException::class.java) {
            convertTokensToString(infixToPostfixConverter.convert(tokens))
        }
    }

    @Test
    fun bracketsMismatch2_ExceptionThrows() {
        val tokens =
            stringToTokensConverter.convert("(90))))))")
        assertThrows(CalculatorException::class.java) {
            convertTokensToString(infixToPostfixConverter.convert(tokens))
        }
    }
}
