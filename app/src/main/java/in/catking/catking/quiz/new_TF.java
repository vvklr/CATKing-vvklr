package in.catking.catking.quiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
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
        mProgressBar.setProgressBackgroundTintList(ColorStateList.valueOf(0xFFE9E6E6));//(getResources().getDrawable(R.drawable.text_container))//.setColorFilter(0xAA92D050, PorterDuff.Mode.SRC_IN);

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

                m_Qr = (myQuestionData.length -1)-m_Index;
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
                        m_Qn = m_Qn+1;
                        m_Count = (m_Count+1);
                        m_Qr = (myQuestionData.length -1)-m_Count;
                        if (m_Index == 0) {
                            inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            LinearLayout empty_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                            LinearLayout empty_layout_1 =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                            //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                            LinearLayout parent_layout = (LinearLayout) findViewById(R.id.tf_layout);
                            LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bottTF);


                            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            parent_layout.removeAllViewsInLayout();

                            TextView greet = new TextView(getApplicationContext());
                            greet.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            greet.setPadding(60,5,30,5);
                            greet.setTextSize(30);
                            Typeface face = Typeface.createFromAsset(getAssets(), "fonts/tondo_regular.ttf");
                            greet.setTypeface(face);
                            greet.setBackgroundColor(0xAAFFC000);
                            greet.setGravity(Gravity.CENTER);
                            greet.setText("Congratulations!");

                            TextView greet_2 = new TextView(getApplicationContext());
                            greet_2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            greet_2.setPadding(60,5,30,5);
                            greet_2.setTextSize(20);
                            greet_2.setTypeface(face);
                            greet_2.setBackgroundColor(0xAAFFC000);
                            greet_2.setGravity(Gravity.CENTER);
                            greet_2.setText("You completed  the quiz");


                            Button btnshare = new Button(getApplicationContext());
                            btnshare.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            btnshare.setText("Click to share your success Stories with friends");
                            btnshare.setTextSize(18);
                            btnshare.setTextColor(0xAA000000);
                            btnshare.setAllCaps(false);
                            btnshare.setBackground(null);

                            TextView teScore = new TextView(getApplicationContext());
                            teScore.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            teScore.setPadding(60,5,30,5);
                            teScore.setTextSize(20);
                            teScore.setTypeface(face);
                            teScore.setBackgroundColor(0xAAFFFFFF);
                            teScore.setText("You got "+ m_Score+" questions correct out of " + myQuestionData.length+" questions");

                            parent_layout.addView(greet);
                            parent_layout.addView(greet_2);
                            parent_layout.addView(empty_layout_1,cp);
                            parent_layout.addView(teScore);
                            LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                            parent_layout.addView(divider_layout,cp);
                            parent_layout.addView(btnshare);

                            mScoreTextView.setText("Hurray 100% progress. You are done!");
                            progress_layout.addView(empty_layout,cp);
                            Button btnfinish = new Button(getApplicationContext());
                            btnfinish.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            btnfinish.setText("Next Quiz >");
                            btnfinish.setTextColor(0xAAFFFFFF);
                            btnfinish.setBackgroundColor(0xAAA6A6A6);
                            progress_layout.addView(btnfinish);
                            btnfinish.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });
                            btnshare.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                        Intent share = new Intent(android.content.Intent.ACTION_SEND);
                                        share.setType("text/plain");

                                        share.putExtra(Intent.EXTRA_SUBJECT, "I got "+m_Score+" questions correct out of "+myQuestionData.length+
                                                " questions\n Install GK App to keep your General Knowledge up to date");
                                        share.putExtra(Intent.EXTRA_TEXT, "I got "+m_Score+" questions correct out of "+myQuestionData.length+
                                                " questions\n Install GK App to keep your General Knowledge up to date "+"https://catking.in/");

                                        startActivity(Intent.createChooser(share, "Share your result with friends using"));
                                }
                            });
