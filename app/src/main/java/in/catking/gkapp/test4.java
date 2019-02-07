package in.catking.gkapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class test4 extends Activity {
    // TODO: Declare member variables here:
    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestion_Number;
    TextView mQuestionTextView;
    TextView mScoreTextView;
    ProgressBar mProgressBar;
    int m_Index; //will increase after answer selection
    String mQuestion;
    int m_Score;  //will increase with correct answer
    int m_Qn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_tf_card);
        if (savedInstanceState != null) {
            m_Score = savedInstanceState.getInt("ScoreKey");
            m_Index = savedInstanceState.getInt("IndexKey");
            m_Qn = savedInstanceState.getInt("Question_Number");

        } else {
            m_Score = 0;
            m_Index = 0;
            m_Qn = 1;

        }
        mTrueButton = (Button) findViewById(R.id.button_option_true);
        mFalseButton = (Button) findViewById(R.id.button_option_false);
        mQuestion_Number = findViewById(R.id.tf_qn_view_card);
        mQuestionTextView = findViewById(R.id.tf_text_view_card);
        mScoreTextView = findViewById(R.id.score_tf_card);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_tf_card);
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
                mQuestion = myQuestionData[m_Index];
                mQuestionTextView.setText(mQuestion);
                mQuestion_Number.setText("Question No: "+m_Qn);
                mScoreTextView.setText("Score" + m_Score + "/" + myQuestionData.length);
                mTrueButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer("true");
                        updateQuestion();
                    }

                    private void updateQuestion() {
                        m_Index = (m_Index + 1) % myQuestionData.length;
                        m_Qn = m_Qn+1;
                        if (m_Index == 0) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(test4.this);
                            alert.setTitle("Quiz is done");
                            alert.setCancelable(false);
                            alert.setMessage("You scored " + m_Score + " points out of " + myQuestionData.length);
                            alert.setPositiveButton("Close Quiz", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            alert.show();
                        }
                        mQuestion = myQuestionData[m_Index];
                        mQuestionTextView.setText(mQuestion);
                        mQuestion_Number.setText("Question No: "+m_Qn);
                        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        mScoreTextView.setText("Score" + m_Score + "/" + myQuestionData.length);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myAnswerData[m_Index];
                        if (userSelection == correctAnswer) {
                            LayoutInflater inflater = getLayoutInflater();
                            View customToast =inflater.inflate(R.layout.bg_toast_true,null);
                            Toast customT = new Toast(getApplicationContext());
                            customT.setView(customToast);
                            customT.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 400);
                            customT.setDuration(Toast.LENGTH_SHORT);
                            customT.show();
                            m_Score = m_Score + 1;

                        } else {
                            LayoutInflater inflater = getLayoutInflater();
                            View customToast =inflater.inflate(R.layout.bg_toast_false,null);
                            Toast customT = new Toast(getApplicationContext());
                            customT.setView(customToast);
                            customT.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 400);
                            customT.setDuration(Toast.LENGTH_SHORT);
                            customT.show();
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
                        m_Index = (m_Index + 1) % myQuestionData.length;
                        m_Qn = m_Qn+1;
                        if (m_Index == 0) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(test4.this);
                            alert.setTitle("Quiz is done");
                            alert.setCancelable(false);
                            alert.setMessage("You scored " + m_Score + " points out of " + myQuestionData.length);
                            alert.setPositiveButton("Close Quiz", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            alert.show();
                        }
                        mQuestion = myQuestionData[m_Index];
                        mQuestionTextView.setText(mQuestion);
                        mQuestion_Number.setText("Question No: "+m_Qn);
                        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        mScoreTextView.setText("Score" + m_Score + "/" + myQuestionData.length);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myAnswerData[m_Index];
                        if (userSelection == correctAnswer) {
                            LayoutInflater inflater = getLayoutInflater();
                            View customToast =inflater.inflate(R.layout.bg_toast_true,null);
                            Toast customT = new Toast(getApplicationContext());
                            customT.setView(customToast);
                            customT.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 400);
                            customT.setDuration(Toast.LENGTH_SHORT);
                            customT.show();
                            m_Score = m_Score + 1;

                        } else {
                            LayoutInflater inflater = getLayoutInflater();
                            View customToast =inflater.inflate(R.layout.bg_toast_false,null);
                            Toast customT = new Toast(getApplicationContext());
                            customT.setView(customToast);
                            customT.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 400);
                            customT.setDuration(Toast.LENGTH_SHORT);
                            customT.show();

                        }
                    }

                });
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
        outState.putInt("ScoreKey", m_Score);
        outState.putInt("IndexKey",m_Index);
        outState.putInt("Question_Number",m_Qn);
    }

}
