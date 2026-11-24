package com.fazlaka.app.utils
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.UUID

object PrinterHelper {
    private val SPP_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    fun getPairedPrinters(): List<BluetoothDevice> {
        val adapter = BluetoothAdapter.getDefaultAdapter() ?: return emptyList()
        return try { adapter.bondedDevices.toList() } catch (e: SecurityException) { emptyList() }
    }
    fun printReceipt(device: BluetoothDevice, receiptText: String, onDone: (Boolean) -> Unit) {
        Thread {
            var socket: BluetoothSocket? = null
            var out: OutputStream? = null
            try {
                socket = device.createRfcommSocketToServiceRecord(SPP_UUID)
                socket.connect()
                out = socket.outputStream
                val esc = byteArrayOf(0x1B, 0x40)
                val center = byteArrayOf(0x1B, 0x61, 0x01)
                val cut = byteArrayOf(0x1D, 0x56, 0x01)
                out.write(esc); out.write(center)
                out.write(receiptText.toByteArray(Charsets.UTF_8))
                out.write("\n\n\n".toByteArray())
                out.write(cut); out.flush()
                onDone(true)
            } catch (e: Exception) { onDone(false) } finally { try { out?.close(); socket?.close() } catch (_: Exception) {} }
        }.start()
    }
    fun buildReceipt(shop: String, type: String, amount: String
