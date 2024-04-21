package com.DobriyanovMP.calculateEquasion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.DobriyanovMP.calculateEquasion.databinding.ActivityMainBinding
import kotlin.math.round
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var state: ActivityState

    var firstOperand = 10
    var secondOperand = 20
    var operation = "-"
    var equationsCount = 20
    var correctAnswers = 10
    var wrongAnswers = 20
    var percentOfCorrect = 100.0
    var answer = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        state = savedInstanceState?.getParcelable("activityState")?: ActivityState(
            AllEquationsCount = 0,
            CorrectEquationsCount = 0,
            WrongEquationsCount = 0,
            LastFirstOperand = 0,
            LastSecondOperand = 0,
            LastOperation = "+",
            StartButtonState = true,
            EquationColor = getColor(R.color.white)
        )

        equationsCount = state.AllEquationsCount
        correctAnswers = state.CorrectEquationsCount
        wrongAnswers = state.WrongEquationsCount
        firstOperand = state.LastFirstOperand
        secondOperand = state.LastSecondOperand
        operation = state.LastOperation
        binding.StartButton.isEnabled = state.StartButtonState
        binding.EquasionLayout.setBackgroundColor(state.EquationColor)

        SetEquation()
        binding.AllEqScoreField.text = equationsCount.toString()
        binding.AllCorrectScoreField.text = correctAnswers.toString()
        binding.AllWrongScoreField.text = wrongAnswers.toString()
        binding.PercentScore.text = percentOfCorrect.toString() + "%"


        binding.StartButton.setOnClickListener { Start() }
        binding.CheckButton.setOnClickListener { CheckEquation() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("activityState", state)
    }

    private fun Start() {
        binding.StartButton.isEnabled = false
        state.StartButtonState = false
        CreateEquation()
    }

    private fun CreateEquation() {
        var tmp: Int

        val newOperation = Random.nextInt(1, 5)

        when (newOperation) {
            1 -> operation = "+"
            2 -> operation = "-"
            3 -> operation = "*"
            4 -> operation = "\\"
        }

        firstOperand = Random.nextInt(1, 100)
        secondOperand = Random.nextInt(1, 100)

        if (newOperation == 4) {
            while (firstOperand % secondOperand != 0){
                firstOperand = Random.nextInt(1, 100)
                secondOperand = Random.nextInt(1, 100)
                if (firstOperand < secondOperand){
                    tmp = firstOperand
                    firstOperand = secondOperand
                    secondOperand = tmp
                }
            }
        }

        when (newOperation) {
            1 -> answer = firstOperand + secondOperand
            2 -> answer = firstOperand - secondOperand
            3 -> answer = firstOperand * secondOperand
            4 -> answer = firstOperand / secondOperand
        }

        SetEquation()
    }

    private fun CheckEquation(){
        if (binding.AnswerField.text.toString() == answer.toString()){
            binding.EquasionLayout.setBackgroundColor(getColor(R.color.green))
            state.EquationColor = getColor(R.color.green)
            correctAnswers += 1
        }
        else {
            binding.EquasionLayout.setBackgroundColor(getColor(R.color.red))
            state.EquationColor = getColor(R.color.red)
            wrongAnswers += 1
        }
        equationsCount += 1
        percentOfCorrect = round(((correctAnswers / equationsCount.toDouble()) * 100))

        binding.AllEqScoreField.text = equationsCount.toString()
        binding.AllCorrectScoreField.text = correctAnswers.toString()
        binding.AllWrongScoreField.text = wrongAnswers.toString()
        binding.PercentScore.text = percentOfCorrect.toString() + "%"

        CreateEquation()
    }

    private fun SetEquation() {
        binding.Operand1.text = firstOperand.toString()
        binding.Operand2.text = secondOperand.toString()
        binding.Operation.text = operation

        state.LastFirstOperand = firstOperand
        state.LastSecondOperand = secondOperand
        state.LastOperation = operation
        state.AllEquationsCount = equationsCount
        state.CorrectEquationsCount = correctAnswers
        state.WrongEquationsCount = wrongAnswers
    }
}