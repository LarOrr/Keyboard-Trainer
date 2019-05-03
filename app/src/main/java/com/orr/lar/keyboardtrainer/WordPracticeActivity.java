package com.orr.lar.keyboardtrainer;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
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
    boolean isLastCorrect = true;

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

    //CharSequence text, int start, int deleted, int count
    @OnTextChanged(R.id.etUserInput)
    void onTextInputed(CharSequence s, int start, int deleted, int count) {
//        Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
//        toast.show();
        //For debug
//        System.out.println("int text " + text.toString());
//        System.out.println("int lastCharPos " + lastCharPos);
//        System.out.println("int deleted " + deleted);
//        System.out.println("int count " + count);

        //text is current text in editText
        //lastCharPos is current char position if added char !!!OR position of deleted char
        //if deleted == 1 then char is deleted otherwise char is added
        //count is always 0/1
        if(!isChronometerRunning){
            startChronometer();
        }

        String text = etUserInput.getText().toString();
        int len = text.length();
        int lastCharPos = len - 1;
        if(count == 0 && deleted != 1){
            return;
        } else if (len == 0){
            isLastCorrect = true;
            paintDefault();
            return;
        }

        //Next word
        if(text.charAt(len - 1) == ' '){
            //todo correct

            totalChars += len - 1;
            totalWords++;
            //If correct
            if(isLastCorrect && len - 1 == currentText.length()){
                correctChars += len - 1;
                correctWords++;
                addTextToHistory(true, text.trim());
            } else {
                addTextToHistory(false, text.trim());
            }
            etUserInput.setText("");
            generateNextWord();
            updateTable();
            return;
        }
        //
        if(len > currentText.length()){
            paintTheWord(COLOR_WRONG, 0, currentText.length() - 1);
        } else if(deleted == 0){
            if(text.charAt(lastCharPos) == currentText.charAt(lastCharPos) && isLastCorrect){
                    paintTheWord(COLOR_CORRECT, 0, lastCharPos);
            } else {
                paintTheWord(COLOR_WRONG, 0, lastCharPos);
                isLastCorrect = false;
            }
            //if char is deleted
        } else if(deleted > 0){
            if(text.toString().equals(currentText.substring(0, len))){
                isLastCorrect = true;
                paintTheWord(COLOR_CORRECT, 0, lastCharPos);
            } else {
                paintDefault();
                paintTheWord(COLOR_WRONG, 0, lastCharPos);
            }
        }

        //
//        boolean isNewChar = deleted < text.length();
//        return;
//        String text = etUserInput.getText().toString();
//        if(text.length() == 0)
//            return;
//        totalChars++;
//        if(text.charAt(0) == currentChar){
//            correctChars++;
//            tvEnteredString.append(text);
//            tvEnteredString.append(" ");
//        } else {
//            Spannable coloredText = new SpannableString(text);
//            coloredText.setSpan(new ForegroundColorSpan(Color.RED), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            tvEnteredString.append(coloredText);
//            tvEnteredString.append(" ");
//        }
//        tvOriginalString.append(String.valueOf(currentChar) + " ");
//        generateNextWord();
//        updateTable();
//        //At the end to not trigger charInputed again!!!
//        //Waits 275 millisecs deleted deleting the text
//        (new Handler()).postDelayed(() -> {
//            etUserInput.setText("");
//        }, 200);
    }

    /**
     * Sets tvCurrentText's text with color from start to end !including!
     * @param color
     * @param start
     * @param end
     */
    void paintTheWord(int color, int start, int end){
        SpannableString spannableString = new SpannableString(currentText);
        spannableString.setSpan(new ForegroundColorSpan(color), start, end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableStringBuilder.append(spannableString);
        tvCurrentText.setText(spannableString, TextView.BufferType.SPANNABLE);
    }

    void generateNextWord(){
//        StringBuilder stringBuilder;
//        editable = editable.toString();
//        int length = editable.length();
//        String obj = this.f1678a.etUserInput.getText().toString();
        isLastCorrect = true;
        currentText = wordList.get(random.nextInt(wordList.size()));
        paintDefault();
    }

    void paintDefault(){
        paintTheWord(COLOR_DEFAULT, 0, currentText.length() - 1);
    }
//    void removeSpan(){
//        Spannable str = tvCurrentText.getText();
//        Object spansToRemove[] = str.getSpans(0, tvCurrentText.length(), Object.class);
//        for(Object span: spansToRemove){
//            if(span instanceof CharacterStyle)
//                str.removeSpan(span);
//        }
//    }
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
                if(word.equals(""))
                    continue;
                wordList.add(word);
            }
        } catch(IOException ioe) {
            Toast toast = Toast.makeText(this, "Could not Save words into buffer", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
