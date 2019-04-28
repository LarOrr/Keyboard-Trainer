package com.example.lar.keyboardtrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChooseModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mode);
    }

    public void openEnterText(View view) {
        Intent intent = new Intent(ChooseModeActivity.this,
                EnterTextActivity.class);
        startActivity(intent);
    }

    public void startCharPractice(View view) {
        Intent intent = new Intent(ChooseModeActivity.this,
                EnterTextActivity.class);
        startActivity(intent);
    }
}
