package com.ex2i.barcodesample.java;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class JavaBarcodeInitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(JavaBarcodeActivity.class);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // 바코드 자동으로 읽어온 결과값
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            String result = scanResult.getContents();

            // 바코드 PopUp 수동으로 입력한 결과값
            String pupUpResult = data.getStringExtra("QR");

            // 최종 WebView 화면으로 넘기는 결과값
            Intent goWebViewIntent = new Intent();

            if (result != null) {
                goWebViewIntent.putExtra("QR", result);
            } else {
                goWebViewIntent.putExtra("QR", pupUpResult);
            }

            setResult(RESULT_OK, goWebViewIntent);
            finish();

        } else {

            finish();
        }
    }
}
