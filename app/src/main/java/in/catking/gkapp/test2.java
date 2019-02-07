package in.catking.gkapp;

//Vishal Raut
//Email me on vr.iitb@gmail.com if you come across any problem
import android.util.Log;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class test2 extends Activity {

    // TODO: Declare member variables here:
    Button mOptionA;
    Button mOptionB;
    Button mOptionC;
    Button mOptionD;
    TextView mQuestionTextView;
    TextView mScoreTextView;
    ProgressBar mProgressBar;
    int mIndex; //will increase after answer selection
    String mQuestion;
    int mScore;  //will increase with correct answer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq);
        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");

        } else {
            mScore = 0;
            mIndex = 0;

        }
        //new LetsCollectJSON("https://script.google.com/macros/s/AKfycbxlRWQK1ypdlapRhFWoI1WY3B5ccQsYduZp7EVCIiBGz1vrcNI/exec?MAT477-BuRhAq-xS2vtUhjkwhP7cC3CUJ");

        mOptionA = (Button) findViewById(R.id.button_optionA);
        mOptionB = (Button) findViewById(R.id.button_optionB);
        mOptionC = (Button) findViewById(R.id.button_optionC);
        mOptionD = (Button) findViewById(R.id.button_optionD);
        mQuestionTextView = findViewById(R.id.mcq_text_view);
        mScoreTextView = findViewById(R.id.score_mcq);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_mcq);

        String url = "https://script.google.com/macros/s/AKfycbx5RQQfBcIHooqNpmuOiQcsQ-xPIJFHw_q4hchmYuL_qAgs536b/exec?MpaJWfVQC0wLOJdMJszqFpZCgur5ISDmC";
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestHandle requestHandle = client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("CATKing", "Successful JSON data collection " + response.toString());
                final String[] myQuestionData = mcq_QuestionData.fromJsonQ(response);
                final String[] myAData = mcq_QuestionData.fromJsonCA(response);
                final String[] myOptinaA_data = mcq_QuestionData.fromJsonA(response);
                final String[] myOptinaB_data = mcq_QuestionData.fromJsonB(response);
                final String[] myOptinaC_data = mcq_QuestionData.fromJsonC(response);
                final String[] myOptinaD_data = mcq_QuestionData.fromJsonD(response);
                // TODO: Declare constants here

                final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / myQuestionData.length);

                mQuestion = myQuestionData[mIndex];
                mQuestionTextView.setText(mQuestion);
                mOptionA.setText(myOptinaA_data[mIndex]);
                mOptionB.setText(myOptinaB_data[mIndex]);
                mOptionC.setText(myOptinaC_data[mIndex]);
                mOptionD.setText(myOptinaD_data[mIndex]);

                mScoreTextView.setText("Score" + mScore + "/" + myQuestionData.length);



                //==============================================================================================
                //listening to buttons now for correct answer
                mOptionA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer("a");
                        updateQuestion();
                    }
                    private void updateQuestion() {
                        mIndex = (mIndex+1)% myQuestionData.length;
                        if(mIndex==0){
                            AlertDialog.Builder alert = new AlertDialog.Builder(test2.this);
                            alert.setTitle("Quiz is done");
                            alert.setCancelable(false);
                            alert.setMessage("You scored " +mScore+ " points out of "+myQuestionData.length);
                            alert.setPositiveButton("Close Quiz", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            alert.show();
                        }
                        mQuestion = myQuestionData[mIndex];
                        mQuestionTextView.setText(mQuestion);
                        mOptionA.setText(myOptinaA_data[mIndex]);
                        mOptionB.setText(myOptinaB_data[mIndex]);
                        mOptionC.setText(myOptinaC_data[mIndex]);
                        mOptionD.setText(myOptinaD_data[mIndex]);
                        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        mScoreTextView.setText("Score" + mScore+ "/"+ myQuestionData.length);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myAData[mIndex];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            Toast.makeText(getApplicationContext(),R.string.correct_toast, Toast.LENGTH_SHORT).show();
                            mScore = mScore+1;

                        }else{
                            Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();

                        }
                    }
