package in.catking.catking.quiz;
//Vishal Raut
//Email me on vr.iitb@gmail.com if you come across any problem
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    LayoutInflater inflater;
    CardView cardMCQ;



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
        cardMCQ = (CardView)findViewById(R.id.cardview_mcq);

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
        mProgress_Bar.setProgressTintList(ColorStateList.valueOf(0xAA92D050));
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
                final String[] myDescription_data = mcq_QuestionData.fromJsonDes(response);
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
                        mOption_A.setEnabled(false);
                        mOption_B.setEnabled(false);
                        mOption_C.setEnabled(false);
                        mOption_D.setEnabled(false);

                        String a = myA_Data[m_Index];
                        setAnswer(a);

                        checkAnswer("a");

//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                updateQuestion();
//                            }
//                        }, SPLASH_TIME);

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
                        mQuestion_Number.setTextColor(0xAA000000);
                        mQuestion_Number.setBackground(null);
                        mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                        mOption_A.setBackgroundColor(0xAAffffff);
                        mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                        mOption_B.setBackgroundColor(0xAAffffff);
                        mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                        mOption_C.setBackgroundColor(0xAAffffff);
                        mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                        mOption_D.setBackgroundColor(0xAAffffff);
                        mOption_A.setEnabled(true);
                        mOption_B.setEnabled(true);
                        mOption_C.setEnabled(true);
                        mOption_D.setEnabled(true);
                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        mScoreText_View.setText("Score " + m_Score+ "/"+ myQuestion_Data.length);

                        LinearLayout ll = findViewById(R.id.p_layout);
                        final LinearLayout child = (LinearLayout) ll.findViewById(R.id.button_layout);
                        ll.removeView(child);
                        final LinearLayout child2 = (LinearLayout) ll.findViewById(R.id.des_layout);
                        ll.removeView(child2);
                        final LinearLayout child3 = (LinearLayout) ll.findViewById(R.id.divider_layout);
                        ll.removeView(child3);
                        final LinearLayout child4 = (LinearLayout) ll.findViewById(R.id.emptyText_layout);
                        ll.removeView(child4);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myA_Data[m_Index];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            mOption_A.setBackgroundColor(0xAA81c784);
                            //mOption_A.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                            mQuestion_Number.setText("Hurray!\n" + "You got it right");
                            mQuestion_Number.setTextColor(0xAA385723);
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                            m_Score = m_Score+1;
                            inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                            LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                            LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                            LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            String des = myDescription_data[m_Index];
                            String check ="";
                            boolean de = check.equalsIgnoreCase(des);
                            if(de == false){
                                LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                parent_layout.addView(divider_layout,cp);

                                parent_layout.addView(description_layout,cp);
                                TextView teTag = new TextView(getApplicationContext());
                                teTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                teTag.setPadding(60,5,30,5);
                                teTag.setTextSize(18);
//                                teTag.setMaxLines(4);
//                                teTag.setVerticalScrollBarEnabled(true);
//                                teTag.setMovementMethod(new ScrollingMovementMethod());
                                Typeface face = Typeface.createFromAsset(getAssets(),
                                        "fonts/tondo_regular.ttf");
                                teTag.setTypeface(face);
                                teTag.setBackground(getResources().getDrawable(R.drawable.text_container_des));
                                //teTag.setTypeface(Typeface.create("tondo_bold", Typeface.NORMAL));
                                teTag.setText(myDescription_data[m_Index]);
                                description_layout.addView(teTag);
                            }

                            parent_layout.addView(activity_layout,cp);
                            parent_layout.addView(emptyTextview_layout,cp);
                            Button btnTag = new Button(getApplicationContext());
                            btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            btnTag.setText("Next Question >");
                            btnTag.setTextColor(0xAAFFFFFF);
                            btnTag.setBackgroundColor(0xAAA6A6A6);
                            activity_layout.addView(btnTag);
                            btnTag.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    updateQuestion();
                                }
                            });
                        }else{
                            mOption_A.setBackgroundColor(0xAAe57272);
                            //mOption_A.setBackground(getResources().getDrawable(R.drawable.text_container_false));
                            mQuestion_Number.setText("Oops!\n" + "You got it wrong");
                            mQuestion_Number.setTextColor(0xAAFFFFFF);
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_false));
                            inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                            LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                            LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                            LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);

                            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            String des = myDescription_data[m_Index];
                            String check ="";
                            boolean de = check.equalsIgnoreCase(des);
                            if(de == false){
                                LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                parent_layout.addView(divider_layout,cp);
//                                ImageView divider = new ImageView(getApplicationContext());
//                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                                lp.setMargins(0, 0, 0, 0);
//                                divider.setLayoutParams(lp);
//                                divider.setBackgroundColor(0xAAAAAAAA);
                                parent_layout.addView(description_layout,cp);
                                TextView teTag = new TextView(getApplicationContext());
                                teTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                teTag.setPadding(70,5,40,5);
                                teTag.setTextSize(18);
//                                teTag.setVerticalScrollBarEnabled(true);
//                                teTag.setMaxLines(4);
//                                teTag.setMovementMethod(new ScrollingMovementMethod());
                                Typeface face = Typeface.createFromAsset(getAssets(),
                                        "fonts/tondo_regular.ttf");
                                teTag.setTypeface(face);
                                teTag.setBackground(getResources().getDrawable(R.drawable.text_container_des));
                                //teTag.setTypeface(Typeface.create("tondo_bold", Typeface.NORMAL));
                                teTag.setText(myDescription_data[m_Index]);
                                description_layout.addView(teTag);
                            }


                            parent_layout.addView(activity_layout,cp);
                            parent_layout.addView(emptyTextview_layout,cp);
                            final Button btnTag = new Button(getApplicationContext());
                            btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            btnTag.setText("Next Question >");
                            btnTag.setTextColor(0xAAFFFFFF);
                            btnTag.setBackgroundColor(0xAAA6A6A6);
                            activity_layout.addView(btnTag);
                            btnTag.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    btnTag.setEnabled(false);
                                    updateQuestion();
                                }
                            });
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

                        mOption_A.setEnabled(false);
                        mOption_B.setEnabled(false);
                        mOption_C.setEnabled(false);
                        mOption_D.setEnabled(false);

                        String a = myA_Data[m_Index];
                        setAnswer(a);

                        checkAnswer("b");

