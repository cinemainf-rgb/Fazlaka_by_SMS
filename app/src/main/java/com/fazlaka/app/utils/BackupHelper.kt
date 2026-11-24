package com.fazlaka.app.utils
import android.content.Context
import org.json.JSONObject
// نسخ احتياطي: تصدير - استيراد - نسخ تلقائي
object BackupHelper {
    fun exportJson(context: Context, json: String): String {
        val file = java.io.File(context.getExternalFilesDir(null), "fazlaka_backup.json")
        file.writeText(json)
        return file.absolutePath
    }
    fun autoBackup(context: Context, json: String) {
        val prefs = context.getSharedPreferences("fazlaka", Context.MODE_PRIVATE)
        prefs.edit().putString("auto_backup", json).putLong("auto_backup_at", System.currentTimeMillis()).apply()
    }
    fun isValidBackup(json: String): Boolean {
        return try { val o = JSONObject(json); o.has("wallets") || o.has("products") } catch (e: Exception) { false }
    }
}
