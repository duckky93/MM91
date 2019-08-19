package com.example.duckky.mm91

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.Result
import com.maxis.ereload2.utils.HapticFeedbackUtil
import kotlinx.android.synthetic.main.activity_scan_code.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanCodeActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private lateinit var scannerView: ZXingScannerView
    private lateinit var hapticFeedback: HapticFeedbackUtil

    companion object {
        private const val ARG_FLASH_STATE = "ARG_FLASH_STATE"
        private const val ARG_CAMERA_ID = "ARG_CAMERA_ID"

        private const val SCAN_RESULT = "SCAN_RESULT"

    }

    private var flashState: Boolean? = false
    private var cameraId: Int? = -1
    private var doubleBackToExit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            flashState = savedInstanceState.getBoolean(ARG_FLASH_STATE, false)
            cameraId = savedInstanceState.getInt(ARG_CAMERA_ID, -1)
        } else {
            flashState = false
            cameraId = -1
        }
        setContentView(R.layout.activity_scan_code)

        hapticFeedback = HapticFeedbackUtil()
        hapticFeedback.init(this, true)

        scannerView = ZXingScannerView(this)
        scannerView!!.setFormats(ZXingScannerView.ALL_FORMATS)
        val contentFrame = findViewById<View>(R.id.content_frame) as ViewGroup
        contentFrame.addView(scannerView)
    }

    override fun onResume() {
        super.onResume()
        hapticFeedback.checkSystemSetting()


        scannerView.setResultHandler(this)
        scannerView.startCamera(cameraId!!)
        scannerView.flash = flashState!!
        scannerView.setAutoFocus(true)
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onBackPressed() {
        if (doubleBackToExit) {
            super.onBackPressed()
            return
        }

        doubleBackToExit = true
        Toast.makeText(this, "Click one more time to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({
            doubleBackToExit = false
        }, 2000)
    }

    private fun toggleFlash(flashOn: Boolean) {
        flashState = flashOn
        scannerView!!.flash = flashState as Boolean
        syncFlashStateIcon(flashOn)
    }

    private fun syncFlashStateIcon(flashOn: Boolean) {
        if (flashOn) {
            btn_flash.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_light_on, 0, 0, 0)
        } else {
            btn_flash.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_light_off, 0, 0, 0)
        }
    }


    override fun handleResult(rawResult: Result?) {
        hapticFeedback.vibrate()

        val intent = Intent()
        intent.putExtra(SCAN_RESULT, rawResult!!.text)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}
