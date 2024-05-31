package com.stingach.dm.solveproblem

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    // Объявляем переменные для текстовых полей и кнопок
    lateinit var resultSummary: TextView

    lateinit var correctCount: TextView
    lateinit var incorrectCount: TextView

    lateinit var calculationPercentage: TextView

    lateinit var operandOne: TextView
    lateinit var operandTwo: TextView
    lateinit var arithmeticOperation: TextView

    lateinit var userResponse: TextView

    lateinit var evaluateButton: Button
    lateinit var initiateButton: Button

    // Объявляем переменные для подсчета правильных и неправильных ответов
    var totalCorrectAnswers = 0
    var totalIncorrectAnswers = 0

    // Список доступных операций
    val availableOperations = listOf("+", "-", "*", "/")

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализируем переменные, связывая их с элементами макета
        resultSummary = findViewById(R.id.textView1)

        correctCount = findViewById(R.id.textView4)
        incorrectCount = findViewById(R.id.textView5)

        calculationPercentage = findViewById(R.id.textView6)

        operandOne = findViewById(R.id.textView7)
        operandTwo = findViewById(R.id.textView9)
        arithmeticOperation = findViewById(R.id.textView8)

        userResponse = findViewById(R.id.editTextText)

        // Устанавливаем слушатель для кнопки "Оценить"
        evaluateButton = findViewById<Button>(R.id.button)
        evaluateButton.setOnClickListener {
            evaluate()
        }

        // Устанавливаем слушатель для кнопки "Начать"
        initiateButton = findViewById<Button>(R.id.button1)
        initiateButton.setOnClickListener {
            initiate()
        }

        // Отключаем поля ввода и кнопки до начала новой игры
        userResponse.isEnabled = false
        evaluateButton.isEnabled = false
    }

    // Функция оценки ответа пользователя
    fun evaluate() {
        evaluateButton.isEnabled = false
        initiateButton.isEnabled = true
        userResponse.isEnabled = false

        // Вычисляем правильный результат
        val calculatedResult = calculateResult().toInt().toString()

        // Сравниваем ответ пользователя с правильным результатом
        if (userResponse.text.toString() == calculatedResult) {
            totalCorrectAnswers++
            userResponse.setBackgroundColor(Color.GREEN)
        } else {
            totalIncorrectAnswers++
            userResponse.setBackgroundColor(Color.RED)
        }

        // Обновляем текстовые поля с результатами
        correctCount.text = totalCorrectAnswers.toString()
        incorrectCount.text = totalIncorrectAnswers.toString()
        resultSummary.text = (totalCorrectAnswers + totalIncorrectAnswers).toString()
        calculationPercentage.text = String.format("%.2f", (totalCorrectAnswers.toDouble() / (totalCorrectAnswers + totalIncorrectAnswers) * 100)) + "%"
    }

    // Функция для начала новой игры
    fun initiate() {
        initiateButton.isEnabled = false
        evaluateButton.isEnabled = true
        userResponse.isEnabled = true
        userResponse.setBackgroundColor(Color.TRANSPARENT)

        // Генерируем новое выражение
        generateNewExpression()
    }

    // Функция для генерации нового математического выражения
    fun generateNewExpression() {
        operandOne.text = Random.nextInt(10, 100).toString()
        operandTwo.text = Random.nextInt(10, 100).toString()
        arithmeticOperation.text = availableOperations[Random.nextInt(0, 4)]

        // Убеждаемся, что результат деления - целое число
        while (arithmeticOperation.text == "/" && operandOne.text.toString().toDouble() % operandTwo.text.toString().toDouble() != 0.0) {
            operandOne.text = Random.nextInt(10, 100).toString()
            operandTwo.text = Random.nextInt(10, 100).toString()
        }
    }

    // Функция для вычисления результата текущего выражения
    fun calculateResult(): Double {
        return when (arithmeticOperation.text) {
            "+" -> operandOne.text.toString().toDouble() + operandTwo.text.toString().toDouble()
            "-" -> operandOne.text.toString().toDouble() - operandTwo.text.toString().toDouble()
            "*" -> operandOne.text.toString().toDouble() * operandTwo.text.toString().toDouble()
            "/" -> operandOne.text.toString().toDouble() / operandTwo.text.toString().toDouble()
            else -> 0.0
        }
    }
}