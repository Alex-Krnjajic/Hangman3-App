package com.example.hangman3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    private static final String TAG ="debugLetters";
    public  int fails;
    public String failsString;
    String guessedLetters;
    TextView textView1;
    TextView textView2;
    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
         failsString = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Log.d(TAG, "onCreate0: "+failsString);
         fails = Integer.parseInt(failsString);
         guessedLetters = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
         textView1 = findViewById(R.id.textView2);
         textView2 = findViewById(R.id.textView3);
         textView3 = findViewById(R.id.textView4);

        if(fails >= 11) {
            textView1.setText("YOU LOSE");
        }
        else {
            textView1.setText("YOU WIN");
        }
        textView2.setText("fails: "+failsString);
        textView3.setText("guessed letters: "+guessedLetters);
        Log.d(TAG, "onCreate1: "+failsString);
        Log.d(TAG, "onCreate2: "+guessedLetters);

    }

}
