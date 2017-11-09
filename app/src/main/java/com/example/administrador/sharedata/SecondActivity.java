package com.example.administrador.sharedata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tv_result = (TextView) findViewById(R.id.resultTextView);
        String result_text = getIntent().getStringExtra("result_text");
        tv_result.setText(result_text);
    }
}
