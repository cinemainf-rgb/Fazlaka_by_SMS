package com.fazlaka.app
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
class MoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)
        val pin = findViewById<EditText>(R.id.editNewPin)
        findViewById<Button>(R.id.btnChangePin)?.setOnClickListener {
            val p = pin?.text.toString()
            if (p.length == 4) { getSharedPreferences("fazlaka", MODE_PRIVATE).edit().putString("pin", p).apply(); Toast.makeText(this, "تم تغيير PIN", Toast.LENGTH_SHORT).show() }
            else Toast.makeText(this, "اكتب 4 ارقام", Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.btnBack)?.setOnClickListener { finish() }
    }
}
