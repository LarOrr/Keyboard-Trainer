package com.example.lar.keyboardtrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PracticeActivity extends AppCompatActivity {

    @BindView(R.id.etUserInput)
    EditText etUserInput;
    @BindView(R.id.tvCurrentText)
    TextView tvCurrentText;
    @BindView(R.id.tvCorrectCharacterCount)
    TextView tvCorrectCharacterCount;
    @BindView(R.id.tvWrongCharacterCount)
    TextView tvWrongCharacterCount;
    @BindView(R.id.tvShowAccuracy)
    TextView tvShowAccuracy;
    @BindView(R.id.tvShowSpeed)
    TextView tvShowSpeed;
    String currentText;
    char currentChar;
    Random rnd = new Random();
    int correctChars = 0;
    int totalChars = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        //ButterKnife.bind(this);
    }


}
