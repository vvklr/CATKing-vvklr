package in.catking.catking.quiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import in.catking.catking.R;
import in.catking.catking.tf_QuestionData;

public class new_TF extends Activity {
    // TODO: Declare member variables here:
    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestion_Number;
    TextView mQuestionTextView;
    TextView mScoreTextView;
    ProgressBar mProgressBar;
    int m_Index; //will increase after answer selection
    String mQuestion;
    String url = "";
    int m_Score;  //will increase with correct answer
    int m_Qn;
    int m_Qr;
    int m_Count;
    LayoutInflater inflater;

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
            m_Count =0;

        }
        mTrueButton = (Button) findViewById(R.id.button_option_true);
        mFalseButton = (Button) findViewById(R.id.button_option_false);
        mQuestion_Number = findViewById(R.id.tf_qn_view_card);
        mQuestionTextView = findViewById(R.id.tf_text_view_card);
        mScoreTextView = findViewById(R.id.score_tf_card);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_tf_card);
        mProgressBar.setProgressTintList(ColorStateList.valueOf(0xAA92D050));
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestHandle requestHandle = client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("CATKing", "Successful JSON data collection " + response.toString());
                final String[] myQuestionData = tf_QuestionData.fromJsonQ(response);
                final String[] myA_Data = tf_QuestionData.fromJsonA(response);
                final String[] myDesData = tf_QuestionData.fromJsonDes(response);
                // TODO: Declare constants here
                final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / myQuestionData.length);
                mQuestion = myQuestionData[m_Index];
                mQuestionTextView.setText(mQuestion);
                mQuestion_Number.setText("Question No: "+m_Qn);

                m_Qr = (myQuestionData.length)-m_Index;
                mScoreTextView.setText(m_Qr+" question to go.");
                //mScoreTextView.setText("Score" + m_Score + "/" + myQuestionData.length);


                mTrueButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTrueButton.setEnabled(false);
                        mFalseButton.setEnabled(false);
                        String a = myA_Data[m_Index];
                        setAnswer(a);

                        checkAnswer("t");

                    }

                    private void updateQuestion() {
                        m_Index = (m_Index + 1) % myQuestionData.length;
                        m_Count = (m_Count+1);
                        m_Qn = m_Qn+1;
                        m_Qr = (myQuestionData.length)-m_Count;
                        if (m_Index == 0) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(new_TF.this);
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
                        if(m_Qr==0){
                            mScoreTextView.setText("Hurray 100% progress. You are done!");
                        }else {
                            mScoreTextView.setText(m_Qr+" more question to go.");
                        }
                        mQuestion = myQuestionData[m_Index];
                        mQuestionTextView.setText(mQuestion);
                        mQuestion_Number.setText("Question No: "+m_Qn);

                        mQuestion_Number.setBackground(null);
                        mQuestion_Number.setTextColor(0xAA000000);
                        mTrueButton.setBackgroundColor(0xAAffffff);
                        mFalseButton.setBackgroundColor(0xAAffffff);

                        mTrueButton.setEnabled(true);
                        mFalseButton.setEnabled(true);

                        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100


                        LinearLayout ll = findViewById(R.id.tf_layout);
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
                            mTrueButton.setBackgroundColor(0xAA81c784);
                            mQuestion_Number.setText("Hurray!\n" + "You got it right");
                            mQuestion_Number.setTextColor(0xAA385723);
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                            m_Score = m_Score+1;

                            inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                            LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                            LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                            LinearLayout parent_layout = (LinearLayout) findViewById(R.id.tf_layout);
                            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            String des = myDesData[m_Index];
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
                                teTag.setText(myDesData[m_Index]);
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
                            mTrueButton.setBackgroundColor(0xAAe57272);
                            //mOption_A.setBackground(getResources().getDrawable(R.drawable.text_container_false));
                            mQuestion_Number.setText("Oops!\n" + "You got it wrong");
                            mQuestion_Number.setTextColor(0xAAFFFFFF);
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_false));
                            inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                            LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                            LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                            LinearLayout parent_layout = (LinearLayout) findViewById(R.id.tf_layout);

                            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            String des = myDesData[m_Index];
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
                                teTag.setText(myDesData[m_Index]);
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
//                        if (userSelection == correctAnswer) {
//                            LayoutInflater inflater = getLayoutInflater();
//                            View customToast =inflater.inflate(R.layout.bg_toast_true,null);
//                            Toast customT = new Toast(getApplicationContext());
//                            customT.setView(customToast);
//                            customT.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 400);
//                            customT.setDuration(Toast.LENGTH_SHORT);
//                            customT.show();
//                            m_Score = m_Score + 1;
//
//                        } else {
//                            LayoutInflater inflater = getLayoutInflater();
//                            View customToast =inflater.inflate(R.layout.bg_toast_false,null);
//                            Toast customT = new Toast(getApplicationContext());
//                            customT.setView(customToast);
//                            customT.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 400);
//                            customT.setDuration(Toast.LENGTH_SHORT);
//                            customT.show();
//                        }
                    }
                    private void setAnswer(String cA){
                        String tANS = "t";
                        String fANS = "f";

                        boolean Aa = tANS.equalsIgnoreCase(cA);
                        boolean Bb = fANS.equalsIgnoreCase(cA);
                        if (Aa == true){
                            mTrueButton.setBackgroundColor(0xAA81c784);
                        }else if(Bb == true){
                            mFalseButton.setBackgroundColor(0xAA81c784);
                        }
                    }
                });
                mFalseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTrueButton.setEnabled(false);
                        mFalseButton.setEnabled(false);
                        String a = myA_Data[m_Index];
                        setAnswer(a);

                        checkAnswer("f");

                    }

                    private void updateQuestion() {
                        m_Index = (m_Index + 1) % myQuestionData.length;
                        m_Qn = m_Qn+1;
                        m_Count = (m_Count+1);
                        m_Qr = (myQuestionData.length)-m_Count;
                        if (m_Index == 0) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(new_TF.this);
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
                        if(m_Qr==0){
                            mScoreTextView.setText("Hurray 100% progress. You are done!");
                        }else {
                            mScoreTextView.setText(m_Qr+" more question to go.");
                        }
                        mQuestion = myQuestionData[m_Index];
                        mQuestionTextView.setText(mQuestion);
                        mQuestion_Number.setText("Question No: "+m_Qn);

                        mQuestion_Number.setBackground(null);
                        mQuestion_Number.setTextColor(0xAA000000);
                        mTrueButton.setBackgroundColor(0xAAffffff);
                        mFalseButton.setBackgroundColor(0xAAffffff);

                        mTrueButton.setEnabled(true);
                        mFalseButton.setEnabled(true);

                        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        //mScoreTextView.setText("Score" + m_Score + "/" + myQuestionData.length);

                        LinearLayout ll = findViewById(R.id.tf_layout);
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
                            mFalseButton.setBackgroundColor(0xAA81c784);
                            //mOption_A.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                            mQuestion_Number.setText("Hurray!\n" + "You got it right");
                            mQuestion_Number.setTextColor(0xAA385723);
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                            m_Score = m_Score+1;
                            inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                            LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                            LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                            LinearLayout parent_layout = (LinearLayout) findViewById(R.id.tf_layout);
                            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            String des = myDesData[m_Index];
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
                                teTag.setText(myDesData[m_Index]);
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
                            mFalseButton.setBackgroundColor(0xAAe57272);
                            //mOption_A.setBackground(getResources().getDrawable(R.drawable.text_container_false));
                            mQuestion_Number.setText("Oops!\n" + "You got it wrong");
                            mQuestion_Number.setTextColor(0xAAFFFFFF);
                            mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_false));
                            inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                            LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                            LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                            LinearLayout parent_layout = (LinearLayout) findViewById(R.id.tf_layout);

                            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            String des = myDesData[m_Index];
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
                                teTag.setText(myDesData[m_Index]);
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
//                        if (userSelection == correctAnswer) {
//                            LayoutInflater inflater = getLayoutInflater();
//                            View customToast =inflater.inflate(R.layout.bg_toast_true,null);
//                            Toast customT = new Toast(getApplicationContext());
//                            customT.setView(customToast);
//                            customT.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 400);
//                            customT.setDuration(Toast.LENGTH_SHORT);
//                            customT.show();
//                            m_Score = m_Score + 1;
//
//                        } else {
//                            LayoutInflater inflater = getLayoutInflater();
//                            View customToast =inflater.inflate(R.layout.bg_toast_false,null);
//                            Toast customT = new Toast(getApplicationContext());
//                            customT.setView(customToast);
//                            customT.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 400);
//                            customT.setDuration(Toast.LENGTH_SHORT);
//                            customT.show();
//                        }
                    }
                    private void setAnswer(String cA){
                        String tANS = "t";
                        String fANS = "f";

                        boolean Aa = tANS.equalsIgnoreCase(cA);
                        boolean Bb = fANS.equalsIgnoreCase(cA);
                        if (Aa == true){
                            mTrueButton.setBackgroundColor(0xAA81c784);
                        }else if(Bb == true){
                            mFalseButton.setBackgroundColor(0xAA81c784);
                        }
                    }
