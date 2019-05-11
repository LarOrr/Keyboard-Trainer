package com.orr.lar.keyboardtrainer;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SentencePraticeActivity extends WordPracticeActivity {

    @BindView(R.id.tvPrevText)
    TextView tvPrevText;
    @BindView(R.id.tvNextText)
    TextView tvNextText;

    String currentArticle;
    List<String> articleList;
    int wordNumb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Another layout
        layout = R.layout.activity_sentence_pratice;
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tvCurrentText.setTextSize(30);
        //! Total words == count of spaces
        // wordList - это из current article!

    }

    @Override
    void generateNextWord(){


        isLastCharCorrect = true;
//        currentText = wordList.get(random.nextInt(wordList.size()));

        paintDefault();
    }

    void getNewArticle() {
        currentArticle = articleList.get(random.nextInt(articleList.size()));
    }
    @Override
    void extractWords() {
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(language + "_sentences.txt");
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load sentences", Toast.LENGTH_LONG);
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
                articleList.add(word);
            }
        } catch(IOException ioe) {
            Toast toast = Toast.makeText(this, "Could not Save words into buffer", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
