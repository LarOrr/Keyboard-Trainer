package com.orr.lar.keyboardtrainer;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import butterknife.ButterKnife;
import butterknife.OnTextChanged;

import static butterknife.OnTextChanged.Callback.AFTER_TEXT_CHANGED;

//TODO inher
public class CharPracticeActivity extends PracticeActivity {
    static final float TEXT_SIZE = 50;
//    char currentChar;
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
        tvInfoContent.setText("");
        //As it doesn't work in superclass somehow
        etUserInput.requestFocus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //CharSequence s, int start, int before, int count
    @OnTextChanged(value = R.id.etUserInput, callback = AFTER_TEXT_CHANGED)
    void charInputed(Editable s){
        if(!isChronometerRunning){
            startChronometer();
        }

        String text = etUserInput.getText().toString();
        if(text.length() == 0)
            return;
        totalChars++;
        if(text.charAt(0) == currentText.charAt(0)){
            correctChars++;
            addTextToHistory(true, text);
        } else {
            addTextToHistory(false, text);
        }
        generateNextChar();
        updateTable();
        //At the end to not trigger charInputed again!!!
        //Waits 275 millisecs before deleting the text
        (new Handler()).postDelayed(() -> {
            etUserInput.setText("");
        }, 200);
    }


    @SuppressLint("SetTextI18n")
    @Override
    void updateTable() {
        tvWrongWordsCount.setText(Integer.toString(totalChars - correctChars));
        tvCorrectWordsCount.setText(Integer.toString(correctChars));
        String acc = Integer.toString((int)(100 * ((double)correctChars / totalChars))) + "%";
        tvShowAccuracy.setText(acc);
        //TODO ideya - vmesto speed time + speed перед выходом в диалоге
    }

    void generateNextChar() {
        currentText = Character.toString((char)(alphabet.firstLetter + random.nextInt(alphabet.size)));
        tvCurrentText.setText(currentText);
        setRandomColor();
    }

    void setRandomColor() {
//        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        //220 to make color not too light
        int colorBorder = 220;
        int color = Color.argb(255, random.nextInt(colorBorder), random.nextInt(colorBorder), random.nextInt(colorBorder));
        tvCurrentText.setTextColor(color);
    }

}