//                    private void showAns(){
//                        String correctA = myAData[mIndex];
//                        Toast.makeText(getApplicationContext(),correctA,Toast.LENGTH_SHORT).show();
//                    }
                });
                //============================================================================================
                mOptionB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer("b");
                        updateQuestion();
                    }
                    private void updateQuestion() {
                        mIndex = (mIndex+1)% myQuestionData.length;
                        if(mIndex==0){
                            AlertDialog.Builder alert = new AlertDialog.Builder(test2.this);
                            alert.setTitle("Quiz is done");
                            alert.setCancelable(false);
                            alert.setMessage("You scored " +mScore+ " points out of "+myQuestionData.length);
                            alert.setPositiveButton("Close Quiz", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            alert.show();
                        }
                        mQuestion = myQuestionData[mIndex];
                        mQuestionTextView.setText(mQuestion);
                        mOptionA.setText(myOptinaA_data[mIndex]);
                        mOptionB.setText(myOptinaB_data[mIndex]);
                        mOptionC.setText(myOptinaC_data[mIndex]);
                        mOptionD.setText(myOptinaD_data[mIndex]);
                        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        mScoreTextView.setText("Score" + mScore+ "/"+ myQuestionData.length);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myAData[mIndex];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            Toast.makeText(getApplicationContext(),R.string.correct_toast, Toast.LENGTH_SHORT).show();
                            mScore = mScore+1;

                        }else{
                            Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();

                        }
                    }
//                    private void showAns(){
//                        String correctA = myAData[mIndex];
//                        Toast.makeText(getApplicationContext(),correctA,Toast.LENGTH_SHORT).show();
//                    }
                });
                //=========================================================================================
                mOptionC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer("c");
                        updateQuestion();
                    }
                    private void updateQuestion() {
                        mIndex = (mIndex+1)% myQuestionData.length;
                        if(mIndex==0){
                            AlertDialog.Builder alert = new AlertDialog.Builder(test2.this);
                            alert.setTitle("Quiz is done");
                            alert.setCancelable(false);
                            alert.setMessage("You scored " +mScore+ " points out of "+myQuestionData.length);
                            alert.setPositiveButton("Close Quiz", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            alert.show();
                        }
                        mQuestion = myQuestionData[mIndex];
                        mQuestionTextView.setText(mQuestion);
                        mOptionA.setText(myOptinaA_data[mIndex]);
                        mOptionB.setText(myOptinaB_data[mIndex]);
                        mOptionC.setText(myOptinaC_data[mIndex]);
                        mOptionD.setText(myOptinaD_data[mIndex]);
                        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        mScoreTextView.setText("Score" + mScore+ "/"+ myQuestionData.length);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myAData[mIndex ];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            Toast.makeText(getApplicationContext(),R.string.correct_toast, Toast.LENGTH_SHORT).show();
                            mScore = mScore+1;

                        }else{
                            Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();

                        }
                    }
//                    private void showAns(){
//                        String correctA = myAData[mIndex];
//                        Toast.makeText(getApplicationContext(),correctA,Toast.LENGTH_SHORT).show();
//                    }
                });
               //========================================================================================================
                mOptionD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer("d");
                        updateQuestion();
                    }
                    private void updateQuestion() {
                        mIndex = (mIndex+1)% myQuestionData.length;
                        if(mIndex==0){
                            AlertDialog.Builder alert = new AlertDialog.Builder(test2.this);
                            alert.setTitle("Quiz is done");
                            alert.setCancelable(false);
                            alert.setMessage("You scored " +mScore+ " points out of "+myQuestionData.length);
                            alert.setPositiveButton("Close Quiz", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            alert.show();
                        }
                        mQuestion = myQuestionData[mIndex];
                        mQuestionTextView.setText(mQuestion);
                        mOptionA.setText(myOptinaA_data[mIndex]);
                        mOptionB.setText(myOptinaB_data[mIndex]);
                        mOptionC.setText(myOptinaC_data[mIndex]);
                        mOptionD.setText(myOptinaD_data[mIndex]);
                        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        mScoreTextView.setText("Score" + mScore+ "/"+ myQuestionData.length);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myAData[mIndex];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            Toast.makeText(getApplicationContext(),R.string.correct_toast, Toast.LENGTH_SHORT).show();
                            mScore = mScore+1;

                        }else{
                            Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();

                        }
                    }
//                    private void showAns(){
//                        String correctA = myAData[mIndex];
//                        Toast.makeText(getApplicationContext(),correctA,Toast.LENGTH_SHORT).show();
//                    }
                });
                //buttons end here=================================================================================================

        }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject responce) {
                Log.e("CATKing", "Fail JSON" + e.toString());
                Log.d("CATKING", " Fail Status Code" + statusCode);
            }

        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey",mIndex);
    }
}