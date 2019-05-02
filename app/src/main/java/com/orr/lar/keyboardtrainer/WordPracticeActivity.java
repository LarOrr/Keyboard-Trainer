package com.orr.lar.keyboardtrainer;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnTextChanged;

import static butterknife.OnTextChanged.Callback.AFTER_TEXT_CHANGED;

public class WordPracticeActivity extends PracticeActivity {
    //Words from file
    List<String> wordList;
    int currentChar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tvCurrentText.setTextSize(40);
        etUserInput.setTextSize(24);
        extractWords();
        generateNextWord();
    }

//    @OnTextChanged(value = R.id.etUserInput, callback = AFTER_TEXT_CHANGED)
//    void wordInputedTest(Editable s) {
//        String text = etUserInput.getText().toString();
//        if(text.length() == 0)
//            return;
//        totalChars++;
//        if(text.charAt(0) == currentChar){
//            correctChars++;
//        } //else just totalChars++
////        generateNextChar();
////
////        updateTable();
//
////        etUserInput.setText("");
//
//    }

    //CharSequence s, int start, int before, int count
    @OnTextChanged(R.id.etUserInput)
    void wordInputed(CharSequence s, int start, int before, int count) {
//        Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
//        toast.show();
//        System.out.println("int s " + s.toString());
//        System.out.println("int start " + start);
//        System.out.println("int before " + before);
//        System.out.println("int count " + count);
    }

    void generateNextWord(){
//        StringBuilder stringBuilder;
//        editable = editable.toString();
//        int length = editable.length();
//        String obj = this.f1678a.etUserInput.getText().toString();
        currentText = wordList.get(random.nextInt(wordList.size()));
        tvCurrentText.setText(currentText);
    }

    void extractWords() {
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(language + "_words.txt");
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        wordList = new ArrayList<>();
        try {
            while ((line = in.readLine()) != null) {
                String word = line.trim();
                wordList.add(word);
            }
        } catch(IOException ioe) {
            Toast toast = Toast.makeText(this, "Could not Save words into buffer", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
