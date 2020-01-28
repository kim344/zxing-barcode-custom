package com.ex2i.barcodesample.kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ex2i.barcodesample.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    val REQUEST_QR = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnBarcode.setOnClickListener {
            startActivityForResult(Intent(this@MainActivity,BarcodeInitActivity::class.java),REQUEST_QR)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK){

            if (requestCode == REQUEST_QR){

                data?.let {
                    txtResult.text = data.getStringExtra("QR")
                }

            }

        }
    }
}