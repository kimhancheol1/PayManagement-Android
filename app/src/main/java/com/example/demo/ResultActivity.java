package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logindemo.R;

public class ResultActivity extends AppCompatActivity {
    TextView tv_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_result);

        Intent intent = getIntent ();

        String result = intent.getStringExtra ("result");

        tv_result=findViewById (R.id.tv_result);
        tv_result.setText (result);
    }
}