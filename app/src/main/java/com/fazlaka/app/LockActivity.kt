package com.fazlaka.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

class LockActivity : AppCompatActivity() {
    private var pinInput = ""
    private lateinit var dotsText: TextView
    private lateinit var msgText: TextView

    private fun getPin(): String {
        val prefs = getSharedPreferences("fazlaka", Context.MODE_PRIVATE)
        return prefs.getString("pin", "1234") ?: "1234"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)

        dotsText = findViewById(R.id.dotsText)
        msgText = findViewById(R.id.msgText)
        updateDots()

        findViewById<Button>(R.id.btnFingerprint).setOnClickListener { showBiometric() }

        findViewById<Button>(R.id.btn1).setOnClickListener { press("1") }
        findViewById<Button>(R.id.btn2).setOnClickListener { press("2") }
        findViewById<Button>(R.id.btn3).setOnClickListener { press("3") }
        findViewById<Button>(R.id.btn4).setOnClickListener { press("4") }
        findViewById<Button>(R.id.btn5).setOnClickListener { press("5") }
        findViewById<Button>(R.id.btn6).setOnClickListener { press("6") }
        findViewById<Button>(R.id.btn7).setOnClickListener { press("7") }
        findViewById<Button>(R.id.btn8).setOnClickListener { press("8") }
        findViewById<Button>(R.id.btn9).setOnClickListener { press("9") }
        findViewById<Button>(R.id.btn0).setOnClickListener { press("0") }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            pinInput = ""
            msgText.text = "اكتب PIN"
            updateDots()
        }
        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            if (pinInput.isNotEmpty()) pinInput = pinInput.dropLast(1)
            updateDots()
        }
    }

    private fun press(num: String) {
        if (pinInput.length >= 4) return
        pinInput += num
        updateDots()
        if (pinInput.length == 4) {
            if (pinInput == getPin()) {
                openHome()
            } else {
                msgText.text = "الرمز غير صحيح"
                pinInput = ""
                updateDots()
            }
        }
    }

    private fun updateDots() {
        dotsText.text = "●".repeat(pinInput.length) + "○".repeat(4 - pinInput.length)
    }

    private fun showBiometric() {
        val executor = ContextCompat.getMainExecutor(this)
        val prompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                openHome()
            }
            override fun onAuthenticationError(code: Int, msg: CharSequence) {
                Toast.makeText(this@LockActivity, "اكتب PIN", Toast.LENGTH_SHORT).show()
            }
        })
        val info = BiometricPrompt.PromptInfo.Builder()
            .setTitle("FAZLAKA")
            .setSubtitle("حط صباعك عشان تفتح")
            .setNegativeButtonText("استخدم PIN")
            .build()
        prompt.authenticate(info)
    }

    private fun openHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
