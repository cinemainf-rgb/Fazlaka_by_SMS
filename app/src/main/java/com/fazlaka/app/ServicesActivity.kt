package com.fazlaka.app
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
class ServicesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)
        val type = findViewById<Spinner>(R.id.spinnerType)
        val amount = findViewById<EditText>(R.id.editAmount)
        val result = findViewById<TextView>(R.id.txtResult)
        type?.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOf("فودافون كاش","اورانج كاش","انستا باي","شحن رصيد","فواتير"))
        findViewById<Button>(R.id.btnCalc)?.setOnClickListener {
            val a = amount?.text.toString().toDoubleOrNull() ?: 0.0
            val c = a * 0.01
            result?.text = "المبلغ: $a\nالعمولة: $c\nالاجمالي: ${a + c}"
        }
        findViewById<Button>(R.id.btnBack)?.setOnClickListener { finish() }
    }
}
