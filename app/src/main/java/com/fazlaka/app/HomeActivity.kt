package com.fazlaka.app
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        findViewById<Button>(R.id.btnServices)?.setOnClickListener { startActivity(Intent(this, ServicesActivity::class.java)) }
        findViewById<Button>(R.id.btnStock)?.setOnClickListener { startActivity(Intent(this, StockActivity::class.java)) }
        findViewById<Button>(R.id.btnReports)?.setOnClickListener { startActivity(Intent(this, ReportsActivity::class.java)) }
        findViewById<Button>(R.id.btnMore)?.setOnClickListener { startActivity(Intent(this, MoreActivity::class.java)) }
    }
}
