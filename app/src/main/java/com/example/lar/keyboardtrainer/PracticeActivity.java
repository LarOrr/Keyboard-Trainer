package com.example.lar.keyboardtrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class PracticeActivity extends AppCompatActivity {
    EditText editTextType;
    TextView textCurrentText;
    String currentText;
    Random rnd;
    int correctChars = 0;
    int totalChars = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        editTextType = (EditText) findViewById(R.id.editTextType);
        textCurrentText = (TextView) findViewById(R.id.textCurrentText);
        rnd = new Random();


        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.equals("")) {
                    return;
                }
                String typedText = editTextType.getText().toString();
                if(typedText.equals(currentText)){
                    editTextType.setText("");

                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        editTextType.addTextChangedListener(tw);
    }


}
