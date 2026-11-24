package com.fazlaka.app
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fazlaka.app.utils.FingerprintHelper
class LockActivity : AppCompatActivity() {
    private var pinInput = ""
    private val correctPin get() = getSharedPreferences("fazlaka", MODE_PRIVATE).getString("pin", "1234") ?: "1234"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)
        val dots = findViewById<TextView>(R.id.dots)
        val msg = findViewById<TextView>(R.id.msg)
        findViewById<Button>(R.id.btnFinger).setOnClickListener {
            FingerprintHelper.show(this, onSuccess = { openMain() }, onNeedPin = { msg.text = "البصمة فشلت - اكتب PIN" })
        }
        val keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "مسح", "0", "X")
        val grid = findViewById<GridLayout>(R.id.keys)
        keys.forEach { k ->
            val b = Button(this)
            b.text = k
            b.setOnClickListener {
                when (k) {
                    "مسح" -> { pinInput = ""; msg.text = "" }
                    "X" -> { pinInput = pinInput.dropLast(1) }
                    else -> { if (pinInput.length < 4) pinInput += k }
                }
                dots.text = "●".repeat(pinInput.length) + "○".repeat(4 - pinInput.length)
                if (pinInput.length == 4) {
                    if (pinInput == correctPin) openMain() else { msg.text = "الرمز غير صحيح"; pinInput = "" }
                }
            }
            grid.addView(b)
        }
        FingerprintHelper.show(this, onSuccess = { openMain() }, onNeedPin = { msg.text = "اكتب PIN - الافتراضي 1234" })
    }
    private fun openMain() {
        val intent = Intent()
        intent.setClassName(this, "com.fazlaka.app.MainActivity")
        startActivity(intent)
        finish()
    }
}
