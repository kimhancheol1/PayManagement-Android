package com.example.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logindemo.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Hashtable;

public class QRActivity  extends AppCompatActivity {
    ImageView iv_qrCode;
    Button btn_test, btn_scan;
    EditText edt_text;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qr);
        mContext = QRActivity.this;
        iv_qrCode = findViewById(R.id.iv_qrCode);
        btn_test = findViewById(R.id.btn_test);
        btn_scan = findViewById(R.id.btn_scan);
        edt_text = findViewById(R.id.edt_text);

        //QR코드를 스캔합니다.
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator((Activity) mContext).initiateScan();
            }
        });

        //클릭시 QR코드를 생성하고 다이얼로그에 띄웁니다.
        //바코드인코더를 사용해 QR코드를 생성해서 비트맵이미지에 적용.
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ad = new AlertDialog.Builder(mContext);

                ad.setTitle("QR");
                final ImageView iv = new ImageView(mContext); //qr코드를 띄울 이미지
                String text = edt_text.getText().toString();

                Hashtable hints = new Hashtable();
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 1000, 1000, hints);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    iv.setImageBitmap(bitmap);

                } catch (Exception e) {
                }
                ad.setView(iv);
                ad.setPositiveButton ("ok", (dialog, which) -> dialog.dismiss ());

                ad.setNeutralButton ("center", (dialog, which) -> dialog.dismiss ());

                ad.setNegativeButton ("cancel", (dialog, which) -> dialog.dismiss ());
                ad.show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult (requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents () == null) {
                Toast.makeText (this, "전달할 값이 없습니다.", Toast.LENGTH_LONG).show ();
            } else {
                Toast.makeText (this, "Scanned: " + result.getContents (), Toast.LENGTH_LONG).show ();
                Intent intent = new Intent (getApplicationContext (), ResultActivity.class);
                intent.putExtra ("result", result.getContents ());
                startActivity (intent);

            }
        } else {
            super.onActivityResult (requestCode, resultCode, data);
        }
    }
}
