package br.com.gabriel.pass_generator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var passwordTextView: TextView
    private lateinit var passwordLengthEditText: EditText
    private lateinit var uppercaseCheckBox: CheckBox
    private lateinit var lowercaseCheckBox: CheckBox
    private lateinit var numbersCheckBox: CheckBox
    private lateinit var similarCharactersCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        passwordTextView = findViewById(R.id.passwordTextView)
        passwordLengthEditText = findViewById(R.id.passwordLengthEditText)
        uppercaseCheckBox = findViewById(R.id.uppercaseCheckBox)
        lowercaseCheckBox = findViewById(R.id.lowercaseCheckBox)
        numbersCheckBox = findViewById(R.id.numbersCheckBox)
        similarCharactersCheckBox = findViewById(R.id.similarCharactersCheckBox)

        val generateButton: Button = findViewById(R.id.generateButton)
        val copyButton: Button = findViewById(R.id.copyButton)

        generateButton.setOnClickListener { generatePassword() }
        copyButton.setOnClickListener { copyPasswordToClipboard() }
    }

    private fun generatePassword() {
        val length = passwordLengthEditText.text.toString().toIntOrNull() ?: return
        val useUppercase = uppercaseCheckBox.isChecked
        val useLowercase = lowercaseCheckBox.isChecked
        val useNumbers = numbersCheckBox.isChecked
        val excludeSimilar = similarCharactersCheckBox.isChecked

        val password = createPassword(length, useUppercase, useLowercase, useNumbers, excludeSimilar)
        passwordTextView.text = password
    }

    private fun createPassword(length: Int, useUppercase: Boolean, useLowercase: Boolean, useNumbers: Boolean, excludeSimilar: Boolean): String {
        val uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val lowercaseChars = "abcdefghijklmnopqrstuvwxyz"
        val numberChars = "0123456789"
        val similarChars = "l1Io0O"
        val allChars = StringBuilder()

        if (useUppercase) allChars.append(uppercaseChars)
        if (useLowercase) allChars.append(lowercaseChars)
        if (useNumbers) allChars.append(numberChars)

        val filteredChars = if (excludeSimilar) {
            allChars.toString().filter { !similarChars.contains(it) }
        } else {
            allChars.toString()
        }

        return (1..length).map { filteredChars.random() }.joinToString("")
    }

    private fun copyPasswordToClipboard() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Senha Copiada", passwordTextView.text.toString())
        clipboard.setPrimaryClip(clip)
    }
}