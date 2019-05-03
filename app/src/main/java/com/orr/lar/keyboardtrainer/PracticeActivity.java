package com.orr.lar.keyboardtrainer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PracticeActivity extends AppCompatActivity {
    @BindColor(R.color.green_correct)
    int COLOR_CORRECT;
    @BindColor(R.color.red_incorrect)
    int COLOR_WRONG;

//    @BindColor(android.R.color.primary_text_dark)
    int COLOR_DEFAULT;

    @BindView(R.id.etUserInput)
    EditText etUserInput;
    @BindView(R.id.tvCurrentText)
    TextView tvCurrentText;
    @BindView(R.id.tvCorrectWordsCount)
    TextView tvCorrectWordsCount;
    @BindView(R.id.tvWrongWordsCount)
    TextView tvWrongWordsCount;
    @BindView(R.id.tvShowAccuracy)
    TextView tvShowAccuracy;
    @BindView(R.id.chrTime)
    Chronometer chrTime;
    @BindView(R.id.tvOriginalString)
    TextView tvOriginalString;
    @BindView(R.id.tvEnteredString)
    TextView tvEnteredString;
    @BindView(R.id. tvInfoContent)
    TextView tvInfoContent;

    boolean isChronometerRunning = false;
    String language;
    String currentText;
    SharedPreferences appPref;
//    char currentChar;
    Random random = new Random();
    int correctChars = 0;
    int totalChars = 0;
    int correctWords = 0;
    int totalWords = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        ButterKnife.bind(this);
        COLOR_DEFAULT = tvCurrentText.getCurrentTextColor();
        etUserInput.requestFocus();
        //To disable copy/ paste etc.
        etUserInput.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            public void onDestroyActionMode(ActionMode mode) {
            }
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
//        etUserInput.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        appPref = getApplicationContext().getSharedPreferences(getString(R.string.APP_PREFS), Context.MODE_PRIVATE);
        language = appPref.getString(getString(R.string.train_lang), "en");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isChronometerRunning){
            chrTime.stop();
            isChronometerRunning = false;
        }
    }

    void startChronometer(){
        isChronometerRunning = true;
        chrTime.setBase(SystemClock.elapsedRealtime());
        chrTime.start();
    }

    @SuppressLint("SetTextI18n")
    void updateTable() {
        tvWrongWordsCount.setText(Integer.toString(totalWords - correctWords));
        tvCorrectWordsCount.setText(Integer.toString(correctWords));
        String acc = Integer.toString((int)(100 * ((double)correctWords / totalWords))) + "%";
        tvShowAccuracy.setText(acc);
        //TODO ideya - vmesto speed time + speed перед выходом в диалоге
    }

    void addTextToHistory(boolean isCorrect, String userText) {
        if (isCorrect) {
            tvEnteredString.append(userText);
            tvEnteredString.append(" ");
        } else {
            userText = userText.equals("") ? "__": userText;
            Spannable coloredText = new SpannableString(userText);
            coloredText.setSpan(new ForegroundColorSpan(COLOR_WRONG), 0, userText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvEnteredString.append(coloredText);
            tvEnteredString.append(" ");
        }
        tvOriginalString.append(currentText + " ");
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
