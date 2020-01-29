package com.ex2i.barcodesample.java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ex2i.barcodesample.R;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class JavaBarcodeActivity extends AppCompatActivity implements DecoratedBarcodeView.TorchListener{

    private ImageView imgBarcodeFlash;
    private TextView txtBarcodeInput;
    private TextView txtClose;

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;

    private boolean switchFlashlightButtonCheck = false;

    static final int REQUEST_BARCODE_RESULT = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        imgBarcodeFlash = findViewById(R.id.img_barcode_flash);
        txtClose = findViewById(R.id.txt_barcode_close);
        txtBarcodeInput = findViewById(R.id.txt_barcode_input);
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);
        barcodeScannerView.setTorchListener(this);

        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();

        txtBarcodeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(JavaBarcodeActivity.this, JavaBarcodePopUpActivity.class),REQUEST_BARCODE_RESULT);
                overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top);
            }
        });

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgBarcodeFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFlashlight(view);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //onPause 상태일때 카메라 상태변경
        //capture.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){

            if (requestCode == REQUEST_BARCODE_RESULT){

                if (data != null) {
                    String result = data.getStringExtra("inputQR");

                    Intent intent = new Intent();
                    intent.putExtra("QR",result);
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }

        }
    }

    public void switchFlashlight(View view) {
        if (switchFlashlightButtonCheck) {
            barcodeScannerView.setTorchOff();
            imgBarcodeFlash.setImageResource(R.drawable.ic_flash_off_white_36dp);
        } else {
            barcodeScannerView.setTorchOn();
            imgBarcodeFlash.setImageResource(R.drawable.ic_flash_on_white_36dp);
        }
    }

    @Override
    public void onTorchOn() {
        switchFlashlightButtonCheck = true;
    }

    @Override
    public void onTorchOff() {
        switchFlashlightButtonCheck = false;
    }
}
