package com.ex2i.barcodesample.kotlin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.ex2i.barcodesample.R
import kotlinx.android.synthetic.main.activity_barcode_popup.*

class KotlinBarcodePopUpActivity : AppCompatActivity() {

    private var inputResult : String? = null
    private var isConfirm : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //상태바 제거 Full Screen 가능(adjustResize 일때)
        window.apply {
            setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        }

        setContentView(R.layout.activity_barcode_popup)

        txt_barcode_popup_cancel.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom)
        }

        edit_barcode_popup_input.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(finishInput: Editable?) {
                inputResult = finishInput?.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(inputStr : CharSequence?, start: Int, before: Int, count: Int) {
                isConfirm = if (inputStr?.length!! > 0) {
                    txt_barcode_popup_confirm.setTextColor(resources.getColor(R.color.colorBlack))
                    true
                } else {
                    txt_barcode_popup_confirm.setTextColor(resources.getColor(R.color.colorPushGray))
                    false
                }
            }

        })

        txt_barcode_popup_confirm.setOnClickListener {
            if (isConfirm) {
                val intent = Intent()
                if (inputResult?.length!! > 0) {
                    intent.putExtra("inputQR", inputResult)
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}