//                            btnshare.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                }
//                            });
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
                        }
                        if(m_Qr==0){
                            mScoreTextView.setText("Hurray 100% progress. You are done!");
                        }else if(m_Qr<0){
                            mScoreTextView.setText("Hurray 100% progress. You are done!");
                        }else{
                            mScoreTextView.setText(m_Qr+" more question to go.");
                        }
                        mQuestion = myQuestionData[m_Index];
                        mQuestionTextView.setText(mQuestion);
                        mQuestion_Number.setText("Question No: "+m_Qn);

                        mQuestion_Number.setBackground(null);
                        mQuestion_Number.setTextColor(0xFF000000);
                        mTrueButton.setBackgroundColor(0xFFffffff);
                        mFalseButton.setBackgroundColor(0xFFffffff);

                        mTrueButton.setEnabled(true);
                        mFalseButton.setEnabled(true);

                        //mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100


                        LinearLayout ll = findViewById(R.id.tf_layout);
                        LinearLayout mm = findViewById(R.id.bottTF);
                        final LinearLayout child = (LinearLayout) mm.findViewById(R.id.button_layout);
                        mm.removeView(child);
                        final LinearLayout child2 = (LinearLayout) ll.findViewById(R.id.des_layout);
                        ll.removeView(child2);
                        final LinearLayout child3 = (LinearLayout) ll.findViewById(R.id.divider_layout);
                        ll.removeView(child3);
                        final LinearLayout child4 = (LinearLayout) mm.findViewById(R.id.emptyText_layout);
                        mm.removeView(child4);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myA_Data[m_Index];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
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
                            LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bottTF);

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
                                Typeface face = Typeface.createFromAsset(getAssets(),
                                        "fonts/tondo_regular.ttf");
                                teTag.setTypeface(face);
                                teTag.setBackground(getResources().getDrawable(R.drawable.text_container_des));
                                //teTag.setTypeface(Typeface.create("tondo_bold", Typeface.NORMAL));
                                teTag.setText(myDesData[m_Index]);
                                description_layout.addView(teTag);
                            }

                            progress_layout.addView(activity_layout,cp);
                            progress_layout.addView(emptyTextview_layout,cp);
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
                            mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
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
                            LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bottTF);

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
                                teTag.setPadding(70,5,40,5);
                                teTag.setTextSize(18);
                                Typeface face = Typeface.createFromAsset(getAssets(),
                                        "fonts/tondo_regular.ttf");
                                teTag.setTypeface(face);
                                teTag.setBackground(getResources().getDrawable(R.drawable.text_container_des));
                                //teTag.setTypeface(Typeface.create("tondo_bold", Typeface.NORMAL));
                                teTag.setText(myDesData[m_Index]);
                                description_layout.addView(teTag);
                            }


                            progress_layout.addView(activity_layout,cp);
                            progress_layout.addView(emptyTextview_layout,cp);
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
                //=======================================================================================

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
                        m_Qr = (myQuestionData.length -1)-m_Count;
                        if (m_Index == 0) {
                            inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            LinearLayout empty_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                            LinearLayout empty_layout_1 =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                            //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                            LinearLayout parent_layout = (LinearLayout) findViewById(R.id.tf_layout);
                            LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bottTF);


                            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            parent_layout.removeAllViewsInLayout();

                            TextView greet = new TextView(getApplicationContext());
                            greet.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            greet.setPadding(60,5,30,5);
                            greet.setTextSize(30);
                            Typeface face = Typeface.createFromAsset(getAssets(), "fonts/tondo_regular.ttf");
                            greet.setTypeface(face);
                            greet.setBackgroundColor(0xAAFFC000);
                            greet.setGravity(Gravity.CENTER);
                            greet.setText("Congratulations!");

                            TextView greet_2 = new TextView(getApplicationContext());
                            greet_2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            greet_2.setPadding(60,5,30,5);
                            greet_2.setTextSize(20);
                            greet_2.setTypeface(face);
                            greet_2.setBackgroundColor(0xAAFFC000);
                            greet_2.setGravity(Gravity.CENTER);
                            greet_2.setText("You completed  the quiz");


                            Button btnshare = new Button(getApplicationContext());
                            btnshare.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            btnshare.setText("Click to share your success Stories with friends");
                            btnshare.setTextSize(18);
                            btnshare.setTextColor(0xAA000000);
                            btnshare.setAllCaps(false);
                            btnshare.setBackground(null);

                            TextView teScore = new TextView(getApplicationContext());
                            teScore.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            teScore.setPadding(60,5,30,5);
                            teScore.setTextSize(20);
                            teScore.setTypeface(face);
                            teScore.setBackgroundColor(0xAAFFFFFF);
                            teScore.setText("You got "+ m_Score+" questions correct out of " + myQuestionData.length+" questions");

                            parent_layout.addView(greet);
                            parent_layout.addView(greet_2);
                            parent_layout.addView(empty_layout_1,cp);
                            parent_layout.addView(teScore);
                            LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                            parent_layout.addView(divider_layout,cp);
                            parent_layout.addView(btnshare);

                            mScoreTextView.setText("Hurray 100% progress. You are done!");
                            progress_layout.addView(empty_layout,cp);
                            Button btnfinish = new Button(getApplicationContext());
                            btnfinish.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            btnfinish.setText("Next Quiz >");
                            btnfinish.setTextColor(0xAAFFFFFF);
                            btnfinish.setBackgroundColor(0xAAA6A6A6);
                            progress_layout.addView(btnfinish);
                            btnfinish.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });
                            btnshare.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent share = new Intent(android.content.Intent.ACTION_SEND);
                                    share.setType("text/plain");

                                    share.putExtra(Intent.EXTRA_SUBJECT, "I got "+m_Score+" questions correct out of "+myQuestionData.length+
                                            " questions\n Install GK App to keep your General Knowledge up to date");
                                    share.putExtra(Intent.EXTRA_TEXT, "I got "+m_Score+" questions correct out of "+myQuestionData.length+
                                            " questions\n Install GK App to keep your General Knowledge up to date "+"https://catking.in/");

                                    startActivity(Intent.createChooser(share, "Share your result with friends using"));
                                }
                            });

