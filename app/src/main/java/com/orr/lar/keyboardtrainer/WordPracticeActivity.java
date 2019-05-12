package com.orr.lar.keyboardtrainer;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
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

public class WordPracticeActivity extends PracticeActivity {
    //List of texts from file; words in this situation
    List<String> textsList;
    boolean isLastCharCorrect = true;
    String fileName = "_words.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        practiceModeName = getString(R.string.word_mode_short_name);
        tvCurrentText.setTextSize(40);
        etUserInput.setTextSize(24);
        extractWords();
        generateNextWord();
    }

    //CharSequence text, int start, int deleted, int count
    @OnTextChanged(R.id.etUserInput)
    void onTextInputed(CharSequence s, int start, int deleted, int count) {

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
            isLastCharCorrect = true;
            setNewWord();
            return;
        }

        //Next word
        if(text.charAt(len - 1) == ' '){
//            totalChars += len - 1;
//            totalAnswers++;
            addTotalScores(len);
            //If correct
            if(isLastCharCorrect && len - 1 == currentText.length()){
//                correctChars += len - 1;
//                correctAnswers++;
                addCorrectScores(len);
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
            if(text.charAt(lastCharPos) == currentText.charAt(lastCharPos) && isLastCharCorrect){
                    paintTheWord(COLOR_CORRECT, 0, lastCharPos);
            } else {
                paintTheWord(COLOR_WRONG, 0, lastCharPos);
                isLastCharCorrect = false;
            }
            //if char is deleted
        } else if(deleted > 0){
            if(text.toString().equals(currentText.substring(0, len))){
                isLastCharCorrect = true;
                paintTheWord(COLOR_CORRECT, 0, lastCharPos);
            } else {
                setNewWord();
                paintTheWord(COLOR_WRONG, 0, lastCharPos);
            }
        }

    }

    /**
     * @param userInputLen Length including space at the end
     */
    void addTotalScores(int userInputLen){
        totalChars += userInputLen - 1;
        totalAnswers++;
    }

    /**
     * @param userInputLen Length including space at the end
     */
    void addCorrectScores(int userInputLen){
        correctChars += userInputLen - 1;
        correctAnswers++;
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
        isLastCharCorrect = true;
        currentText = textsList.get(random.nextInt(textsList.size()));
        setNewWord();
    }

    void setNewWord(){
        paintTheWord(COLOR_DEFAULT, 0, currentText.length() - 1);
    }

    void extractWords() {
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(language + fileName);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        textsList = new ArrayList<>();
        try {
            while ((line = in.readLine()) != null) {
                String word = line.trim();
                if(word.equals(""))
                    continue;
                textsList.add(word);
            }
        } catch(IOException ioe) {
            Toast toast = Toast.makeText(this, "Could not Save words into buffer", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
