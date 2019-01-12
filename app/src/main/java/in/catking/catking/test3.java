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

public class test3 extends Activity {

    // TODO: Declare member variables here:
    Button mOption_A;
    Button mOption_B;
    Button mOption_C;
    Button mOption_D;
    TextView mQuestionText_View;
    TextView mScoreText_View;
    ProgressBar mProgress_Bar;
    int m_Index; //will increase after answer selection
    String m_Question;
    int m_Score;  //will increase with correct answer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_card);
        if (savedInstanceState != null) {
            m_Score = savedInstanceState.getInt("ScoreKey");
            m_Index = savedInstanceState.getInt("IndexKey");

        } else {
            m_Score = 0;
            m_Index = 0;

        }
        //new LetsCollectJSON("https://script.google.com/macros/s/AKfycbxlRWQK1ypdlapRhFWoI1WY3B5ccQsYduZp7EVCIiBGz1vrcNI/exec?MAT477-BuRhAq-xS2vtUhjkwhP7cC3CUJ");

        mOption_A = (Button) findViewById(R.id.button_option_A);
        mOption_B = (Button) findViewById(R.id.button_option_B);
        mOption_C = (Button) findViewById(R.id.button_option_C);
        mOption_D = (Button) findViewById(R.id.button_option_D);
        mQuestionText_View = findViewById(R.id.mcq_text_view_card);
        mScoreText_View = findViewById(R.id.score_mcq_card);
        mProgress_Bar = (ProgressBar) findViewById(R.id.progress_bar_mcq_card);

        String url = "https://script.google.com/macros/s/AKfycbx5RQQfBcIHooqNpmuOiQcsQ-xPIJFHw_q4hchmYuL_qAgs536b/exec?MpaJWfVQC0wLOJdMJszqFpZCgur5ISDmC";
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestHandle requestHandle = client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("CATKing", "Successful JSON data collection " + response.toString());
                final String[] myQuestion_Data = mcq_QuestionData.fromJsonQ(response);
                final String[] myA_Data = mcq_QuestionData.fromJsonCA(response);
                final String[] myOptina_A_data = mcq_QuestionData.fromJsonA(response);
                final String[] myOptina_B_data = mcq_QuestionData.fromJsonB(response);
                final String[] myOptina_C_data = mcq_QuestionData.fromJsonC(response);
                final String[] myOptina_D_data = mcq_QuestionData.fromJsonD(response);
                // TODO: Declare constants here

