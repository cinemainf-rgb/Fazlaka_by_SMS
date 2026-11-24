package com.fazlaka.app.utils
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
object FingerprintHelper {
    fun canUseBiometric(activity: FragmentActivity): Boolean {
        val mgr = BiometricManager.from(activity)
        return mgr.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
    }
    fun show(activity: FragmentActivity, onSuccess: () -> Unit, onNeedPin: () -> Unit) {
        if (!canUseBiometric(activity)) {
            onNeedPin()
            return
        }
        val executor = ContextCompat.getMainExecutor(activity)
        val prompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess()
            }
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onNeedPin()
            }
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                onNeedPin()
            }
        })
        val info = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Fazlaka")
            .setSubtitle("المس بصمة صباعك للدخول")
            .setNegativeButtonText("استخدم PIN")
            .build()
        prompt.authenticate(info)
    }
}
