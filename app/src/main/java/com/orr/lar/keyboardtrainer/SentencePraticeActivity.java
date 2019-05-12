package com.orr.lar.keyboardtrainer;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SentencePraticeActivity extends WordPracticeActivity {

    @BindView(R.id.tvPrevText)
    TextView tvPrevText;
    @BindView(R.id.tvNextText)
    TextView tvNextText;

    //Start and end of the word in the currentArticle
    int wordStartIndex = 0;
    int wordEndIndex = 0;
    String currentArticle;

    //List of articles - it's now a textsList
//    List<String> articlesList;
    //words is currentArticle.split()
    String[] words;

    //Current word number in textsList array
    int wordNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Another layout
        layout = R.layout.activity_sentence_pratice;
        fileName = "_sentences.txt";
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tvCurrentText.setTextSize(30);
        //! Total words == count of spaces
        // textsList - это из current article!
    }

    @Override
    void generateNextWord(){
        isLastCharCorrect = true;
//        currentText = textsList.get(random.nextInt(textsList.size()));
        if(currentArticle == null || wordNumber + 1 >= words.length)
                getNewArticle();

        if(wordNumber >= 0) {
            //+= size of prev word + space
            wordStartIndex += words[wordNumber].length() + 1;
        }

        //+= size of new word
        wordEndIndex = wordStartIndex + words[++wordNumber].length() - 1;
//        if(wordEndIndex > currentArticle.length())
//            wordEndIndex = currentArticle.length() - 1;
        //text will be set to view in setNewWord
        currentText = words[wordNumber];
        //endIndex in substring excluding

        tvPrevText.setText(currentArticle.substring(0, wordStartIndex));
        tvNextText.setText(currentArticle.substring(wordEndIndex + 1));
//        } else {
//            currentText = words[0];
//            wordEndIndex += wordStartIndex + words[++wordNumber].length();
//        }
        setNewWord();
    }

    void getNewArticle() {
        currentArticle = textsList.get(random.nextInt(textsList.size()));
        words = currentArticle.split(" ");
        wordNumber = -1;
        wordStartIndex = 0;
//        wordEndIndex = words[0].length() - 1;
//        currentText = words[0];
    }

    @Override
    void addTotalScores(int userInputLen) {
        super.addTotalScores(userInputLen);

    }

    @Override
    void addCorrectScores(int userInputLen) {
        super.addCorrectScores(userInputLen);
    }

//    @Override
//    void extractWords() {
//        AssetManager assetManager = getAssets();
//        InputStream inputStream = null;
//        try {
//            inputStream = assetManager.open(language + "_sentences.txt");
//        } catch (IOException e) {
//            Toast toast = Toast.makeText(this, "Could not load sentences", Toast.LENGTH_LONG);
//            toast.show();
//        }
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
//        String line;
//        textsList = new ArrayList<>();
//        try {
//            while ((line = in.readLine()) != null) {
//                String word = line.trim();
//                if(word.equals(""))
//                    continue;
//                textsList.add(word);
//            }
//        } catch(IOException ioe) {
//            Toast toast = Toast.makeText(this, "Could not Save sentences into buffer", Toast.LENGTH_LONG);
//            toast.show();
//        }
//    }
}
