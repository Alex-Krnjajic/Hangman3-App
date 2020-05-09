package com.example.hangman3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    private static final String TAG ="debugLetters";
    public  int fails;
    public String failsString;
    String guessedLetters;
    String countString;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
         failsString = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Log.d(TAG, "onCreate0: "+failsString);
         fails = Integer.parseInt(failsString);
         guessedLetters = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
         countString = intent.getStringExtra(MainActivity.EXTRA_MESSAGE3);
         textView1 = findViewById(R.id.textView2);
         textView2 = findViewById(R.id.textView3);
         textView3 = findViewById(R.id.textView4);
         time = findViewById(R.id.time);

        if(fails == 9) {
            textView1.setText("YOU LOSE");
        }
        else if (countString.equals("0")) {
            textView1.setText("TIME OUT");
        }
        else {
            textView1.setText("YOU WIN");
        }
        time.setText("time remaining: "+countString);
        textView2.setText("fails: "+failsString);
        textView3.setText("guessed letters: "+guessedLetters);
        Log.d(TAG, "onCreate1: "+failsString);
        Log.d(TAG, "onCreate2: "+guessedLetters);

    }
    public void restart(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
