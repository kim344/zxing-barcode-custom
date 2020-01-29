package com.ex2i.barcodesample.java;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ex2i.barcodesample.R;

public class JavaBarcodePopUpActivity extends AppCompatActivity {

    TextView txtBarcodeConfirm;
    EditText editBarcodeInput;

    String inputResult = "";
    boolean isConfirm = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //상태바 제거 Full Screen 가능 (adjustResize 일때)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        setContentView(R.layout.activity_barcode_popup);

        txtBarcodeConfirm = findViewById(R.id.txt_barcode_popup_confirm);
        editBarcodeInput = findViewById(R.id.edit_barcode_popup_input);

        // 취소 버튼 클릭
        TextView txtBarcodeCancel = findViewById(R.id.txt_barcode_popup_cancel);
        txtBarcodeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom);
            }
        });

        // 바코드 수동 입력값 리스너
        editBarcodeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence inputStr, int start, int before, int count) {

                if (inputStr.length() > 0) {
                    txtBarcodeConfirm.setTextColor(getResources().getColor(R.color.colorBlack));
                    isConfirm = true;
                } else {
                    txtBarcodeConfirm.setTextColor(getResources().getColor(R.color.colorPushGray));
                    isConfirm = false;
                }

            }

            @Override
            public void afterTextChanged(Editable finishInput) {
                inputResult = finishInput.toString();
            }
        });

        // 확인 클릭
        txtBarcodeConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConfirm){
                    Intent intent = new Intent();
                    if (inputResult.length() > 0) {
                        intent.putExtra("inputQR", inputResult);
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

    }
}