//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                updateQuestion();
//                            }
//                        }, SPLASH_TIME);
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
                        mQuestion_Number.setTextColor(0xAA000000);
                        mQuestion_Number.setBackground(null);
                        mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                        mOption_A.setBackgroundColor(0xAAffffff);
                        mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                        mOption_B.setBackgroundColor(0xAAffffff);
                        mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                        mOption_C.setBackgroundColor(0xAAffffff);
                        mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                        mOption_D.setBackgroundColor(0xAAffffff);
                        mOption_A.setEnabled(true);
                        mOption_B.setEnabled(true);
                        mOption_C.setEnabled(true);
                        mOption_D.setEnabled(true);
                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        mScoreText_View.setText("Score " + m_Score+ "/"+ myQuestion_Data.length);

                        LinearLayout ll = findViewById(R.id.p_layout);
                        final LinearLayout child = (LinearLayout) ll.findViewById(R.id.button_layout);
                        ll.removeView(child);
                        final LinearLayout child2 = (LinearLayout) ll.findViewById(R.id.des_layout);
                        ll.removeView(child2);
                        final LinearLayout child3 = (LinearLayout) ll.findViewById(R.id.divider_layout);
                        ll.removeView(child3);
                        final LinearLayout child4 = (LinearLayout) ll.findViewById(R.id.emptyText_layout);
                        ll.removeView(child4);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myA_Data[m_Index];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            mOption_B.setBackgroundColor(0xAA81c784);
                            //mOption_A.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                            mQuestion_Number.setText("Hurray!\n" + "You got it right");
                            mQuestion_Number.setTextColor(0xAA385723);
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                            m_Score = m_Score+1;
                            inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                            LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                            LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                            LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            String des = myDescription_data[m_Index];
                            String check ="";
                            boolean de = check.equalsIgnoreCase(des);
                            if(de == false){
                                LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                parent_layout.addView(divider_layout,cp);

                                parent_layout.addView(description_layout,cp);
                                TextView teTag = new TextView(getApplicationContext());
                                teTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                teTag.setPadding(60,5,30,5);
                                teTag.setTextSize(18);
//                                teTag.setMaxLines(4);
//                                teTag.setVerticalScrollBarEnabled(true);
//                                teTag.setMovementMethod(new ScrollingMovementMethod());
                                Typeface face = Typeface.createFromAsset(getAssets(),
                                        "fonts/tondo_regular.ttf");
                                teTag.setTypeface(face);
                                teTag.setBackground(getResources().getDrawable(R.drawable.text_container_des));
                                //teTag.setTypeface(Typeface.create("tondo_bold", Typeface.NORMAL));
                                teTag.setText(myDescription_data[m_Index]);
                                description_layout.addView(teTag);
                            }

                            parent_layout.addView(activity_layout,cp);
                            parent_layout.addView(emptyTextview_layout,cp);
                            Button btnTag = new Button(getApplicationContext());
                            btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            btnTag.setText("Next Question >");
                            btnTag.setTextColor(0xAAFFFFFF);
                            btnTag.setBackgroundColor(0xAAA6A6A6);
                            activity_layout.addView(btnTag);
                            btnTag.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    updateQuestion();
                                }
                            });
                        }else{
                            mOption_B.setBackgroundColor(0xAAe57272);
                            //mOption_A.setBackground(getResources().getDrawable(R.drawable.text_container_false));
                            mQuestion_Number.setText("Oops!\n" + "You got it wrong");
                            mQuestion_Number.setTextColor(0xAAFFFFFF);
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_false));
                            inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                            LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                            LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                            LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);

                            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            String des = myDescription_data[m_Index];
                            String check ="";
                            boolean de = check.equalsIgnoreCase(des);
                            if(de == false){
                                LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                parent_layout.addView(divider_layout,cp);
//                                ImageView divider = new ImageView(getApplicationContext());
//                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                                lp.setMargins(0, 0, 0, 0);
//                                divider.setLayoutParams(lp);
//                                divider.setBackgroundColor(0xAAAAAAAA);
                                parent_layout.addView(description_layout,cp);
                                TextView teTag = new TextView(getApplicationContext());
                                teTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                teTag.setPadding(70,5,40,5);
                                teTag.setTextSize(18);
//                                teTag.setMaxLines(4);
//                                teTag.setVerticalScrollBarEnabled(true);
//                                teTag.setMovementMethod(new ScrollingMovementMethod());
                                Typeface face = Typeface.createFromAsset(getAssets(),
                                        "fonts/tondo_regular.ttf");
                                teTag.setTypeface(face);
                                teTag.setBackground(getResources().getDrawable(R.drawable.text_container_des));
                                //teTag.setTypeface(Typeface.create("tondo_bold", Typeface.NORMAL));
                                teTag.setText(myDescription_data[m_Index]);
                                description_layout.addView(teTag);
                            }


                            parent_layout.addView(activity_layout,cp);
                            parent_layout.addView(emptyTextview_layout,cp);
                            final Button btnTag = new Button(getApplicationContext());
                            btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            btnTag.setText("Next Question >");
                            btnTag.setTextColor(0xAAFFFFFF);
                            btnTag.setBackgroundColor(0xAAA6A6A6);
                            activity_layout.addView(btnTag);
                            btnTag.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    btnTag.setEnabled(false);
                                    updateQuestion();
                                }
                            });
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

                        mOption_A.setEnabled(false);
                        mOption_B.setEnabled(false);
                        mOption_C.setEnabled(false);
                        mOption_D.setEnabled(false);

                        String a = myA_Data[m_Index];
                        setAnswer(a);

                        checkAnswer("c");

