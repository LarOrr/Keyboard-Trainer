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
import android.text.style.UnderlineSpan;

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
        //As it doesn't work in superclass somehow
        etUserInput.requestFocus();
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
            tvEnteredString.append(text);
            tvEnteredString.append(" ");
        } else {
            Spannable coloredText = new SpannableString(text);
            coloredText.setSpan(new ForegroundColorSpan(Color.RED), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvEnteredString.append(coloredText);
            tvEnteredString.append(" ");
        }
        tvOriginalString.append(String.valueOf(currentChar) + " ");
        generateNextChar();
        updateTable();
        //At the end to not trigger charInputed again!!!
        //Waits 275 millisecs before deleting the text
        (new Handler()).postDelayed(() -> {
            etUserInput.setText("");
        }, 200);
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

    void generateNextChar() {
        currentChar = (char)(alphabet.firstLetter + random.nextInt(alphabet.size));
        tvCurrentText.setText(Character.toString(currentChar));
        setRandomColor();
    }

    void setRandomColor() {
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        tvCurrentText.setTextColor(color);
    }
}
