package com.example.lar.keyboardtrainer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class CharPracticeACtivity extends PracticeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    char generateNewText(){
        char c = (char) (rnd.nextInt(26) + 'a');
        return c;
    }
}
