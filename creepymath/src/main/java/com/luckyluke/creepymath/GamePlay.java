package com.luckyluke.creepymath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.daimajia.numberprogressbar.OnProgressBarListener;

import java.util.Timer;
import java.util.TimerTask;


public class GamePlay extends Activity implements View.OnClickListener, OnProgressBarListener {

    TextView firstNum, secondNum, operator, resultNum, scoreTv;

    ImageButton yes, no;

    int x, y, sumXY, fakeResult;
    public static int score = 0;

    String operatorStr;

    private Timer timer;

    private NumberProgressBar bnp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_play);


        firstNum = (TextView) findViewById(R.id.firstNumber);
        secondNum = (TextView) findViewById(R.id.secondNumber);
        resultNum = (TextView) findViewById(R.id.resultNum);
        operator = (TextView) findViewById(R.id.operator);
        yes = (ImageButton) findViewById(R.id.yes);
        no = (ImageButton) findViewById(R.id.no);
        scoreTv = (TextView) findViewById(R.id.scoreTV);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        random();

    }

    @Override
    public void onProgressChange(int current, int max) {
        if (current == max) {
            bnp.setProgress(0);
        }
    }

    public void updateProgressBar() {

        final int[] i = {0};

        Log.v("Timer - UI - Start: ", "Timer starts");

        bnp = (NumberProgressBar) findViewById(R.id.numberbar1);
        bnp.setOnProgressBarListener(this);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bnp.incrementProgressBy(1);

                        i[0]++;

                        if (i[0] == bnp.getMax()) {
                            timer.cancel();
                            timer.purge();
                            timer = null;

                            Toast.makeText(getApplication(), "Loser", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplication(), MainActivity.class));
                            finish();

                            Log.v("Finish: ", "Finish at: " + i[0]);
                        }


                    }
                });
            }
        }, 1500, 10);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;

            Log.v("Timer - onStop: ", "Runnable is terminated");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void random() {

        updateProgressBar();

        bnp = (NumberProgressBar) findViewById(R.id.numberbar1);
        bnp.setProgress(0);

        x = Utility.genCeiling(10);
        y = Utility.genCeiling(12);

        operatorStr = Utility.genOperator();
        if (operatorStr.equals("+")) {
            sumXY = x + y;

        } else {
            sumXY = x - y;

        }

        fakeResult = Utility.genNumber(sumXY);

        firstNum.setText(String.valueOf(x));
        secondNum.setText(String.valueOf(y));
        resultNum.setText(String.valueOf(fakeResult));

        operator.setText(operatorStr);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes:
                if (fakeResult == sumXY) {

                    if (timer != null) {
                        timer.cancel();
                        timer.purge();
                        timer = null;
                        Log.v("Timer - Yes - True: ", "Timer is terminated");

                    }

//                    removeHandler("Yes - True");
                    score++;
                    scoreTv.setText(String.valueOf(score));
//                    stopAsyncOperation();

                    startActivity(new Intent(getApplication(), GamePlay.class));
                    finish();

                    //Create progress bar

                } else {
                    if (timer != null) {
                        timer.cancel();
                        timer.purge();
                        timer = null;
                        Log.v("Timer - Yes - False: ", "Timer is terminated");

                    }
//                    removeHandler("Yes - False");
                    Toast.makeText(getApplication(), "Loser", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplication(), MainActivity.class));
                    finish();
                    scoreTv.setText("0");
//                    stopAsyncOperation();

                }
                break;

            case R.id.no:

                if (fakeResult != sumXY) {
                    if (timer != null) {
                        timer.cancel();
                        timer.purge();
                        timer = null;

                        Log.v("Timer - No - True: ", "Timer is terminated");

                    }
//                    removeHandler("No - True");

                    score++;
                    scoreTv.setText(String.valueOf(score));
//                    stopAsyncOperation();
                    startActivity(new Intent(getApplication(), GamePlay.class));
                    finish();

                } else {
                    if (timer != null) {
                        timer.cancel();
                        timer.purge();
                        timer = null;
                        Log.v("Timer - No - False: ", "Timer is terminated");

                    }
//                    removeHandler("No - False");

                    Toast.makeText(getApplication(), "Loser", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplication(), MainActivity.class));
                    finish();
                    scoreTv.setText("0");
//                    stopAsyncOperation();

                }

                break;
        }
    }

}
