package com.fazlaka.app
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
class StockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tv = TextView(this).apply { text = "StockActivity - Fazlaka 1.0"; textSize = 20f; setPadding(32, 32, 32, 32) }
        setContentView(tv)
    }
}
