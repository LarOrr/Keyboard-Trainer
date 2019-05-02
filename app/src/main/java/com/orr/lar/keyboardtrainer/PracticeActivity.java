package com.orr.lar.keyboardtrainer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

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
    Random random = new Random();
    int correctChars = 0;
    int totalChars = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        ButterKnife.bind(this);
        etUserInput.requestFocus();
//        etUserInput.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        appPref = getApplicationContext().getSharedPreferences(getString(R.string.APP_PREFS), Context.MODE_PRIVATE);
        language = appPref.getString(getString(R.string.train_lang), "en");

    }

//    @OnClick(R.id.etUserInput)
//    public void onClickUserInput(View view) {
//        //Cursor always at the end
//        etUserInput.setSelection(etUserInput.getText().length());
//    }
//    @OnFocusChange(R.id.etUserInput)
//    public void onFocusChangeUserInput(View view, boolean hasFocus) {
//        //Cursor always at the end
//        //todo
////        if(hasFocus)
//            etUserInput.setSelection(etUserInput.getText().length());
//    }
}
