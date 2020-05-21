package com.ex2i.barcodesample.java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ex2i.barcodesample.R;

public class JavaMainActivity extends AppCompatActivity {

    Button btnBarcode;
    TextView txtResult;

    static final int REQUEST_QR = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBarcode = findViewById(R.id.btn_barcode);
        txtResult = findViewById(R.id.txt_result);

        btnBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JavaMainActivity.this,JavaBarcodeInitActivity.class);
                startActivityForResult(intent,REQUEST_QR);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){

            if (requestCode == REQUEST_QR){

                if (data != null) {
                    txtResult.setText(data.getStringExtra("QR"));
                }

            }

        }
    }
}