//                            inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                            //LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
//                            LinearLayout empty_Textview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
//                            LinearLayout parent_layout = (LinearLayout) findViewById(R.id.tf_layout);
//                            LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bottTF);
//
//                            LinearLayout ll = (LinearLayout)findViewById(R.id.tf_layout);
//                            ll.removeAllViewsInLayout();
//
//                            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

//                            //parent_layout.addView(description_layout,cp);
//                            TextView teTag = new TextView(getApplicationContext());
//                            teTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                            teTag.setPadding(60,5,30,5);
//                            teTag.setTextSize(20);
//                            Typeface face = Typeface.createFromAsset(getAssets(),
//                                    "fonts/tondo_regular.ttf");
//                            teTag.setTypeface(face);
//                            teTag.setBackgroundColor(0xAAFFC000);
//                            teTag.setGravity(Gravity.CENTER);
//                            //teTag.setTypeface(Typeface.create("tondo_bold", Typeface.NORMAL));
//                            teTag.setText("Congratulations!\n" + "You completed  the quiz");
////                            description_layout.addView(teTag);
////                            parent_layout.addView(description_layout,cp);
//                            Button btnshare = new Button(getApplicationContext());
//                            btnshare.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                            btnshare.setText("Click to share your success Stories with friends");
//                            btnshare.setTextSize(18);
//                            btnshare.setTextColor(0xAAFFFFFF);
//                            btnshare.setAllCaps(false);
//                            btnshare.setBackgroundColor(0xAAA6A6A6);
//
//                            TextView teScore = new TextView(getApplicationContext());
//                            teScore.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                            teScore.setPadding(60,5,30,5);
//                            teScore.setTextSize(20);
//                            Typeface re_face = Typeface.createFromAsset(getAssets(),
//                                    "fonts/tondo_regular.ttf");
//                            teScore.setTypeface(re_face);
//                            teScore.setBackgroundColor(0xAAFFFFFF);
//                            //teTag.setTypeface(Typeface.create("tondo_bold", Typeface.NORMAL));
//                            teScore.setText("You got "+ m_Score+" questions correct out of " + myQuestionData.length+" questions");
//
////                            description_layout.addView(teTag);
////                            parent_layout.addView(description_layout,cp);
//                            parent_layout.addView(teTag);
//                            parent_layout.addView(teScore);
//                            LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
//                            parent_layout.addView(divider_layout,cp);
//                            parent_layout.addView(btnshare);
//                            parent_layout.addView(divider_layout,cp);
//                            mScoreTextView.setText("Hurray 100% progress. You are done!");

                            //progress_layout.addView(activity_layout,cp);