//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                updateQuestion();
//                            }
//                        }, SPLASH_TIME);
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
                        mQuestion_Number.setTextColor(0xAA000000);
                        mQuestion_Number.setBackground(null);
                        mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                        mOption_A.setBackgroundColor(0xAAffffff);
                        mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                        mOption_B.setBackgroundColor(0xAAffffff);
                        mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                        mOption_C.setBackgroundColor(0xAAffffff);
                        mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                        mOption_D.setBackgroundColor(0xAAffffff);
                        mOption_A.setEnabled(true);
                        mOption_B.setEnabled(true);
                        mOption_C.setEnabled(true);
                        mOption_D.setEnabled(true);
                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        mScoreText_View.setText("Score " + m_Score+ "/"+ myQuestion_Data.length);

                        LinearLayout ll = findViewById(R.id.p_layout);
                        final LinearLayout child = (LinearLayout) ll.findViewById(R.id.button_layout);
                        ll.removeView(child);
                        final LinearLayout child2 = (LinearLayout) ll.findViewById(R.id.des_layout);
                        ll.removeView(child2);
                        final LinearLayout child3 = (LinearLayout) ll.findViewById(R.id.divider_layout);
                        ll.removeView(child3);
                        final LinearLayout child4 = (LinearLayout) ll.findViewById(R.id.emptyText_layout);
                        ll.removeView(child4);

                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myA_Data[m_Index];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            mOption_C.setBackgroundColor(0xAA81c784);
                            //mOption_A.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                            mQuestion_Number.setText("Hurray!\n" + "You got it right");
                            mQuestion_Number.setTextColor(0xAA385723);
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                            m_Score = m_Score+1;
                            inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                            LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                            LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                            LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            String des = myDescription_data[m_Index];
                            String check ="";
                            boolean de = check.equalsIgnoreCase(des);
                            if(de == false){
                                LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                parent_layout.addView(divider_layout,cp);

                                parent_layout.addView(description_layout,cp);
                                TextView teTag = new TextView(getApplicationContext());
                                teTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                teTag.setPadding(60,5,30,5);
                                teTag.setTextSize(18);
//                                teTag.setMaxLines(4);
//                                teTag.setVerticalScrollBarEnabled(true);
//                                teTag.setMovementMethod(new ScrollingMovementMethod());
                                Typeface face = Typeface.createFromAsset(getAssets(),
                                        "fonts/tondo_regular.ttf");
                                teTag.setTypeface(face);
                                teTag.setBackground(getResources().getDrawable(R.drawable.text_container_des));
                                //teTag.setTypeface(Typeface.create("tondo_bold", Typeface.NORMAL));
                                teTag.setText(myDescription_data[m_Index]);
                                description_layout.addView(teTag);
                            }

                            parent_layout.addView(activity_layout,cp);
                            parent_layout.addView(emptyTextview_layout,cp);
                            Button btnTag = new Button(getApplicationContext());
                            btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            btnTag.setText("Next Question >");
                            btnTag.setTextColor(0xAAFFFFFF);
                            btnTag.setBackgroundColor(0xAAA6A6A6);
                            activity_layout.addView(btnTag);
                            btnTag.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    updateQuestion();
                                }
                            });
                        }else{
                            mOption_C.setBackgroundColor(0xAAe57272);
                            //mOption_A.setBackground(getResources().getDrawable(R.drawable.text_container_false));
                            mQuestion_Number.setText("Oops!\n" + "You got it wrong");
                            mQuestion_Number.setTextColor(0xAAFFFFFF);
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_false));
                            inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                            LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                            LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                            LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);

                            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            String des = myDescription_data[m_Index];
                            String check ="";
                            boolean de = check.equalsIgnoreCase(des);
                            if(de == false){
                                LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                parent_layout.addView(divider_layout,cp);
//                                ImageView divider = new ImageView(getApplicationContext());
//                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                                lp.setMargins(0, 0, 0, 0);
//                                divider.setLayoutParams(lp);
//                                divider.setBackgroundColor(0xAAAAAAAA);
                                parent_layout.addView(description_layout,cp);
                                TextView teTag = new TextView(getApplicationContext());
                                teTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                teTag.setPadding(70,5,40,5);
                                teTag.setTextSize(18);
//                                teTag.setMaxLines(4);
//                                teTag.setVerticalScrollBarEnabled(true);
//                                teTag.setMovementMethod(new ScrollingMovementMethod());
                                Typeface face = Typeface.createFromAsset(getAssets(),
                                        "fonts/tondo_regular.ttf");
                                teTag.setTypeface(face);
                                teTag.setBackground(getResources().getDrawable(R.drawable.text_container_des));
                                //teTag.setTypeface(Typeface.create("tondo_bold", Typeface.NORMAL));
                                teTag.setText(myDescription_data[m_Index]);
                                description_layout.addView(teTag);
                            }


                            parent_layout.addView(activity_layout,cp);
                            parent_layout.addView(emptyTextview_layout,cp);
                            final Button btnTag = new Button(getApplicationContext());
                            btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            btnTag.setText("Next Question >");
                            btnTag.setTextColor(0xAAFFFFFF);
                            btnTag.setBackgroundColor(0xAAA6A6A6);
                            activity_layout.addView(btnTag);
                            btnTag.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    btnTag.setEnabled(false);
                                    updateQuestion();
                                }
                            });
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

                        mOption_A.setEnabled(false);
                        mOption_B.setEnabled(false);
                        mOption_C.setEnabled(false);
                        mOption_D.setEnabled(false);

                        String a = myA_Data[m_Index];
                        setAnswer(a);

                        checkAnswer("d");

