package com.orr.lar.keyboardtrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class ChooseModeActivity extends AppCompatActivity {
    @BindString(R.string.APP_PREFS)
    String APP_PREFS;
    @BindString(R.string.train_lang_numb)
    String train_lang_pos;
    @BindString(R.string.train_lang)
    String train_lang;
    SharedPreferences appPrefs;
    @BindView(R.id.spLanguages)
    Spinner spLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        SharedPreferences preferences =getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.clear();
//        editor.apply();
//        appPrefs.edit().apply();
        setContentView(R.layout.activity_choose_mode);
        ButterKnife.bind(this);
        appPrefs = getApplicationContext().getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE); // 0 - for private mode
//        appPrefs.edit().putInt(train_lang_pos, 0).apply();
//        String a = appPrefs.getString(train_lang_pos, "0");
//        Object o = appPrefs.getInt(train_lang_pos, 0);
        int pos = appPrefs.getInt(train_lang_pos, 0);
        spLanguages.setSelection(pos);
    }

    @OnItemSelected(R.id.spLanguages)
    public void languageChanged(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        SharedPreferences.Editor editor = appPrefs.edit();
        //Getting value for spinner:
//        int spinner_pos = spLanguages.getSelectedItemPosition();
        String[] train_lang_val = getResources().getStringArray(R.array.train_lang_val);
        // Might be "en", "ru" etc
        String lang = String.valueOf(train_lang_val[position]);
        //Saving the value - it's more convenient for other activities
        editor.putString(train_lang, lang);
        //Saving the position in Spinner!
        editor.putInt(train_lang_pos, position);
        editor.apply();
//        Toast.makeText(getApplicationContext(),
//                String.valueOf(position), Toast.LENGTH_SHORT).show();

    }

//    @OnClick(R.id.buttonEnterText)
//    public void openEnterText(View view) {
//        Intent intent = new Intent(ChooseModeActivity.this,
//                EnterTextActivity.class);
//        startActivity(intent);
//    }

    @OnClick(R.id.buttonStartCharPractice)
    public void startCharPractice(View view) {
        Intent intent = new Intent(ChooseModeActivity.this,
                CharPracticeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonStartWordPractice)
    public void startStartWordPractice(View view) {
        Intent intent = new Intent(ChooseModeActivity.this,
                WordPracticeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonStartSentencePractice)
    public void startSentencePractice() {
        Intent intent = new Intent(ChooseModeActivity.this,
                SentencePraticeActivity.class);
        startActivity(intent);
    }
}
