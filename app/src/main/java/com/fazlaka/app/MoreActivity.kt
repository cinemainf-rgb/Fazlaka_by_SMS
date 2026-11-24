package com.fazlaka.app
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
class MoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tv = TextView(this).apply { text = "MoreActivity - Fazlaka 1.0"; textSize = 20f; setPadding(32, 32, 32, 32) }
        setContentView(tv)
    }
}
