package com.example.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    private var justCalc: Boolean = true
    private var prevNumber = ""
    private var sqNum: Double = 0.0
    private var prevOperator: String = ""
    var firstNumber = true
    fun operatorOrNumber(view: View) {
        val calculation: TextView = findViewById(R.id.resultSheet)
        val calcHistory: TextView = findViewById(R.id.resultHistory)
        val buttonClicked = view as Button
        when (buttonClicked.id) {
            R.id.AC -> {
                calcHistory.text = ""
                calculation.text = "0"
                prevOperator = ""
                prevNumber = ""
                firstNumber = true
                justCalc = true
            }
            R.id.sqroot -> {
                sqNum = sqrt(calculation.text.toString().toDouble())
                calculation.text = sqNum.toString()
                calcHistory.text = calcHistory.text.toString() + calculation.text
            }
            R.id.addition -> {
                if (firstNumber) {
                    prevOperator = "+"
                    firstNumberOperations(calcHistory, calculation,prevOperator)
                } else if( prevOperator != "+")
                    operatorChange("+",calcHistory,calculation,false)
                 else {
                    prevOperator = "+"
                    performCalc(calculation, calcHistory)
                    justCalc = true
                }
            }
            R.id.subtract -> {
                if (firstNumber) {
                    prevOperator = "-"
                    firstNumberOperations(calcHistory, calculation,prevOperator)
                } else if( prevOperator != "-")
                    operatorChange("-",calcHistory,calculation,false)
                else {
                    prevOperator = "-"
                    performCalc(calculation, calcHistory)
                    justCalc = true
                }
            }
            R.id.multiplication -> {
                if (firstNumber) {
                    prevOperator = "*"
                    firstNumberOperations(calcHistory, calculation, prevOperator)
                } else if( prevOperator != "*")
                    operatorChange("*",calcHistory,calculation,false)
                else {
                    prevOperator = "*"
                    performCalc(calculation, calcHistory)
                    justCalc = true
                }
            }
            R.id.divide -> {
                if (firstNumber) {
                    prevOperator = "/"
                    firstNumberOperations(calcHistory, calculation,prevOperator)
                } else if( prevOperator != "/")
                    operatorChange("/",calcHistory,calculation,false)
                else {
                    prevOperator = "/"
                    performCalc(calculation, calcHistory)
                    justCalc = true
                }
            }
            R.id.square -> {
                calcHistory.text = calcHistory.text.toString()+"(" + calculation.text.toString() + ")²"
                prevNumber = (calculation.text.toString().toDouble() * calculation.text.toString().toDouble()).toString()
                justCalc = true
                calculation.text = prevNumber
            }
            R.id.equals -> operatorChange("=",calcHistory,calculation,true)
            R.id.one -> if (justCalc) {calculation.text = "1" 
                justCalc = false}
            else calculation.text = calculation.text.toString() + "1"
            R.id.two -> if (justCalc) {calculation.text = "2"
                justCalc = false}
            else calculation.text = calculation.text.toString() + "2"
            R.id.three -> if (justCalc) {calculation.text = "3"
                justCalc = false}
            else calculation.text = calculation.text.toString() + "3"
            R.id.four -> if (justCalc) {calculation.text = "4"
                justCalc = false}
            else calculation.text = calculation.text.toString() + "4"
            R.id.five -> if (justCalc) {calculation.text = "5"
                justCalc = false}
            else calculation.text = calculation.text.toString() + "5"
            R.id.six -> if (justCalc) {calculation.text = "6"
                justCalc = false}
            else calculation.text = calculation.text.toString() + "6"
            R.id.seven -> if (justCalc) {calculation.text = "7"
                justCalc = false}
            else calculation.text = calculation.text.toString() + "7"
            R.id.eight ->if (justCalc) {calculation.text = "8"
                justCalc = false}
            else calculation.text = calculation.text.toString() + "8"
            R.id.nine -> if (justCalc) {calculation.text = "9"
                justCalc = false}
            else calculation.text = calculation.text.toString() + "9"
            R.id.zero -> if (justCalc) {calculation.text = "0"
                justCalc = false}
            else calculation.text = calculation.text.toString() + "0"
            R.id.limiter -> if (justCalc) {calculation.text = "."
                justCalc = false}
            else calculation.text = calculation.text.toString() + "."
            R.id.clear_text -> calculation.text = calculation.text.toString().dropLast(1)
        }
}

    private fun operatorChange(currentOperator:String,calcHistory: TextView,calculation: TextView,equalClicked:Boolean) {
        if(prevOperator == "+")
            calculation.text = (prevNumber.toDouble()+calculation.text.toString().toDouble()).toString()
        else if(prevOperator == "-")
            calculation.text = (prevNumber.toDouble()-calculation.text.toString().toDouble()).toString()
        else if(prevOperator == "*")
            calculation.text = (prevNumber.toDouble()*calculation.text.toString().toDouble()).toString()
        else if(prevOperator == "/")
            calculation.text = (prevNumber.toDouble()/calculation.text.toString().toDouble()).toString()
        if(!equalClicked)
            prevOperator = currentOperator
        else
            firstNumber=true
        justCalc = true
        calcHistory.text = calculation.text.toString()+prevOperator
        prevNumber = calculation.text.toString()
    }

    private fun firstNumberOperations(calcHistory:TextView,calculation: TextView,prevOperator:String)
    {
        calcHistory.text = calculation.text.toString()+prevOperator
        prevNumber = calculation.text.toString()
        calculation.text = ""
        firstNumber = false
    }

    @SuppressLint("SetTextI18n")
    private fun performCalc(calculation: TextView, calcHistory: TextView) {
        when (prevOperator) {
            "*" -> {
                calcHistory.text = calcHistory.text.toString()+calculation.text.toString()+"*"
                calculation.text = (calculation.text.toString().toDouble() * prevNumber.toDouble()).toString()
            }
            "+" -> {
                calcHistory.text = calcHistory.text.toString()+calculation.text.toString()+"+"
                calculation.text = (calculation.text.toString().toDouble() + prevNumber.toDouble()).toString()
            }
            "-" -> {
                calcHistory.text = calcHistory.text.toString()+calculation.text.toString()+"-"
                calculation.text = (prevNumber.toDouble() - calculation.text.toString().toDouble()).toString()
            }
            "/" -> {
                calcHistory.text = calcHistory.text.toString()+calculation.text.toString()+"÷"
                calculation.text = (prevNumber.toDouble() / calculation.text.toString().toDouble()).toString()
            }
        }
        prevNumber = calculation.text.toString()
    }
}
