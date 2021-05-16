package com.ikar.arithmetike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    public Integer answer;
    public Integer level = 0;
    public Integer score = 0;
    public Integer highScore = 0;
    public Integer timeToSkip = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //EditText userAnswer = findViewById(R.id.answerText);
        //userAnswer.requestFocus();

        createRandom();
        scoreUpdate();
        timer();
        levelChange();
    }

    public void onKeyboardClick(View v){
        EditText userAnswer = findViewById(R.id.answerText);

        String result = userAnswer.getText().toString();
        switch(v.getId()){
            case R.id.num0:
                result = result + "0";
                break;
            case R.id.num1:
                result = result + "1";
                break;
            case R.id.num2:
                result = result + "2";
                break;
            case R.id.num3:
                result = result + "3";
                break;
            case R.id.num4:
                result = result + "4";
                break;
            case R.id.num5:
                result = result + "5";
                break;
            case R.id.num6:
                result = result + "6";
                break;
            case R.id.num7:
                result = result + "7";
                break;
            case R.id.num8:
                result = result + "8";
                break;
            case R.id.num9:
                result = result + "9";
                break;
        }

        userAnswer.setText(result);
        if (result.length() == answer.toString().length()){
            checkAnswer();
        }

    }

    public void createRandom(){
        TextView a = findViewById(R.id.aText);
        TextView b = findViewById(R.id.bText);
        TextView operation = findViewById(R.id.operationText);

        Integer range = 1;
        Integer operation_range = 0;
        switch (level){
            case 1:
                range = 10;
                operation_range = 2;
                break;
            case 2:
                range = 10;
                operation_range = 3;
                break;
            case 3:
                range = 20;
                operation_range = 2;
                break;
            case 4:
                range = 20;
                operation_range = 3;
                break;
            case 5:
                range = 60;
                operation_range = 2;
                break;
        }

        Integer random1 = (int)(1 + (Math.random() * range));
        Integer random2 = (int)(1 + (Math.random() * range));
        Integer random3 = (int)(Math.random() * operation_range);

        Integer aInt;
        Integer bInt;
        if (random1 > random2) {
            aInt = random1;
            bInt = random2;
        }
        else{
            aInt = random2;
            bInt = random1;
        }

        a.setText(aInt.toString());
        b.setText(bInt.toString());

        switch (random3){
            case 0:
                operation.setText("+");
                answer = aInt + bInt;
                break;
            case 1:
                operation.setText("-");
                answer = aInt - bInt;
                break;
            case 2:
                operation.setText("x");
                answer = aInt * bInt;
                break;
        }
    }

    public void levelChange(){
        Integer prevLevel = level;

        switch (score){
            case 0:
                level = 1;
                break;
            case 5:
                level = 2;
                break;
            case 20:
                level = 3;
                break;
        }

        if(prevLevel != level){
            TextView levelText = findViewById(R.id.lvlText);
            levelText.setText(level.toString());
            if(prevLevel < level) {
                mediaPlayer = MediaPlayer.create(this, R.raw.level_up);
            }
        }
    }

    public class timerThread extends Thread{
        @Override
        public void run(){
            while(true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timeToSkip--;
                if (timeToSkip < 1){
                    checkAnswer();
                }
            }
        }
    }
    public void timer(){
        /*Thread thread = new timerThread();
        thread.start();*/
    }

    public void checkAnswer(){
        EditText userAnswer = findViewById(R.id.answerText);
        ConstraintLayout background = findViewById(R.id.background);

        String userAnswerText = userAnswer.getText().toString();
        if (userAnswerText.equals("")){
                userAnswerText = "-2";
        }

        String answerText = answer.toString();
        if (answerText.equals(userAnswerText)){
            background.setBackground(getDrawable(R.color.colorOk));
            score++;
            mediaPlayer = MediaPlayer.create(this, R.raw.ok);
        }
        else{
            background.setBackground(getDrawable(R.color.colorError));
            score = 0;
            mediaPlayer = MediaPlayer.create(this, R.raw.error);
        }
        scoreUpdate();
        levelChange();
        mediaPlayer.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                EditText userAnswer = findViewById(R.id.answerText);
                ConstraintLayout background = findViewById(R.id.background);

                background.setBackground(getDrawable(R.color.colorBackground));
                createRandom();
                userAnswer.setText("");
            }
        }, 250);
    }

    public void scoreUpdate(){
        timeToSkip = 5;

        TextView scoreText = findViewById(R.id.scoreText);
        TextView highScoreText = findViewById(R.id.HSText);

        if (score > highScore){
            highScore = score;
        }

        highScoreText.setText(highScore.toString());
        scoreText.setText(score.toString());
    }
}
