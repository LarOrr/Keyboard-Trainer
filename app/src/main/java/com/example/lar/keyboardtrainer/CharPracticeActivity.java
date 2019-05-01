package com.example.lar.keyboardtrainer;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
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

//TODO inher
public class CharPracticeActivity extends AppCompatActivity {
    static final float TEXT_SIZE = 50;
    //char currentChar;
    Alphabet alphabet;

    enum Alphabet{
        RUSSIAN(32, 'а'),
        ENGLISH(26, 'a');
        int size;
        char firstLetter;
        Alphabet(int size, char firstLetter){
            this.size = size;
            this.firstLetter = firstLetter;
        }
    }
    ////////TOdo FIX THIS:
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
    ///////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        ButterKnife.bind(this);
        tvCurrentText.setTextSize(TEXT_SIZE);
        //setMaxLength programmatically
        tvCurrentText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
        //No SUGGESTIONS for edit text
        etUserInput.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        //TODO switch case
        alphabet = Alphabet.RUSSIAN;
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

    @OnTextChanged(R.id.etUserInput)
    void charInputed(CharSequence s, int start, int before, int count){
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
        etUserInput.setText("");
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
