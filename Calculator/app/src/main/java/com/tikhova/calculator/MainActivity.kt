package com.tikhova.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.round
import android.R.attr.name

class MainActivity : AppCompatActivity() {
    val SCREEN_TEXT = "screenText"
    val DOT_FLAG = "dotEnabled"
    val OPERATION_FLAG = "lastIsOperation"

    var dotEnabled: Boolean = true
    var lastIsOperation: Boolean = false
    val operations: Array<String> = arrayOf("/", "*", "+", "-")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        number0.setOnClickListener { appendValue("0") }
        number1.setOnClickListener { appendValue("1") }
        number2.setOnClickListener { appendValue("2") }
        number3.setOnClickListener { appendValue("3") }
        number4.setOnClickListener { appendValue("4") }
        number5.setOnClickListener { appendValue("5") }
        number6.setOnClickListener { appendValue("6") }
        number7.setOnClickListener { appendValue("7") }
        number8.setOnClickListener { appendValue("8") }
        number9.setOnClickListener { appendValue("9") }
        add.setOnClickListener { appendValue("+") }
        sub.setOnClickListener { appendValue("-") }
        mul.setOnClickListener { appendValue("*") }
        div.setOnClickListener { appendValue("/") }
        open_bracket.setOnClickListener { appendValue("(") }
        close_bracket.setOnClickListener { appendValue(")") }
        dot.setOnClickListener { appendValue(".") }
        clear.setOnClickListener { resetScreen() }
        back.setOnClickListener { removeLastSymbol() }
        equals.setOnClickListener {
            try {
                val result = ExpressionBuilder(screen.text.toString()).build().evaluate()
                if (result == round(result))
                    screen.text = result.toLong().toString()
                else
                    screen.text = result.toString()
            } catch (e: Exception) {
                resetScreen()
                screen.text = "ERROR"
            }
        }

        if (savedInstanceState != null) {
            screen.text = savedInstanceState.getString(SCREEN_TEXT)
            dotEnabled = savedInstanceState.getBoolean(DOT_FLAG)
            lastIsOperation = savedInstanceState.getBoolean(OPERATION_FLAG)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(SCREEN_TEXT, screen.text.toString())
        outState.putBoolean(DOT_FLAG, dotEnabled)
        outState.putBoolean(OPERATION_FLAG, lastIsOperation)

        super.onSaveInstanceState(outState)
    }

    fun appendValue(value: String) {
        if (value.isOperation()) {
            if (screen.text.isNotEmpty()) {
                if (lastIsOperation) {
                    removeLastSymbol()
                }
                lastIsOperation = true
                dotEnabled = true
                screen.append(value)
            }
        } else {
            lastIsOperation = false
            if (value != "." || dotEnabled) {
                screen.append(value)
                if (value == ".") dotEnabled = false
                else if (!value.isDigit()) dotEnabled = true
            }
        }
    }

    fun resetScreen() {
        dotEnabled = true
        lastIsOperation = false
        screen.text = ""
    }

    fun removeLastSymbol() {
        var result = screen.text.toString()
        if (result.isNotEmpty()) {
            screen.text = result.substring(0, result.length - 1)
            lastIsOperation = result.length == 1 || !result[result.length - 2].toString().isOperation()
            dotEnabled = dotEnabled || (result.length > 1 && result[result.length - 2].toString() == ".")
        }
    }

    fun String.isDigit() : Boolean {
        return length == 1 && this <= "9" && this >= "0"
    }

    fun String.isOperation() : Boolean {
        return operations.indexOf(this) != -1
    }
}
