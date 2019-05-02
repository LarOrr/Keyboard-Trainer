package com.orr.lar.keyboardtrainer;

import android.os.Bundle;

import butterknife.ButterKnife;

public class WordPracticeActivity extends PracticeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
