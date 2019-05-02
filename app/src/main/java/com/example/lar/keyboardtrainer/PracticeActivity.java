package com.example.lar.keyboardtrainer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindInt;
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
    @BindView(R.id.tvOriginalString)
    TextView tvOriginalString;
    @BindView(R.id.tvEnteredString)
    TextView tvEnteredString;

    String language;
    String currentText;
    SharedPreferences appPref;
    char currentChar;
    Random rnd = new Random();
    int correctChars = 0;
    int totalChars = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        ButterKnife.bind(this);

        appPref = getApplicationContext().getSharedPreferences(getString(R.string.APP_PREFS), Context.MODE_PRIVATE);
        language = appPref.getString(getString(R.string.train_lang), "en");
//        language =
    }


}
