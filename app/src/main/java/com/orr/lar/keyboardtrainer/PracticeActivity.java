package com.orr.lar.keyboardtrainer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
    @BindView(R.id.tvInfoContent)
    TextView tvInfoContent;

    //--------Stats-----------
    String practiceModeName;
    //------------------------
    boolean isChronometerRunning = false;
    String language;
    String currentText;
    SharedPreferences appPref;
    SharedPreferences statPref;
//    char currentChar;
    Random random = new Random();
    int correctChars = 0;
    int totalChars = 0;
    int correctWords = 0;
    int totalWords = 0;

    //Default layout
    int layout = R.layout.activity_practice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);
        ButterKnife.bind(this);
        COLOR_DEFAULT = tvCurrentText.getCurrentTextColor();
        etUserInput.requestFocus();
        //To disable copy/ paste etc.
        //todo focus
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
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        etUserInput.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        appPref = getApplicationContext().getSharedPreferences(getString(R.string.APP_PREFS), Context.MODE_PRIVATE);
        language = appPref.getString(getString(R.string.train_lang), "en");
        statPref = getApplicationContext().getSharedPreferences(getString(R.string.STATs_PREFS), Context.MODE_PRIVATE);
    }

//    @Override
//    protected void onStop() {
//        showResults();
//        super.onStop();
//    }

//    @Override
//    protected void onDestroy() {
//
//    }

    @Override
    public void onBackPressed() {
        if(isChronometerRunning){
            chrTime.stop();
            isChronometerRunning = false;
        }
        saveStats();
        showResults();
//        super.onBackPressed();
    }

    @SuppressLint("DefaultLocale")
    void showResults(){
        AlertDialog.Builder dialog = new
                AlertDialog.Builder(PracticeActivity.this);

        StringBuilder sb = new StringBuilder();
        sb.append("Total: ").append(totalWords);
        sb.append("\nCorrect: ").append(correctWords);
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
//        sdf.
        sb.append("\nTime: ").append((SystemClock.elapsedRealtime() - chrTime.getBase()) / 1000).append(" seconds");
        String speed = String.format("%.2f", getSpeed());
//        SpannableString sp = new SpannableString(speed);
//        sp.setSpan(new ForegroundColorSpan(Color.BLUE), 0, speed.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.append("\nAverage speed: ").append(speed).append(" chars per minute");
        dialog.setMessage(sb);
        dialog.setTitle("Practice results");
        dialog.setNeutralButton("OK", (dialog1, which) -> {
            dialog1.dismiss();
            //Closing activity
            PracticeActivity.this.finish();
        });
        dialog.setIcon(R.drawable.ic_speedometer);
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    float getSpeed(){
        long elapsedMillis = SystemClock.elapsedRealtime() - chrTime.getBase();
        float minutes = (float)elapsedMillis / 1000 / 60;
        return correctChars / minutes;

    }
    void saveStats(){
        SharedPreferences.Editor edit = statPref.edit();
        //General
        String str = language + "_" + getString(R.string.correct_chars_all);
        edit.putInt(str, statPref.getInt(str, 0) + correctChars);
        str = language + "_" + getString(R.string.total_chars_all);
        edit.putInt(str, statPref.getInt(str, 0) + totalChars);
        //Mode
        str = language + "_" + getString(R.string.correct_ans_mode) + "_" + practiceModeName;
        edit.putInt(str, statPref.getInt(str, 0) + correctWords);

        str = language + "_" + getString(R.string.total_ans_mode) + "_" + practiceModeName;
        edit.putInt(str, statPref.getInt(str, 0) + totalWords);

        str = language + "_" + getString(R.string.total_time_mode) + "_" + practiceModeName;
        edit.putLong(str, statPref.getLong(str, 0) + SystemClock.elapsedRealtime() - chrTime.getBase());

        str = language + "_" + getString(R.string.record_speed_mode) + "_" + practiceModeName;
        float speed = getSpeed();
        if(speed > statPref.getFloat(str, 0))
            edit.putFloat(str, speed);
        edit.apply();
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
////        if(hasFocus)
//            etUserInput.setSelection(etUserInput.getText().length());
//    }
}