//                    @Override
//                    public void onClick(View v) {
//                        checkAnswer("false");
//                        updateQuestion();
//                    }
//
//                    private void updateQuestion() {
//                        m_Index = (m_Index + 1) % myQuestionData.length;
//                        m_Qn = m_Qn+1;
//                        if (m_Index == 0) {
//                            AlertDialog.Builder alert = new AlertDialog.Builder(new_TF.this);
//                            alert.setTitle("Quiz is done");
//                            alert.setCancelable(false);
//                            alert.setMessage("You scored " + m_Score + " points out of " + myQuestionData.length);
//                            alert.setPositiveButton("Close Quiz", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    finish();
//                                }
//                            });
//                            alert.show();
//                        }
//                        mQuestion = myQuestionData[m_Index];
//                        mQuestionTextView.setText(mQuestion);
//                        mQuestion_Number.setText("Question No: "+m_Qn);
//                        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
//                        mScoreTextView.setText("Score" + m_Score + "/" + myQuestionData.length);
//                    }
//
//                    private void checkAnswer(String userSelection) {
//                        String correctAnswer = myA_Data[m_Index];
//                        if (userSelection == correctAnswer) {
//                            LayoutInflater inflater = getLayoutInflater();
//                            View customToast =inflater.inflate(R.layout.bg_toast_true,null);
//                            Toast customT = new Toast(getApplicationContext());
//                            customT.setView(customToast);
//                            customT.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 400);
//                            customT.setDuration(Toast.LENGTH_SHORT);
//                            customT.show();
//                            m_Score = m_Score + 1;
//
//                        } else {
//                            LayoutInflater inflater = getLayoutInflater();
//                            View customToast =inflater.inflate(R.layout.bg_toast_false,null);
//                            Toast customT = new Toast(getApplicationContext());
//                            customT.setView(customToast);
//                            customT.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 400);
//                            customT.setDuration(Toast.LENGTH_SHORT);
//                            customT.show();
//
//                        }
//                    }

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
