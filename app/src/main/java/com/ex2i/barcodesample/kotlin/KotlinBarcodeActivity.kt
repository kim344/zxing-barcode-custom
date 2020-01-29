package com.ex2i.barcodesample.kotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ex2i.barcodesample.R
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import kotlinx.android.synthetic.main.activity_barcode.*

class KotlinBarcodeActivity : AppCompatActivity(), DecoratedBarcodeView.TorchListener {

    private var capture: CaptureManager? = null
    private var barcodeScannerView: DecoratedBarcodeView? = null
    private var switchFlashlightButtonCheck: Boolean = false

    private val REQUEST_BARCODE_RESULT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)

        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner)
        barcodeScannerView?.setTorchListener(this)
        capture = CaptureManager(this, barcodeScannerView)
        capture?.initializeFromIntent(intent, savedInstanceState)
        capture?.decode()

        txt_barcode_input.setOnClickListener {
            startActivityForResult(
                Intent(this@KotlinBarcodeActivity, KotlinBarcodePopUpActivity::class.java),
                REQUEST_BARCODE_RESULT
            )
            overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top)
        }

        txt_barcode_close.setOnClickListener {
            finish()
        }

        img_barcode_flash.setOnClickListener {
            switchFlashlight()
        }
    }

    override fun onResume() {
        super.onResume()
        capture?.onResume()
    }

    override fun onPause() {
        super.onPause()
        //onPause 상태일때 카메라 상태변경 (뒤 화면 활성화 / 비활성화)
        //capture?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture?.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        finishAffinity()
        //backPressCloseHandler?.onBackPressed()
    }

    private fun switchFlashlight() {
        if (switchFlashlightButtonCheck) {
            barcodeScannerView?.setTorchOff()
            img_barcode_flash.setImageResource(R.drawable.ic_flash_off_white_36dp)
        } else {
            barcodeScannerView?.setTorchOn()
            img_barcode_flash.setImageResource(R.drawable.ic_flash_on_white_36dp)
        }
    }

    override fun onTorchOn() {
        switchFlashlightButtonCheck = true
    }

    override fun onTorchOff() {
        switchFlashlightButtonCheck = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_BARCODE_RESULT) {

                data.let {
                    val result: String? = data?.getStringExtra("inputQR")

                    Intent().apply {
                        putExtra("QR", result)
                        setResult(Activity.RESULT_OK, this)
                        finish()
                    }
                }

            }
        }
    }
}