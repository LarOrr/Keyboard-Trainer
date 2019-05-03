package com.orr.lar.keyboardtrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticsActivity extends AppCompatActivity {

    @BindView(R.id.spStatLang)
    Spinner spStatLang;
    String[] trainLangValues;
    String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ButterKnife.bind(this);
        int pos = spStatLang.getSelectedItemPosition();
        trainLangValues = getResources().getStringArray(R.array.train_lang_val);
        // Might be "en", "ru" etc
        language = String.valueOf(trainLangValues[pos]);
    }

    void updateTable(){

    }


}