//                            progress_layout.addView(empty_Textview_layout,cp);
//                            Button btnfinish = new Button(getApplicationContext());
//                            btnfinish.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                            btnfinish.setText("Next Quiz >");
//                            btnfinish.setTextColor(0xAAFFFFFF);
//                            btnfinish.setBackgroundColor(0xAAA6A6A6);
//                            progress_layout.addView(btnfinish);
//                            btnfinish.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    finish();
//                                }
//                            });
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
                        }
                        if(m_Qr==0){
                            mScoreTextView.setText("Hurray 100% progress. You are done!");
                        }else if(m_Qr<0){
                            mScoreTextView.setText("Hurray 100% progress. You are done!");
                        }else {
                            mScoreTextView.setText(m_Qr+" more question to go.");
                        }
                        mQuestion = myQuestionData[m_Index];
                        mQuestionTextView.setText(mQuestion);
                        mQuestion_Number.setText("Question No: "+m_Qn);

                        mQuestion_Number.setBackground(null);
                        mQuestion_Number.setTextColor(0xFF000000);
                        mTrueButton.setBackgroundColor(0xFFffffff);
                        mFalseButton.setBackgroundColor(0xFFffffff);

                        mTrueButton.setEnabled(true);
                        mFalseButton.setEnabled(true);

                        //mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
                        //mScoreTextView.setText("Score" + m_Score + "/" + myQuestionData.length);

                        LinearLayout ll = findViewById(R.id.tf_layout);
                        LinearLayout mm = findViewById(R.id.bottTF);
                        final LinearLayout child = (LinearLayout) mm.findViewById(R.id.button_layout);
                        mm.removeView(child);
                        final LinearLayout child2 = (LinearLayout) ll.findViewById(R.id.des_layout);
                        ll.removeView(child2);
                        final LinearLayout child3 = (LinearLayout) ll.findViewById(R.id.divider_layout);
                        ll.removeView(child3);
                        final LinearLayout child4 = (LinearLayout) mm.findViewById(R.id.emptyText_layout);
                        mm.removeView(child4);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myA_Data[m_Index];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
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
                            LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bottTF);

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
                                Typeface face = Typeface.createFromAsset(getAssets(),
                                        "fonts/tondo_regular.ttf");
                                teTag.setTypeface(face);
                                teTag.setBackground(getResources().getDrawable(R.drawable.text_container_des));
                                //teTag.setTypeface(Typeface.create("tondo_bold", Typeface.NORMAL));
                                teTag.setText(myDesData[m_Index]);
                                description_layout.addView(teTag);
                            }

                            progress_layout.addView(activity_layout,cp);
                            progress_layout.addView(emptyTextview_layout,cp);
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
                            mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
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
                            LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bottTF);

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
                                teTag.setPadding(70,5,40,5);
                                teTag.setTextSize(18);
                                Typeface face = Typeface.createFromAsset(getAssets(),
                                        "fonts/tondo_regular.ttf");
                                teTag.setTypeface(face);
                                teTag.setBackground(getResources().getDrawable(R.drawable.text_container_des));
                                //teTag.setTypeface(Typeface.create("tondo_bold", Typeface.NORMAL));
                                teTag.setText(myDesData[m_Index]);
                                description_layout.addView(teTag);
                            }


                            progress_layout.addView(activity_layout,cp);
                            progress_layout.addView(emptyTextview_layout,cp);
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
