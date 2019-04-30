package com.example.lar.keyboardtrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PracticeActivity extends AppCompatActivity {

    @BindView(R.id.editTextType)
    EditText editTextType;
    @BindView(R.id.textCurrentText)
    TextView textCurrentText;
    String currentText;
    Random rnd = new Random();;
    int correctChars = 0;
    int totalChars = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        ButterKnife.bind(this);
    }


}
