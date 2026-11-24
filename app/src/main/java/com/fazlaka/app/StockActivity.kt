package com.fazlaka.app
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
class StockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)
        val name = findViewById<EditText>(R.id.editName)
        val qty = findViewById<EditText>(R.id.editQty)
        val price = findViewById<EditText>(R.id.editPrice)
        val list = findViewById<TextView>(R.id.txtList)
        findViewById<Button>(R.id.btnAdd)?.setOnClickListener {
            val n = name?.text.toString()
            val q = qty?.text.toString()
            val p = price?.text.toString()
            if (n.isNotBlank()) {
                list?.text = (list?.text.toString() + "\n$n - الكمية: $q - السعر: $p")
                name?.setText(""); qty?.setText(""); price?.setText("")
            }
        }
        findViewById<Button>(R.id.btnBack)?.setOnClickListener { finish() }
    }
}
