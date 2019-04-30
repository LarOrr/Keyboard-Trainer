package com.example.lar.keyboardtrainer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class CharPracticeActivity extends PracticeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    char generateNewText(){
        char c = (char) (rnd.nextInt(26) + 'a');
        return c;
    }


}
