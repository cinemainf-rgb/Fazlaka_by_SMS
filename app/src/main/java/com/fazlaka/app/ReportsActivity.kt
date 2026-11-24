package com.fazlaka.app
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
class ReportsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)
        findViewById<TextView>(R.id.txtReport)?.text = "تقرير اليوم\n\nمبيعات: 0\nخدمات: 0\nعمولات: 0"
        findViewById<Button>(R.id.btnBack)?.setOnClickListener { finish() }
    }
}
