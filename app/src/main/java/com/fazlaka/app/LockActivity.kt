package com.fazlaka.app
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fazlaka.app.utils.FingerprintHelper
class LockActivity : AppCompatActivity() {
    private var pinInput = ""
    private var fingerShown = false
    private val correctPin get() = getSharedPreferences("fazlaka", MODE_PRIVATE).getString("pin", "1234") ?: "1234"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)
        val dots = findViewById<TextView>(R.id.dots)
        val msg = findViewById<TextView>(R.id.msg)
        findViewById<Button>(R.id.btnFinger)?.setOnClickListener { showFinger(msg) }
        val map = mapOf(R.id.key1 to "1", R.id.key2 to "2", R.id.key3 to "3", R.id.key4 to "4", R.id.key5 to "5", R.id.key6 to "6", R.id.key7 to "7", R.id.key8 to "8", R.id.key9 to "9", R.id.key0 to "0")
        for ((id, v) in map) { findViewById<Button>(id)?.setOnClickListener { onKey(v, dots, msg) } }
        findViewById<Button>(R.id.keyClear)?.setOnClickListener { pinInput = ""; dots?.text = "○○○○"; msg?.text = "" }
        findViewById<Button>(R.id.keyBack)?.setOnClickListener { pinInput = pinInput.dropLast(1); dots?.text = "●".repeat(pinInput.length) + "○".repeat(4 - pinInput.length) }
    }
    override fun onResume() { super.onResume(); if (!fingerShown) { fingerShown = true; window.decorView.post { showFinger(findViewById<TextView>(R.id.msg)) } } }
    private fun showFinger(msg: TextView?) { try { FingerprintHelper.show(this, onSuccess = { openMain() }, onNeedPin = { msg?.text = "اكتب PIN" }) } catch (e: Exception) { msg?.text = "اكتب PIN" } }
    private fun onKey(v: String, dots: TextView?, msg: TextView?) { if (pinInput.length < 4) pinInput += v; dots?.text = "●".repeat(pinInput.length) + "○".repeat(4 - pinInput.length); if (pinInput.length == 4) { if (pinInput == correctPin) openMain() else { msg?.text = "الرمز غير صحيح"; pinInput = "" } } }
    private fun openMain() { val i = Intent(); i.setClassName(this, "com.fazlaka.app.MainActivity"); startActivity(i); finish() }
}
