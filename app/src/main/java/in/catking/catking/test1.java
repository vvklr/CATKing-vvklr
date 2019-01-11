package in.catking.catking;

//Vishal Raut
//Email me on vr.iitb@gmail.com if you come across any problem
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;
import java.net.URL;
import java.util.Arrays;
import cz.msebera.android.httpclient.Header;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class test1 extends Activity {

    // TODO: Declare member variables here:
    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestionTextView;
    TextView mScoreTextView;
    ProgressBar mProgressBar;
    int mIndex; //will increase after answer selection
    String mQuestion;
    int mScore;  //will increase with correct answer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_true_false);
        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");

        } else {
            mScore = 0;
            mIndex = 0;

        }
        //new LetsCollectJSON("https://script.google.com/macros/s/AKfycbxlRWQK1ypdlapRhFWoI1WY3B5ccQsYduZp7EVCIiBGz1vrcNI/exec?MAT477-BuRhAq-xS2vtUhjkwhP7cC3CUJ");

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mQuestionTextView = findViewById(R.id.question_text_view);
        mScoreTextView = findViewById(R.id.score);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        // multiple cq https://script.google.com/macros/s/AKfycbx43E6TT_KXcLJecWFdBySt_QiMrTIPPBjWUVbAYMUUBduJjKI/exec?MPtlf0VeKRdOrWxcJ8asoGUwhP7cC3CUJ

        String url = "https://script.google.com/macros/s/AKfycbxlRWQK1ypdlapRhFWoI1WY3B5ccQsYduZp7EVCIiBGz1vrcNI/exec?MAT477-BuRhAq-xS2vtUhjkwhP7cC3CUJ";
            AsyncHttpClient client = new AsyncHttpClient();
            final RequestHandle requestHandle = client.get(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("CATKing", "Successful JSON data collection " + response.toString());
                    final String[] myQuestionData = tf_QuestionData.fromJsonQ(response);
                    final String[] myAnswerData = tf_QuestionData.fromJsonA(response);
                    // TODO: Declare constants here
                    final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / myQuestionData.length);

//                    String[][] mA = {myQuestionData, myAnswerData};
//                    System.out.println(Arrays.deepToString(mA));
//                    Intent intent = new Intent();//(, quiz_true_false.class);
//                    Intent mQdata = intent.putExtra("mQdata", mA);
                    // TODO: Declare member variables here:
//                    for (int i = 0; i<myQuestionData.length;i++){
//                        new TrueFalse(myQuestionData[i],myAnswerData[i]);
//                    }
//
//                }
                    mQuestion = myQuestionData[mIndex];
                    mQuestionTextView.setText(mQuestion);
                    mScoreTextView.setText("Score" + mScore + "/" + myQuestionData.length);

                    mTrueButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkAnswer("true");
                            updateQuestion();
                        }

                        private void updateQuestion() {
                            mIndex = (mIndex+1)% myQuestionData.length;
                            if(mIndex==0){
                                AlertDialog.Builder alert = new AlertDialog.Builder(test1.this);
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
                            mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                            mScoreTextView.setText("Score" + mScore+ "/"+ myQuestionData.length);
                        }

                        private void checkAnswer(String userSelection) {
                            String correctAnswer = myAnswerData[mIndex];
                            if(userSelection==correctAnswer){
                                Toast.makeText(getApplicationContext(),R.string.correct_toast, Toast.LENGTH_SHORT).show();
                                mScore = mScore+1;

                            }else{
                                Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    mFalseButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkAnswer("false");
                            updateQuestion();
                        }
                        private void updateQuestion() {
                            mIndex = (mIndex+1)% myQuestionData.length;
                            if(mIndex==0){
                                AlertDialog.Builder alert = new AlertDialog.Builder(test1.this);
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
                            mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                            mScoreTextView.setText("Score" + mScore+ "/"+ myQuestionData.length);
                        }

                        private void checkAnswer(String userSelection) {
                            String correctAnswer = myAnswerData[mIndex];
                            if(userSelection==correctAnswer){
                                Toast.makeText(getApplicationContext(),R.string.correct_toast, Toast.LENGTH_SHORT).show();
                                mScore = mScore+1;

                            }else{
                                Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


//                    private void updateQuestion(){
//                        mIndex = (mIndex+1)% myQuestionData.length;
//                        if(mIndex==0){
//                            AlertDialog.Builder alert = new AlertDialog.Builder(this);
//                            alert.setTitle("Quiz is done");
//                            alert.setCancelable(false);
//                            alert.setMessage("You scored " +mScore+ " points out of "+myQuestionData.length);
//                            alert.setPositiveButton("Close Quiz", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    finish();
//                                }
//                            });
//                            alert.show();
//                        }
//                        mQuestion = myQuestionData[mIndex];
//                        mQuestionTextView.setText(mQuestion);
//                        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
//                        mScoreTextView.setText("Score" + mScore+ "/"+ myQuestionData.length);
//                    }
//                    private void checkAnswer(String userSelection){
//                        String correctAnswer = myAnswerData[mIndex];
//                        if(userSelection==correctAnswer){
//                            Toast.makeText(getApplicationContext(),R.string.correct_toast, Toast.LENGTH_SHORT).show();
//                            mScore = mScore+1;
//
//                        }else{
//                            Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    }
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