//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                updateQuestion();
//                            }
//                        }, SPLASH_TIME);
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
                        mQuestion_Number.setTextColor(0xAA000000);
                        mQuestion_Number.setBackground(null);
                        mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                        mOption_A.setBackgroundColor(0xAAffffff);
                        mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                        mOption_B.setBackgroundColor(0xAAffffff);
                        mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                        mOption_C.setBackgroundColor(0xAAffffff);
                        mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                        mOption_D.setBackgroundColor(0xAAffffff);
                        mOption_A.setEnabled(true);
                        mOption_B.setEnabled(true);
                        mOption_C.setEnabled(true);
                        mOption_D.setEnabled(true);
                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        mScoreText_View.setText("Score " + m_Score+ "/"+ myQuestion_Data.length);

                        LinearLayout ll = findViewById(R.id.p_layout);
                        final LinearLayout child = (LinearLayout) ll.findViewById(R.id.button_layout);
                        ll.removeView(child);
                        final LinearLayout child2 = (LinearLayout) ll.findViewById(R.id.des_layout);
                        ll.removeView(child2);
                        final LinearLayout child3 = (LinearLayout) ll.findViewById(R.id.divider_layout);
                        ll.removeView(child3);
                        final LinearLayout child4 = (LinearLayout) ll.findViewById(R.id.emptyText_layout);
                        ll.removeView(child4);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myA_Data[m_Index];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            mOption_D.setBackgroundColor(0xAA81c784);
                            //mOption_D.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                            mQuestion_Number.setText("Hurray!\n" + "You got it right");
                            mQuestion_Number.setTextColor(0xAA385723);
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                            m_Score = m_Score+1;
                            inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                            LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                            LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                            LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            String des = myDescription_data[m_Index];
                            String check ="";
                            boolean de = check.equalsIgnoreCase(des);
                            if(de == false){
                                LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                parent_layout.addView(divider_layout,cp);

                                parent_layout.addView(description_layout,cp);
                                TextView teTag = new TextView(getApplicationContext());
                                teTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                teTag.setPadding(60,5,30,5);
                                teTag.setTextSize(18);
//                                teTag.setMaxLines(4);
//                                teTag.setVerticalScrollBarEnabled(true);
//                                teTag.setMovementMethod(new ScrollingMovementMethod());
                                Typeface face = Typeface.createFromAsset(getAssets(),
                                        "fonts/tondo_regular.ttf");
                                teTag.setTypeface(face);
                                teTag.setBackground(getResources().getDrawable(R.drawable.text_container_des));
                                //teTag.setTypeface(Typeface.create("tondo_bold", Typeface.NORMAL));
                                teTag.setText(myDescription_data[m_Index]);
                                description_layout.addView(teTag);
                            }

                            parent_layout.addView(activity_layout,cp);
                            parent_layout.addView(emptyTextview_layout,cp);
                            Button btnTag = new Button(getApplicationContext());
                            btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            btnTag.setText("Next Question >");
                            btnTag.setTextColor(0xAAFFFFFF);
                            btnTag.setBackgroundColor(0xAAA6A6A6);
                            activity_layout.addView(btnTag);
                            btnTag.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    updateQuestion();
                                }
                            });
                        }else{
                            mOption_D.setBackgroundColor(0xAAe57272);
                            //mOption_D.setBackground(getResources().getDrawable(R.drawable.text_container_false));
                            mQuestion_Number.setText("Oops!\n" + "You got it wrong");
                            mQuestion_Number.setTextColor(0xAAFFFFFF);
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_false));
                            inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                            LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                            LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                            LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);

                            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            String des = myDescription_data[m_Index];
                            String check ="";
                            boolean de = check.equalsIgnoreCase(des);
                            if(de == false){
                                LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                parent_layout.addView(divider_layout,cp);
//                                ImageView divider = new ImageView(getApplicationContext());
//                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                                lp.setMargins(0, 0, 0, 0);
//                                divider.setLayoutParams(lp);
//                                divider.setBackgroundColor(0xAAAAAAAA);
                                parent_layout.addView(description_layout,cp);
                                TextView teTag = new TextView(getApplicationContext());
                                teTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                teTag.setPadding(70,5,40,5);
                                teTag.setTextSize(18);
//                                teTag.setMaxLines(4);
//                                teTag.setVerticalScrollBarEnabled(true);
//                                teTag.setMovementMethod(new ScrollingMovementMethod());
                                Typeface face = Typeface.createFromAsset(getAssets(),
                                        "fonts/tondo_regular.ttf");
                                teTag.setTypeface(face);
                                teTag.setBackground(getResources().getDrawable(R.drawable.text_container_des));
                                //teTag.setTypeface(Typeface.create("tondo_bold", Typeface.NORMAL));
                                teTag.setText(myDescription_data[m_Index]);
                                description_layout.addView(teTag);
                            }


                            parent_layout.addView(activity_layout,cp);
                            parent_layout.addView(emptyTextview_layout,cp);
                            final Button btnTag = new Button(getApplicationContext());
                            btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            btnTag.setText("Next Question >");
                            btnTag.setTextColor(0xAAFFFFFF);
                            btnTag.setBackgroundColor(0xAAA6A6A6);
                            activity_layout.addView(btnTag);
                            btnTag.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    btnTag.setEnabled(false);
                                    updateQuestion();
                                }
                            });
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