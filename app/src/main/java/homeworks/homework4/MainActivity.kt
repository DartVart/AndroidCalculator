package homeworks.homework4

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.TextView
import homeworks.homework4.calculation.CalculatorException
import homeworks.homework4.calculation.CalculatorUtilities
import homeworks.homework4.calculation.InfixCalculator
import homeworks.homework4.calculation.StringToTokensConverter
import homeworks.homework4.calculation.binaryOperations.Addition
import homeworks.homework4.calculation.binaryOperations.Division
import homeworks.homework4.calculation.binaryOperations.Multiplication
import homeworks.homework4.calculation.binaryOperations.Subtraction
import homeworks.homework4.calculation.unaryOperations.InverseByAddition

class MainActivity : AppCompatActivity() {
    private val calculator: InfixCalculator
    private val stringToTokensConverter: StringToTokensConverter

    init {
        val binaryOperations = listOf(Addition, Subtraction, Division, Multiplication)
        val unaryOperations = listOf(InverseByAddition)
        val calculatorUtilities = CalculatorUtilities(binaryOperations, unaryOperations)
        calculator = InfixCalculator(calculatorUtilities)
        stringToTokensConverter = StringToTokensConverter(calculatorUtilities)
    }

    private fun calculate(expression: String): Double {
        val listOfTokens = stringToTokensConverter.convert(expression)
        return calculator.calculate(listOfTokens)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val expressionTextView = findViewById<TextView>(R.id.expression)
        val answerTextView = findViewById<TextView>(R.id.answer)
        val scrollViewWithExpression =
            findViewById<HorizontalScrollView>(R.id.scrollViewWithExpression)

        buttonsAddingSymbolsInit(expressionTextView, answerTextView)
        clearButtonInit(expressionTextView, answerTextView, scrollViewWithExpression)
        deleteOneSymbolButtonInit(expressionTextView, answerTextView)
        equalsButtonInit(expressionTextView, answerTextView)
    }

    @SuppressLint("SetTextI18n")
    fun buttonsAddingSymbolsInit(expressionView: TextView, answerTextView: TextView) {
        val buttons = listOf(
            R.id.zeroButton,
            R.id.oneButton,
            R.id.twoButton,
            R.id.threeButton,
            R.id.fourButton,
            R.id.fiveButton,
            R.id.sixButton,
            R.id.sevenButton,
            R.id.eightButton,
            R.id.nineButton,
            R.id.additionButton,
            R.id.subtractionButton,
            R.id.divisionButton,
            R.id.multiplicationButton,
            R.id.openBracketButton,
            R.id.closingBracketButton,
            R.id.dotButton
        ).map { findViewById<Button>(it) }

        buttons.forEach { button ->
            button.setOnClickListener {
                val previousExpression = expressionView.text.toString()
                val previousAnswerText = answerTextView.text.toString()
                val buttonText = button.text.toString()
                expressionView.text = when {
                    previousAnswerText.contains("Error") -> {
                        answerTextView.text = ""
                        previousExpression + buttonText
                    }
                    previousAnswerText != "" -> {
                        answerTextView.text = ""
                        previousAnswerText + buttonText
                    }
                    previousExpression == getString(R.string.defaultExpressionValue) -> buttonText
                    else -> previousExpression + buttonText
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun clearButtonInit(
        expressionView: TextView,
        answerTextView: TextView,
        scrollViewWithExpression: HorizontalScrollView
    ) {
        val clearButton = findViewById<Button>(R.id.clearButton)
        clearButton.setOnClickListener {
            expressionView.text = getString(R.string.defaultExpressionValue)
            answerTextView.text = ""
            scrollViewWithExpression.scrollTo(0, 0)
        }
    }

    @SuppressLint("SetTextI18n")
    fun deleteOneSymbolButtonInit(expressionView: TextView, answerTextView: TextView) {
        val deleteOneSymbolButton = findViewById<Button>(R.id.deleteOneSymbolButton)
        deleteOneSymbolButton.setOnClickListener {
            answerTextView.text = ""
            val previousText = expressionView.text.toString()
            expressionView.text = if (previousText.length == 1) {
                getString(R.string.defaultExpressionValue)
            } else {
                previousText.dropLast(1)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun equalsButtonInit(
        expressionView: TextView,
        answerTextView: TextView
    ) {
        val equalsButton = findViewById<Button>(R.id.equalsButton)
        equalsButton.setOnClickListener {
            val expression = expressionView.text.toString()
            answerTextView.text = try {
                calculate(expression).toString()
            } catch (exception: CalculatorException) {
                exception.message ?: ""
            }
        }
    }
}
