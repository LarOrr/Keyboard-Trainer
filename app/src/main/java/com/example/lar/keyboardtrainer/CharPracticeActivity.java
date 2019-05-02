package com.example.lar.keyboardtrainer;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

import static butterknife.OnTextChanged.Callback.AFTER_TEXT_CHANGED;

//TODO inher
public class CharPracticeActivity extends PracticeActivity {
    static final float TEXT_SIZE = 50;
    char currentChar;
    Alphabet alphabet;
    enum Alphabet{
        RUSSIAN(32, 'а'),
        ENGLISH(26, 'a'),
        SYMB(30, '!');
        int size;
        char firstLetter;
        Alphabet(int size, char firstLetter){
            this.size = size;
            this.firstLetter = firstLetter;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        ButterKnife.bind(this);
        tvCurrentText.setTextSize(TEXT_SIZE);
        //setMaxLength programmatically
        tvCurrentText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
        //No SUGGESTIONS for edit text
       // etUserInput.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        switch (language){
            case "en":
                alphabet = Alphabet.ENGLISH;
                break;
            case "ru":
                alphabet = Alphabet.RUSSIAN;
                break;
            default:
                alphabet = Alphabet.ENGLISH;
                break;
        }
        generateNextChar();
//        TextWatcher tw = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                charInputed(s, start, before,count);
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        };
//        etUserInput.addTextChangedListener(tw);
    }

    //CharSequence s, int start, int before, int count
    @OnTextChanged(value = R.id.etUserInput, callback = AFTER_TEXT_CHANGED)
    void charInputed(Editable s){
        String text = etUserInput.getText().toString();
        if(text.length() == 0)
            return;
        totalChars++;
        if(text.charAt(0) == currentChar){
            correctChars++;
        } //else just totalChars++
        generateNextChar();
//        tvCurrentText.setText(generateNextChar());
        updateTable();
        //At the end to not trigger charInputed again!!!
        //Waits 275 millisecs before deleting the text
        (new Handler()).postDelayed(() -> {
            etUserInput.setText("");
        }, 200);


//        if(s.length() == 0)
//            return;
//
//        totalChars++;
//        if(s.charAt(0) == currentChar){
//            correctChars++;
//        } //else just totalChars++
//        generateNextChar();
////        tvCurrentText.setText(generateNextChar());
//        updateTable();
//        //At the end to not trigger charInputed again!!!
//        s.clear();
    }

    @SuppressLint("SetTextI18n")
    void updateTable() {
        tvWrongCharacterCount.setText(Integer.toString(totalChars - correctChars));
        tvCorrectCharacterCount.setText(Integer.toString(correctChars));
        //TODO?
        String acc = Integer.toString((int)(100 * ((double)correctChars / totalChars))) + "%";
        tvShowAccuracy.setText(acc);
        //TODO speed + timer
        //TODO ideya - vmesto speed time + speed перед выходом в диалоге
    }

    char generateNextChar() {
        //TODO add numbers?
        currentChar = (char)(alphabet.firstLetter + rnd.nextInt(alphabet.size));
        tvCurrentText.setText(Character.toString(currentChar));
        setRandomColor();
        return currentChar;
    }

    void setRandomColor() {
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        tvCurrentText.setTextColor(color);
    }
}
