package in.catking.catking.quiz;
//Vishal Raut
//Email me on vr.iitb@gmail.com if you come across any problem
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;
import java.net.URL;
import java.util.Arrays;
import java.util.zip.Inflater;

import cz.msebera.android.httpclient.Header;
import in.catking.catking.Login_Mobile;
import in.catking.catking.R;
import in.catking.catking.Splash_Screen_otp;
import in.catking.catking.mcq_QuestionData;
import in.catking.catking.popup_quiz_screen;

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

public class newMCQ extends Activity {

    // TODO: Declare member variables here:
    Button mOption_A;
    Button mOption_B;
    Button mOption_C;
    Button mOption_D;
    TextView mQuestionText_View;
    TextView mQuestion_Number;
    TextView mScoreText_View;
    ProgressBar mProgress_Bar;
    int m_Index; //will increase after answer selection
    String m_Question;
    String url = "";
    int m_Qn;
    int m_Score;  //will increase with correct answer
    private static int SPLASH_TIME = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_card);
        if (savedInstanceState != null) {
            m_Score = savedInstanceState.getInt("ScoreKey");
            m_Index = savedInstanceState.getInt("IndexKey");
            m_Qn = savedInstanceState.getInt("Question_Number");
        } else {
            m_Score = 0;
            m_Index = 0;
            m_Qn = 1;
        }
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        mOption_A = (Button) findViewById(R.id.button_option_A);
        mOption_B = (Button) findViewById(R.id.button_option_B);
        mOption_C = (Button) findViewById(R.id.button_option_C);
        mOption_D = (Button) findViewById(R.id.button_option_D);
        mQuestion_Number = findViewById(R.id.mcq_qn_view_card);
        mQuestionText_View = findViewById(R.id.mcq_text_view_card);
        mScoreText_View = findViewById(R.id.score_mcq_card);
        mProgress_Bar = (ProgressBar) findViewById(R.id.progress_bar_mcq_card);
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
                mQuestion_Number.setText("Question No: "+m_Qn);
                mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                mOption_D.setText("D. "+myOptina_D_data[m_Index]);

                mScoreText_View.setText("Score " + m_Score + "/" + myQuestion_Data.length);



                //==================================================================================
                //listening to buttons now for correct answer
                mOption_A.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String a = myA_Data[m_Index];
                        setAnswer(a);

                        checkAnswer("a");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateQuestion();
                            }
                        }, SPLASH_TIME);
                    }
                    private void updateQuestion() {
                        m_Index = (m_Index+1)% myQuestion_Data.length;
                        m_Qn = m_Qn+1;
                        if(m_Index==0){
                            AlertDialog.Builder alert = new AlertDialog.Builder(newMCQ.this);
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
                        mQuestion_Number.setText("Question No: "+m_Qn);
                        mQuestion_Number.setBackground(null);
                        mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                        mOption_A.setBackgroundColor(0xAAffffff);
                        mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                        mOption_B.setBackgroundColor(0xAAffffff);
                        mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                        mOption_C.setBackgroundColor(0xAAffffff);
                        mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                        mOption_D.setBackgroundColor(0xAAffffff);
                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        mScoreText_View.setText("Score " + m_Score+ "/"+ myQuestion_Data.length);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myA_Data[m_Index];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            mOption_A.setBackgroundColor(0xAA81c784);
                            mQuestion_Number.setText("Correct");
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                            m_Score = m_Score+1;
//                            LayoutInflater inflater = getLayoutInflater();
//                            View customToast =inflater.inflate(R.layout.bg_toast_true,null);
//                            Toast customT = new Toast(getApplicationContext());
//                            customT.setView(customToast);
//                            customT.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 400);
//                            customT.setDuration(Toast.LENGTH_SHORT);
//                            customT.show();
//                            m_Score = m_Score+1;

                        }else{
                            mOption_A.setBackgroundColor(0xAAe57272);
                            mQuestion_Number.setText("Wrong");
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_false));
//                            LayoutInflater inflater = getLayoutInflater();
//                            View customToast =inflater.inflate(R.layout.bg_toast_false,null);
//                            Toast customT = new Toast(getApplicationContext());
//                            customT.setView(customToast);
//                            customT.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 400);
//                            customT.setDuration(Toast.LENGTH_SHORT);
//                            customT.show();
                        }
                    }
                    private void setAnswer(String cA){
                        String aANS = "a";
                        String bANS = "b";
                        String cANS = "c";
                        String dANS = "d";
                        boolean Aa = aANS.equalsIgnoreCase(cA);
                        boolean Bb = bANS.equalsIgnoreCase(cA);
                        boolean Cc = cANS.equalsIgnoreCase(cA);
                        boolean Dd = dANS.equalsIgnoreCase(cA);
                        if (Aa == true){
                                mOption_A.setBackgroundColor(0xAA81c784);
                        }else if(Bb == true){
                                mOption_B.setBackgroundColor(0xAA81c784);
                        }else if(Cc == true){
                                mOption_C.setBackgroundColor(0xAA81c784);
                        }else if(Dd == true){
                                mOption_D.setBackgroundColor(0xAA81c784);
                        }
                    }
                });
                //==================================================================================
                mOption_B.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a = myA_Data[m_Index];
                        setAnswer(a);

                        checkAnswer("b");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateQuestion();
                            }
                        }, SPLASH_TIME);
                    }
                    private void updateQuestion() {
                        m_Index = (m_Index+1)% myQuestion_Data.length;
                        m_Qn = m_Qn+1;
                        if(m_Index==0){
                            AlertDialog.Builder alert = new AlertDialog.Builder(newMCQ.this);
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
                        mQuestion_Number.setText("Question No: "+m_Qn);
                        mQuestion_Number.setBackground(null);
                        mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                        mOption_A.setBackgroundColor(0xAAffffff);
                        mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                        mOption_B.setBackgroundColor(0xAAffffff);
                        mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                        mOption_C.setBackgroundColor(0xAAffffff);
                        mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                        mOption_D.setBackgroundColor(0xAAffffff);
                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        mScoreText_View.setText("Score " + m_Score+ "/"+ myQuestion_Data.length);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myA_Data[m_Index];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            mOption_B.setBackgroundColor(0xAA81c784);
                            mQuestion_Number.setText("Correct");
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                            m_Score = m_Score+1;
//                            LayoutInflater inflater = getLayoutInflater();
//                            View customToast =inflater.inflate(R.layout.bg_toast_true,null);
//                            Toast customT = new Toast(getApplicationContext());
//                            customT.setView(customToast);
//                            customT.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 400);
//                            customT.setDuration(Toast.LENGTH_SHORT);
//                            customT.show();
//                            m_Score = m_Score+1;

                        }else{
                            mOption_B.setBackgroundColor(0xAAe57272);
                            mQuestion_Number.setText("Wrong");
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_false));
//                            LayoutInflater inflater = getLayoutInflater();
//                            View customToast =inflater.inflate(R.layout.bg_toast_false,null);
//                            Toast customT = new Toast(getApplicationContext());
//                            customT.setView(customToast);
//                            customT.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 400);
//                            customT.setDuration(Toast.LENGTH_SHORT);
//                            customT.show();
                        }
                    }
                    private void setAnswer(String cA){
                        String aANS = "a";
                        String bANS = "b";
                        String cANS = "c";
                        String dANS = "d";
                        boolean Aa = aANS.equalsIgnoreCase(cA);
                        boolean Bb = bANS.equalsIgnoreCase(cA);
                        boolean Cc = cANS.equalsIgnoreCase(cA);
                        boolean Dd = dANS.equalsIgnoreCase(cA);
                        if (Aa == true){
                            mOption_A.setBackgroundColor(0xAA81c784);
                        }else if(Bb == true){
                            mOption_B.setBackgroundColor(0xAA81c784);
                        }else if(Cc == true){
                            mOption_C.setBackgroundColor(0xAA81c784);
                        }else if(Dd == true){
                            mOption_D.setBackgroundColor(0xAA81c784);
                        }
                    }
                });
                //==================================================================================
                mOption_C.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a = myA_Data[m_Index];
                        setAnswer(a);

                        checkAnswer("c");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateQuestion();
                            }
                        }, SPLASH_TIME);
                    }
                    private void updateQuestion() {
                        m_Index = (m_Index+1)% myQuestion_Data.length;
                        m_Qn = m_Qn+1;
                        if(m_Index==0){
                            AlertDialog.Builder alert = new AlertDialog.Builder(newMCQ.this);
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
                        mQuestion_Number.setText("Question No: "+m_Qn);
                        mQuestion_Number.setBackground(null);
                        mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                        mOption_A.setBackgroundColor(0xAAffffff);
                        mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                        mOption_B.setBackgroundColor(0xAAffffff);
                        mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                        mOption_C.setBackgroundColor(0xAAffffff);
                        mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                        mOption_D.setBackgroundColor(0xAAffffff);
                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        mScoreText_View.setText("Score " + m_Score+ "/"+ myQuestion_Data.length);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myA_Data[m_Index];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            mOption_C.setBackgroundColor(0xAA81c784);
                            mQuestion_Number.setText("Correct");
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                            m_Score = m_Score+1;
//                            LayoutInflater inflater = getLayoutInflater();
//                            View customToast =inflater.inflate(R.layout.bg_toast_true,null);
//                            Toast customT = new Toast(getApplicationContext());
//                            customT.setView(customToast);
//                            customT.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 400);
//                            customT.setDuration(Toast.LENGTH_SHORT);
//                            customT.show();
//                            m_Score = m_Score+1;

                        }else{
                            mOption_C.setBackgroundColor(0xAAe57272);
                            mQuestion_Number.setText("Wrong");
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_false));
//                            LayoutInflater inflater = getLayoutInflater();
//                            View customToast =inflater.inflate(R.layout.bg_toast_false,null);
//                            Toast customT = new Toast(getApplicationContext());
//                            customT.setView(customToast);
//                            customT.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 400);
//                            customT.setDuration(Toast.LENGTH_SHORT);
//                            customT.show();
                        }
                    }
                    private void setAnswer(String cA){
                        String aANS = "a";
                        String bANS = "b";
                        String cANS = "c";
                        String dANS = "d";
                        boolean Aa = aANS.equalsIgnoreCase(cA);
                        boolean Bb = bANS.equalsIgnoreCase(cA);
                        boolean Cc = cANS.equalsIgnoreCase(cA);
                        boolean Dd = dANS.equalsIgnoreCase(cA);
                        if (Aa == true){
                            mOption_A.setBackgroundColor(0xAA81c784);
                        }else if(Bb == true){
                            mOption_B.setBackgroundColor(0xAA81c784);
                        }else if(Cc == true){
                            mOption_C.setBackgroundColor(0xAA81c784);
                        }else if(Dd == true){
                            mOption_D.setBackgroundColor(0xAA81c784);
                        }
                    }
                });
                //==================================================================================
                mOption_D.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a = myA_Data[m_Index];
                        setAnswer(a);

                        checkAnswer("d");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateQuestion();
                            }
                        }, SPLASH_TIME);
                    }
                    private void updateQuestion() {
                        m_Index = (m_Index+1)% myQuestion_Data.length;
                        m_Qn = m_Qn+1;
                        if(m_Index==0){
                            AlertDialog.Builder alert = new AlertDialog.Builder(newMCQ.this);
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
                        mQuestion_Number.setText("Question No: "+m_Qn);
                        mQuestion_Number.setBackground(null);
                        mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                        mOption_A.setBackgroundColor(0xAAffffff);
                        mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                        mOption_B.setBackgroundColor(0xAAffffff);
                        mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                        mOption_C.setBackgroundColor(0xAAffffff);
                        mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                        mOption_D.setBackgroundColor(0xAAffffff);
                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        mScoreText_View.setText("Score " + m_Score+ "/"+ myQuestion_Data.length);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myA_Data[m_Index];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            mOption_D.setBackgroundColor(0xAA81c784);
                            mQuestion_Number.setText("Correct");
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                            m_Score = m_Score+1;
//                            LayoutInflater inflater = getLayoutInflater();
//                            View customToast =inflater.inflate(R.layout.bg_toast_true,null);
//                            Toast customT = new Toast(getApplicationContext());
//                            customT.setView(customToast);
//                            customT.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 400);
//                            customT.setDuration(Toast.LENGTH_SHORT);
//                            customT.show();
//                            m_Score = m_Score+1;

                        }else{
                            mOption_D.setBackgroundColor(0xAAe57272);
                            mQuestion_Number.setText("Wrong");
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_false));
//                            LayoutInflater inflater = getLayoutInflater();
//                            View customToast =inflater.inflate(R.layout.bg_toast_false,null);
//                            Toast customT = new Toast(getApplicationContext());
//                            customT.setView(customToast);
//                            customT.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 400);
//                            customT.setDuration(Toast.LENGTH_SHORT);
//                            customT.show();
                        }
                    }
                    private void setAnswer(String cA){
                        String aANS = "a";
                        String bANS = "b";
                        String cANS = "c";
                        String dANS = "d";
                        boolean Aa = aANS.equalsIgnoreCase(cA);
                        boolean Bb = bANS.equalsIgnoreCase(cA);
                        boolean Cc = cANS.equalsIgnoreCase(cA);
                        boolean Dd = dANS.equalsIgnoreCase(cA);
                        if (Aa == true){
                            mOption_A.setBackgroundColor(0xAA81c784);
                        }else if(Bb == true){
                            mOption_B.setBackgroundColor(0xAA81c784);
                        }else if(Cc == true){
                            mOption_C.setBackgroundColor(0xAA81c784);
                        }else if(Dd == true){
                            mOption_D.setBackgroundColor(0xAA81c784);
                        }
                    }
                });
                //buttons end here==================================================================

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

