package com.ex2i.barcodesample.kotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator

class KotlinBarcodeInitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        /*
        val integrator = IntentIntegrator(this)
        integrator.captureActivity = BarcodeInitActivity::class.java
        integrator.initiateScan()
         */

        IntentIntegrator(this).apply {
            captureActivity = KotlinBarcodeActivity::class.java
            setBeepEnabled(false)
            initiateScan()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            // 바코드 자동으로 읽어온 결과값
            val scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            val result = scanResult.contents

            // 바코드 PopUp 수동으로 읽어온 결과값
            val popUpResult = data?.getStringExtra("QR")

            val intent = Intent()

            if (result != null) {
                intent.putExtra("QR", result)
                Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            } else {
                intent.putExtra("QR", popUpResult)
            }

            setResult(RESULT_OK, intent)
            finish()

        } else {
            finish()
        }
    }
}
