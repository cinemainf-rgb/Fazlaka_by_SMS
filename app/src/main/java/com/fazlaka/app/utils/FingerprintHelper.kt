<!DOCTYPE html><html><head><style>
@font-face {
  font-family: "Optimistic";
  font-style: normal;
  font-weight: 400 600;
  font-display: swap;
  src: url("/fonts/OptimisticAI_VF_Optimized.woff2") format("woff2");
}
@font-face {
  font-family: "Optimistic Mono";
  font-style: normal;
  font-weight: 400;
  font-display: swap;
  src: url("/fonts/OptimisticMono_W_TextRegular.woff2") format("woff2");
}
:where(html) {
  font-family: "Optimistic", system-ui, sans-serif;
}
:where(code, pre, kbd, samp) {
  font-family: "Optimistic Mono", ui-monospace, monospace;
}
</style><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>7 - PrinterHelper.kt</title><style>body{font-family:monospace;padding:16px;background:#0f172a;color:#e2e8f0}pre{white-space:pre-wrap;word-break:break-word}button{padding:12px 18px;font-size:16px;border-radius:10px;border:0;background:#14b8a6;color:#fff}</style></head><body><h3>app/src/main/java/com/fazlaka/app/utils/PrinterHelper.kt</h3><button onclick="navigator.clipboard.writeText(document.getElementById('c').innerText)">نسخ</button><pre id="c">package com.fazlaka.app.utils
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
// دالة الطابعة - زي كاش مصر: بلوتوث مباشر ESC/POS + مشاركة صورة ايصال
object PrinterHelper {
    private val SPP_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    fun getPairedPrinters(): List&lt;BluetoothDevice&gt; {
        val adapter = BluetoothAdapter.getDefaultAdapter() ?: return emptyList()
        return try { adapter.bondedDevices.toList() } catch (e: SecurityException) { emptyList() }
    }
    fun printReceipt(device: BluetoothDevice, receiptText: String, onDone: (Boolean) -&gt; Unit) {
        Thread {
            var socket: BluetoothSocket? = null
            var out: OutputStream? = null
            try {
                socket = device.createRfcommSocketToServiceRecord(SPP_UUID)
                socket.connect()
                out = socket.outputStream
                val esc = byteArrayOf(0x1B, 0x40) // ESC @ reset
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
    fun buildReceipt(shop: String, type: String, amount: String, commission: String, date: String): String {
        return "Fazlaka - $shop\n----------------\n$type\nالمبلغ: $amount\nالعمولة: $commission\nالتاريخ: $date\n----------------\nشكرا لزيارتك"
    }
    fun shareReceiptAsImage(context: Context, receiptText: String) {
        val bitmap = Bitmap.createBitmap(580, 520, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap); canvas.drawColor(Color.WHITE)
        val paint = Paint().apply { color = Color.BLACK; textSize = 30f; textAlign = Paint.Align.CENTER }
        var y = 60f
        receiptText.split("\n").forEach { line -&gt; canvas.drawText(line, 290f, y, paint); y += 42f }
        val file = File(context.cacheDir, "fazlaka_receipt.png")
        FileOutputStream(file).use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        val uri: Uri = FileProvider.getUriForFile(context, context.packageName + ".provider", file)
        val intent = Intent(Intent.ACTION_SEND).apply { type = "image/png"; putExtra(Intent.EXTRA_STREAM, uri); addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) }
        context.startActivity(Intent.createChooser(intent, "مشاركة الايصال"))
    }
}
</pre><script>(function(){var loc=location.href.replace(/#.*$/,"");var ATTR_NAMES=["data-product-id","data-productid","data-product_id","product-id","productid","product_id","data-source-entity-id","source-entity-id","source_entity_id","data-product","data-metadata","data-meta"];var DATASET_KEYS=["productId","productid","product_id","sourceEntityId","sourceentityid","source_entity_id","product","metadata","meta"];function readProductId(value){if(typeof value!=="string"||value.length===0)return null;if(/^[0-9]{6,}$/.test(value))return value;var match=value.match(/(?:product(?:_|-)?id|source(?:_|-)?entity(?:_|-)?id)["'=:\s]+([0-9]{6,})/i);return match?match[1]:null}function extractProductId(start){for(var node=start;node&&node!==document.body;node=node.parentElement){for(var i=0;i<ATTR_NAMES.length;i++){var attrValue=node.getAttribute&&node.getAttribute(ATTR_NAMES[i]);var attrProductId=readProductId(attrValue);if(attrProductId)return attrProductId}var dataset=node.dataset||null;if(dataset){for(var j=0;j<DATASET_KEYS.length;j++){var dataValue=dataset[DATASET_KEYS[j]];var dataProductId=readProductId(dataValue);if(dataProductId)return dataProductId}}}return null}function isInlineMediaSlotElement(node){return !!(node&&node.getAttribute&&node.getAttribute("data-clippy-inline-media-slot")!==null)}function findInlineMediaSlot(start){for(var node=start;node&&node!==document.body;node=node.parentElement){if(isInlineMediaSlotElement(node))return node}return null}function readInlineMediaUrl(node){if(!node)return null;return node.getAttribute&&((node.getAttribute("data-clippy-inline-media-url")||node.getAttribute("data-url")||node.getAttribute("data_url")))||node.href||null}function stripHash(url){return String(url).replace(/#.*$/,"")}function urlsMatch(a,b){if(!a||!b)return false;try{return stripHash(new URL(a,loc).href)===stripHash(new URL(b,loc).href)}catch(_){return stripHash(a)===stripHash(b)}}function isFirstPartyReelUrl(value){try{var url=new URL(value,loc);if(url.protocol!=="https:")return false;var host=url.hostname.toLowerCase();var supported=host==="instagram.com"||host.endsWith(".instagram.com")||host==="facebook.com"||host.endsWith(".facebook.com");return supported&&/\/reels?\//i.test(url.pathname)}catch(_){return false}}function isInlineMediaUrlClick(node,href){var slot=findInlineMediaSlot(node);if(!slot)return false;var slotUrl=readInlineMediaUrl(slot);if(slotUrl)return urlsMatch(href,slotUrl);return isFirstPartyReelUrl(href)}function findDataHref(start){for(var node=start;node&&node!==document.body;node=node.parentElement){if(node.getAttribute){var href=node.getAttribute("data-href")||node.getAttribute("data-url");if(href)return{href:href,node:node}}}return null}var nativeOpen=window.open;window.open=function(url){if(parent!==window&&typeof url==="string"&&/^https?:\/\//.test(url)){parent.postMessage({type:"ecto:usercontent-link-click",href:url},"*");return null}return nativeOpen?nativeOpen.apply(window,arguments):null};document.addEventListener("click",function(e){var target=e.target instanceof Element?e.target:null;if(!target)return;if(parent===window)return;var a=target.closest?target.closest("a[href]"):null;if(a&&a.href&&/^https?:\/\//.test(a.href)&&a.href.replace(/#.*$/,"")!==loc){if(isInlineMediaUrlClick(a,a.href))return;var productId=extractProductId(target)||extractProductId(a);if(productId){e.preventDefault();parent.postMessage({type:"ecto-artifact-link-click",productId:productId},"*");return}e.preventDefault();parent.postMessage({type:"ecto:usercontent-link-click",href:a.href},"*");return}var dataHref=findDataHref(target);if(dataHref&&/^https?:\/\//.test(dataHref.href)&&dataHref.href.replace(/#.*$/,"")!==loc){if(isInlineMediaUrlClick(dataHref.node,dataHref.href))return;e.preventDefault();parent.postMessage({type:"ecto:usercontent-link-click",href:dataHref.href},"*")}},true)})();</script><script>(function(){var FOCUS_TYPE="ecto:artifact-focus-request";var CLOSE_TYPE="ecto:artifact-close-request";function focusArtifactDocument(){var body=document.body;if(!body)return;try{window.focus();}catch(e){}if(!body.hasAttribute("tabindex"))body.setAttribute("tabindex","-1");try{body.focus({preventScroll:true});}catch(e){try{body.focus();}catch(e2){}}}window.addEventListener("message",function(event){if(event.source!==window.parent)return;var data=event.data;if(!data||typeof data!=="object"||data.type!==FOCUS_TYPE)return;if(document.readyState==="loading"){document.addEventListener("DOMContentLoaded",focusArtifactDocument,{once:true});return;}focusArtifactDocument();});window.addEventListener("keydown",function(event){if(event.key!=="Escape")return;window.setTimeout(function(){if(event.defaultPrevented)return;window.parent.postMessage({type:CLOSE_TYPE},"*");},0);});})();</script></body></html>
