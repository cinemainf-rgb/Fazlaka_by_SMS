package com.fazlaka.app

import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class HomeActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this)
        setContentView(webView)

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    // البصمة نجحت - افتح التطبيق من غير PIN
                    runOnUiThread {
                        webView.evaluateJavascript("unlockByFingerprint()", null)
                    }
                }
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    runOnUiThread {
                        Toast.makeText(this@HomeActivity, "البصمة اتلغت - اكتب PIN", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    runOnUiThread {
                        Toast.makeText(this@HomeActivity, "البصمة غلط - جرب تاني", Toast.LENGTH_SHORT).show()
                    }
                }
            })

        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = true

        // ده الجسر اللي بيوصل HTML بالبصمة الحقيقية
        webView.addJavascriptInterface(FingerprintBridge(), "Android")
        webView.webViewClient = WebViewClient()
        webView.loadUrl("file:///android_asset/fazlaka/index.html")
    }

    inner class FingerprintBridge {
        @JavascriptInterface
        fun fingerprint() {
            runOnUiThread {
                val manager = BiometricManager.from(this@HomeActivity)
                if (manager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) != BiometricManager.BIOMETRIC_SUCCESS) {
                    Toast.makeText(this@HomeActivity, "مفيش بصمة متسجلة على الموبايل", Toast.LENGTH_LONG).show()
                    return@runOnUiThread
                }
                val info = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("FAZLAKA")
                    .setSubtitle("حط صباعك عشان تفتح")
                    .setNegativeButtonText("استخدم PIN")
                    .build()
                biometricPrompt.authenticate(info)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack() else super.onBackPressed()
    }
}
