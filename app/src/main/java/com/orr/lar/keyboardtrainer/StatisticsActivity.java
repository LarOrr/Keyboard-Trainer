package com.orr.lar.keyboardtrainer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

public class StatisticsActivity extends AppCompatActivity {

    @BindView(R.id.spStatLang)
    Spinner spStatLang;

    @BindView(R.id.totalChar)
    TextView totalChar;
    @BindView(R.id.correctChar)
    TextView correctChar;
    @BindView(R.id.totalWords)
    TextView totalWords;
    @BindView(R.id.correctWords)
    TextView correctWords;
    @BindView(R.id.recordSpeedCharMode)
    TextView recordSpeedCharMode;
    @BindView(R.id.recordSpeedWordMode)
    TextView recordSpeedWordMode;
    @BindView(R.id.totalCharMode)
    TextView totalCharMode;
    @BindView(R.id.correctCharMode)
    TextView correctCharMode;

    String[] trainLangValues;
    String language;
    SharedPreferences statPref;
    @BindView(R.id.timeCharMode)
    TextView timeCharMode;
    @BindView(R.id.timeWordMode)
    TextView timeWordMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ButterKnife.bind(this);
        //When activity opens OnItemSelected always triggered!
        statPref = getApplicationContext().getSharedPreferences(getString(R.string.STATs_PREFS), Context.MODE_PRIVATE);
    }

    @OnItemSelected(R.id.spStatLang)
    public void languageChanged(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        int pos = spStatLang.getSelectedItemPosition();
        trainLangValues = getResources().getStringArray(R.array.train_lang_val);
        // Might be "en", "ru" etc
        language = String.valueOf(trainLangValues[pos]);
        updateTable();
    }

    void updateTable() {
        //General
        String str = language + "_" + getString(R.string.correct_chars_all);
        correctChar.setText(String.valueOf(statPref.getInt(str, 0)));

        str = language + "_" + getString(R.string.total_chars_all);
        totalChar.setText(String.valueOf(statPref.getInt(str, 0)));

        //Mode

    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    void updateCharMode() {
        String practiceModeName = getString(R.string.chars_mode_short_name);
        String str = language + "_" + getString(R.string.correct_ans_mode) + "_" + practiceModeName;
        correctCharMode.setText(String.valueOf(statPref.getInt(str, 0)));

        str = language + "_" + getString(R.string.total_ans_mode) + "_" + practiceModeName;
        totalCharMode.setText(String.valueOf(statPref.getInt(str, 0)));

        str = language + "_" + getString(R.string.total_time_mode) + "_" + practiceModeName;
        timeCharMode.setText(String.valueOf((double) statPref.getLong(str, 0) / 1000 / 60) + " minutes");

        str = language + "_" + getString(R.string.record_speed_mode) + "_" + practiceModeName;
        recordSpeedCharMode.setText(String.format("%.2f", statPref.getFloat(str, 0)));
    }
}
