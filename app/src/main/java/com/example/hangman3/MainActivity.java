package com.example.hangman3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="debugLetters" ;
    public static final String EXTRA_MESSAGE = "com.example.hangman3.MESSAGE";
    public static final String EXTRA_MESSAGE2 = "com.example.hangman3.MESSAGE2";
    public static final String EXTRA_MESSAGE3 = "com.example.hangman3.MESSAGE3";
    Integer imageArray[] = {
            R.drawable.hangman1,
            R.drawable.hangman2,
            R.drawable.hangman3,
            R.drawable.hangman4,
            R.drawable.hangman5,
            R.drawable.hangman6,
            R.drawable.hangman7,
            R.drawable.hangman8,
            R.drawable.hangman9,
            R.drawable.hangman10,
    };
    String[] words;
    String currentLetter = "";
    String currentWord = "";
    String guessedWord = "";
    String guessedLetters = "";
    String guessedWordComp;

    boolean rightLetter;
    boolean duplicateLetter;
    int fails = 0;
    ArrayList<Character> guessedWordChar = new ArrayList<Character>();
    char currentLetterChar = ' ';
    Random random = new Random();
    ImageView img;
    TextView textView;
    TextView timer;
    final Handler hand = new Handler();
    int count;
    String countString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.imageView);
        img.setImageResource(imageArray[fails]);

        timer = findViewById(R.id.timer);

        textView = findViewById(R.id.textView);
        guessedWordChar.clear();
        words = getResources().getStringArray(R.array.words);
        currentWord = words[random.nextInt(words.length)];
        count = 60;

        for (int i = 0; i < currentWord.length(); i++) {

            guessedWordChar.add('_');
        }
        guessedWord = TextUtils.join(" ",guessedWordChar);
        textView.setText(guessedWord);

    }
    Runnable run = new Runnable() {

        @Override
        public void run() {
            if(count == 0) { //will end if count reach 30 which means 30 second
                countString="0";
                startResult();
            }
            else
            {
                count -= 1; //add 1 every second
                countString = Integer.toString(count);
                timer.setText("timer: "+countString+"sec");
                hand.postDelayed(run, 1000); //will call the runnable after 1 second

            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        hand.postDelayed(run, 1000);

    }

    @Override
    protected void onStop() {
        super.onStop();
        hand.removeCallbacks(run);
    }

    public void checkLetter(View view) {
        EditText editText = findViewById(R.id.editText);
        currentLetter = editText.getText().toString();
        editText.getText().clear();
        currentLetter = currentLetter.toLowerCase();

        rightLetter = false;
        duplicateLetter = false;

        while (true) { //TODO: figure out a better way to exit the method
            if (currentLetter.isEmpty()) {
                Toast.makeText(getApplicationContext(),"Shits empty",Toast.LENGTH_SHORT).show();
                break;
            }
            if (currentLetter.equals(currentWord)){
                Toast.makeText(getApplicationContext(),"YOU WINNER!",Toast.LENGTH_SHORT).show();
                startResult();



            }

            currentLetterChar = currentLetter.charAt(0);
            Log.d(TAG, "checkLetter1: "+currentLetter);
            if (guessedLetters.contains(currentLetter)){
                Toast.makeText(getApplicationContext(),"you have already guessed this letter1",Toast.LENGTH_SHORT).show();
                break;
            }
            for (int i = 0; i < currentWord.length(); i++) {

                if (currentLetterChar == currentWord.charAt(i)) {
                    if (currentLetterChar == guessedWordChar.get(i)) {
                        duplicateLetter = true;
                    } else {
                        guessedWordChar.set(i, currentLetterChar);
                        Toast.makeText(getApplicationContext(),"correct guess",Toast.LENGTH_SHORT).show();
                        rightLetter = true;
                    }
                }

            }
            Log.d(TAG, "checkLetter2: "+guessedWordChar);
            if (duplicateLetter){
                Toast.makeText(getApplicationContext(),"you have already guessed this letter2",Toast.LENGTH_SHORT).show();
                break;
            }
            if (!rightLetter) {
                fails++;
                Log.d(TAG, "checkLetter: fails "+fails);
                guessedLetters = guessedLetters + currentLetterChar;
                try {
                    if (fails == 9) {
                        Toast.makeText(getApplicationContext(),"you lose",Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "checkLetter: loss");
                        startResult();



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                img.setImageResource(imageArray[fails]);
            }
            Log.d(TAG, "checkLetter3: rightLetter = " + rightLetter);
            guessedWord = TextUtils.join(" ",guessedWordChar);
            textView.setText(guessedWord);
            guessedWordComp = TextUtils.join("",guessedWordChar);
            Log.d(TAG, "checkLetter4: " + currentWord + " = " + guessedWordComp);
            if (currentWord.equals(guessedWordComp)) {
                Toast.makeText(getApplicationContext(),"YOU WINNER!",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "checkLetter5: win");
                startResult();

            }
            break;


        }
    }
    public void startResult() {
        Intent intent = new Intent(this, ResultsActivity.class);
        String failsString = Integer.toString(fails);

        intent.putExtra(EXTRA_MESSAGE,failsString);
        intent.putExtra(EXTRA_MESSAGE2, guessedLetters);
        intent.putExtra(EXTRA_MESSAGE3, countString);
        startActivity(intent);
        }

}