                final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / myQuestion_Data.length);

                m_Question = myQuestion_Data[m_Index];
                mQuestionText_View.setText(m_Question);
                mOption_A.setText(myOptina_A_data[m_Index]);
                mOption_B.setText(myOptina_B_data[m_Index]);
                mOption_C.setText(myOptina_C_data[m_Index]);
                mOption_D.setText(myOptina_D_data[m_Index]);

                mScoreText_View.setText("Score" + m_Score + "/" + myQuestion_Data.length);



                //==============================================================================================
                //listening to buttons now for correct answer
                mOption_A.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer("a");
                        updateQuestion();
                    }
                    private void updateQuestion() {
                        m_Index = (m_Index+1)% myQuestion_Data.length;
                        if(m_Index==0){
                            AlertDialog.Builder alert = new AlertDialog.Builder(test3.this);
                            alert.setTitle("Quiz is done");
                            alert.setCancelable(false);
                            alert.setMessage("You scored " +m_Score+ " points out of "+myQuestion_Data.length);
                            alert.setPositiveButton("Close Quiz", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            alert.show();
                        }
                        m_Question = myQuestion_Data[m_Index];
                        mQuestionText_View.setText(m_Question);
                        mOption_A.setText(myOptina_A_data[m_Index]);
                        mOption_B.setText(myOptina_B_data[m_Index]);
                        mOption_C.setText(myOptina_C_data[m_Index]);
                        mOption_D.setText(myOptina_D_data[m_Index]);
                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        mScoreText_View.setText("Score" + m_Score+ "/"+ myQuestion_Data.length);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myA_Data[m_Index];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            Toast.makeText(getApplicationContext(),R.string.correct_toast, Toast.LENGTH_SHORT).show();
                            m_Score = m_Score+1;

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
                mOption_B.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer("b");
                        updateQuestion();
                    }
                    private void updateQuestion() {
                        m_Index = (m_Index+1)% myQuestion_Data.length;
                        if(m_Index==0){
                            AlertDialog.Builder alert = new AlertDialog.Builder(test3.this);
                            alert.setTitle("Quiz is done");
                            alert.setCancelable(false);
                            alert.setMessage("You scored " +m_Score+ " points out of "+myQuestion_Data.length);
                            alert.setPositiveButton("Close Quiz", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            alert.show();
                        }
                        m_Question = myQuestion_Data[m_Index];
                        mQuestionText_View.setText(m_Question);
                        mOption_A.setText(myOptina_A_data[m_Index]);
                        mOption_B.setText(myOptina_B_data[m_Index]);
                        mOption_C.setText(myOptina_C_data[m_Index]);
                        mOption_D.setText(myOptina_D_data[m_Index]);
                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        mScoreText_View.setText("Score" + m_Score+ "/"+ myQuestion_Data.length);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myA_Data[m_Index];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            Toast.makeText(getApplicationContext(),R.string.correct_toast, Toast.LENGTH_SHORT).show();
                            m_Score = m_Score+1;

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
                mOption_C.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer("c");
                        updateQuestion();
                    }
                    private void updateQuestion() {
                        m_Index = (m_Index+1)% myQuestion_Data.length;
                        if(m_Index==0){
                            AlertDialog.Builder alert = new AlertDialog.Builder(test3.this);
                            alert.setTitle("Quiz is done");
                            alert.setCancelable(false);
                            alert.setMessage("You scored " +m_Score+ " points out of "+myQuestion_Data.length);
                            alert.setPositiveButton("Close Quiz", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            alert.show();
                        }
                        m_Question = myQuestion_Data[m_Index];
                        mQuestionText_View.setText(m_Question);
                        mOption_A.setText(myOptina_A_data[m_Index]);
                        mOption_B.setText(myOptina_B_data[m_Index]);
                        mOption_C.setText(myOptina_C_data[m_Index]);
                        mOption_D.setText(myOptina_D_data[m_Index]);
                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        mScoreText_View.setText("Score" + m_Score+ "/"+ myQuestion_Data.length);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myA_Data[m_Index];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            Toast.makeText(getApplicationContext(),R.string.correct_toast, Toast.LENGTH_SHORT).show();
                            m_Score = m_Score+1;

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
                mOption_D.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer("d");
                        updateQuestion();
                    }
                    private void updateQuestion() {
                        m_Index = (m_Index+1)% myQuestion_Data.length;
                        if(m_Index==0){
                            AlertDialog.Builder alert = new AlertDialog.Builder(test3.this);
                            alert.setTitle("Quiz is done");
                            alert.setCancelable(false);
                            alert.setMessage("You scored " +m_Score+ " points out of "+myQuestion_Data.length);
                            alert.setPositiveButton("Close Quiz", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            alert.show();
                        }
                        m_Question = myQuestion_Data[m_Index];
                        mQuestionText_View.setText(m_Question);
                        mOption_A.setText(myOptina_A_data[m_Index]);
                        mOption_B.setText(myOptina_B_data[m_Index]);
                        mOption_C.setText(myOptina_C_data[m_Index]);
                        mOption_D.setText(myOptina_D_data[m_Index]);
                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        mScoreText_View.setText("Score" + m_Score+ "/"+ myQuestion_Data.length);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myA_Data[m_Index];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            Toast.makeText(getApplicationContext(),R.string.correct_toast, Toast.LENGTH_SHORT).show();
                            m_Score = m_Score+1;

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
        outState.putInt("ScoreKey", m_Score);
        outState.putInt("IndexKey",m_Index);
    }
}
