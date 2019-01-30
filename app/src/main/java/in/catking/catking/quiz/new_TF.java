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
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import in.catking.catking.pdf_SheetData;
import in.catking.catking.test3;
import in.catking.catking.test4;
import in.catking.catking.tf_QuestionData;
import in.catking.catking.utils.GifImageView;
import in.catking.catking.view_ArtAndCulture;
import in.catking.catking.view_BooksAndAuthor;
import in.catking.catking.view_ConstitutionOfIndia;
import in.catking.catking.view_DynamicGK;
import in.catking.catking.view_Economics;
import in.catking.catking.view_FunFacts;
import in.catking.catking.view_Geography;
import in.catking.catking.view_History;
import in.catking.catking.view_Miscellaneous;
import in.catking.catking.view_Olympics;
import in.catking.catking.view_Politics;
import in.catking.catking.view_Science;
import in.catking.catking.view_Sport_Achievement;

public class new_TF extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // TODO: Declare member variables here:
    private String Science_1;
    private String Geography_1;
    private String Books_and_Authors_1;
    private String Olympics_1;
    private String Sports_and_Achievements_1;
    private String Art_and_Culture_1;
    private String History_1;
    private String Politics_1;
    private String Constitution_of_India_1;
    private String Miscellaneous_1;
    private String Funfacts_1;
    private String DynamicGK_1;
    private String Economics_1;
    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestion_Number;
    TextView mQuestionTextView;
    TextView mScoreTextView;
    ProgressBar mProgressBar;
    int m_Index; //will increase after answer selection
    String mQuestion;
    String URL = "";
    int m_Score;  //will increase with correct answer
    int m_Qn;
    int m_Qr;
    int m_Count;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_quiz_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        GifImageView gifImageView = (GifImageView) findViewById(R.id.Gif_View);
        gifImageView.setGifImageResource(R.drawable.smartphone_drib);

        Intent intent = getIntent();
        URL = intent.getStringExtra("url");
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestHandle requestHandle = client.get(URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("CATKing", "Successful JSON data collection " + response.toString());
                final String[] myQuestionData = tf_QuestionData.fromJsonQ(response);
                final String[] myA_Data = tf_QuestionData.fromJsonA(response);
                final String[] myDesData = tf_QuestionData.fromJsonDes(response);

                final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / myQuestionData.length);
                LinearLayout dd = findViewById(R.id.loaded_quiz);
                dd.removeAllViews();

                // TODO: Declare constants here
                inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout.LayoutParams de = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                LinearLayout activity_stQ = (LinearLayout) inflater.inflate(R.layout.activity_tf_quizcard_layout, null);
                dd.addView(activity_stQ,de);
                mTrueButton = (Button) findViewById(R.id.button_option_true);
                mFalseButton = (Button) findViewById(R.id.button_option_false);
                mQuestion_Number = findViewById(R.id.tf_qn_view_card);
                mQuestionTextView = findViewById(R.id.tf_text_view_card);
                mScoreTextView = findViewById(R.id.score_tf_card);

                mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_tf_card);
                mProgressBar.setProgressTintList(ColorStateList.valueOf(0xAA92D050));
                mProgressBar.setProgressBackgroundTintList(ColorStateList.valueOf(0xFFE9E6E6));//(getResources().getDrawable(R.drawable.text_container))//.setColorFilter(0xAA92D050, PorterDuff.Mode.SRC_IN);

                mQuestion = myQuestionData[m_Index];
                mQuestionTextView.setText(mQuestion);
                mQuestion_Number.setText("Question No: "+m_Qn);

                m_Qr = (myQuestionData.length)-m_Index;
                mScoreTextView.setText(myQuestionData.length+" question to go.");
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
                            greet.setPadding(60,40,30,0);
                            greet.setTextSize(30);
                            Typeface face = Typeface.createFromAsset(getAssets(), "fonts/tondo_regular.ttf");
                            greet.setTypeface(face);
                            greet.setBackgroundColor(0xAAFFC000);
                            greet.setGravity(Gravity.CENTER);
                            greet.setText("Congratulations!");

                            TextView greet_2 = new TextView(getApplicationContext());
                            greet_2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            greet_2.setPadding(60,0,30,40);
                            greet_2.setTextSize(20);
                            greet_2.setTypeface(face);
                            greet_2.setBackgroundColor(0xAAFFC000);
                            greet_2.setGravity(Gravity.CENTER);
                            greet_2.setText("You completed the quiz");


                            Button btnshare = new Button(getApplicationContext());
                            btnshare.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            btnshare.setText("Click to share your success Stories with friends");
                            btnshare.setTextSize(18);
                            btnshare.setPadding(20,30,20,30);
                            btnshare.setTextColor(0xFF000000);
                            btnshare.setBackgroundColor(0xFFdedede);
                            btnshare.setAllCaps(false);

                            TextView teScore = new TextView(getApplicationContext());
                            teScore.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            teScore.setPadding(60,60,30,60);
                            teScore.setTextSize(20);
                            teScore.setTypeface(face);
                            teScore.setBackgroundColor(0xAAFFFFFF);
                            teScore.setText("You got "+ m_Score+" questions correct out of " + myQuestionData.length+" questions");

                            parent_layout.addView(greet);
                            parent_layout.addView(greet_2);
                            //parent_layout.addView(empty_layout_1,cp);
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
                        }
                        mQuestion = myQuestionData[m_Index];
                        mQuestionTextView.setText(mQuestion);
                        mQuestion_Number.setText("Question No: "+m_Qn);

                        mQuestion_Number.setBackground(null);
                        mQuestion_Number.setTextColor(0xFF000000);
                        mTrueButton.setBackgroundColor(0xFFf2f2f2);
                        mFalseButton.setBackgroundColor(0xFFf2f2f2);

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
//                        final LinearLayout child5 = (LinearLayout) mm.findViewById(R.id.bottTF_bar);
//                        mm.removeView(child5);
                    }

                    private void checkAnswer(String userSelection) {
                        String correctAnswer = myA_Data[m_Index];
                        boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                        if(Aa== true){
                            m_Count = (m_Count+1);
                            m_Qr = (myQuestionData.length)-m_Count;
                            if(m_Qr==0){
                                mScoreTextView.setText("Hurray 100% progress. You are done!");
                            }else if(m_Qr<0){
                                mScoreTextView.setText("Hurray 100% progress. You are done!");
                            }else{
                                mScoreTextView.setText(m_Qr+" more question to go.");
                            }
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
                                teTag.setMaxLines(4);
                                teTag.setVerticalScrollBarEnabled(true);
                                teTag.setMovementMethod(new ScrollingMovementMethod());
                                parent_layout.setOnTouchListener(new View.OnTouchListener() {

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        findViewById(R.id.p_layout).getParent()
                                                .requestDisallowInterceptTouchEvent(false);
                                        return false;
                                    }
                                });

                                teTag.setOnTouchListener(new View.OnTouchListener() {

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        // Disallow the touch request for parent scroll on touch of  child view
                                        v.getParent().requestDisallowInterceptTouchEvent(true);
                                        return false;
                                    }
                                });
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

                            if(m_Qr==0){
                                btnTag.setText("Finish Quiz >");
                            }else if(m_Qr<0){
                                btnTag.setText("Finish Quiz >");
                            }else{
                                btnTag.setText("Next Question >");
                            }

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
                            m_Count = (m_Count+1);
                            m_Qr = (myQuestionData.length)-m_Count;
                            if(m_Qr==0){
                                mScoreTextView.setText("Hurray 100% progress. You are done!");
                            }else if(m_Qr<0){
                                mScoreTextView.setText("Hurray 100% progress. You are done!");
                            }else{
                                mScoreTextView.setText(m_Qr+" more question to go.");
                            }
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
                                teTag.setMaxLines(4);
                                teTag.setVerticalScrollBarEnabled(true);
                                teTag.setMovementMethod(new ScrollingMovementMethod());
                                parent_layout.setOnTouchListener(new View.OnTouchListener() {

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        findViewById(R.id.p_layout).getParent()
                                                .requestDisallowInterceptTouchEvent(false);
                                        return false;
                                    }
                                });

                                teTag.setOnTouchListener(new View.OnTouchListener() {

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        // Disallow the touch request for parent scroll on touch of  child view
                                        v.getParent().requestDisallowInterceptTouchEvent(true);
                                        return false;
                                    }
                                });
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

                            if(m_Qr==0){
                                btnTag.setText("Finish Quiz >");
                            }else if(m_Qr<0){
                                btnTag.setText("Finish Quiz >");
                            }else{
                                btnTag.setText("Next Question >");
                            }

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
                            greet.setPadding(60,40,30,0);
                            greet.setTextSize(30);
                            Typeface face = Typeface.createFromAsset(getAssets(), "fonts/tondo_regular.ttf");
                            greet.setTypeface(face);
                            greet.setBackgroundColor(0xAAFFC000);
                            greet.setGravity(Gravity.CENTER);
                            greet.setText("Congratulations!");

                            TextView greet_2 = new TextView(getApplicationContext());
                            greet_2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            greet_2.setPadding(60,0,30,40);
                            greet_2.setTextSize(20);
                            greet_2.setTypeface(face);
                            greet_2.setBackgroundColor(0xAAFFC000);
                            greet_2.setGravity(Gravity.CENTER);
                            greet_2.setText("You completed the quiz");


                            Button btnshare = new Button(getApplicationContext());
                            btnshare.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            btnshare.setText("Click to share your success Stories with friends");
                            btnshare.setTextSize(18);
                            btnshare.setTextColor(0xAA000000);
                            btnshare.setAllCaps(false);
                            btnshare.setBackground(null);

                            TextView teScore = new TextView(getApplicationContext());
                            teScore.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            teScore.setPadding(60,60,30,60);
                            teScore.setTextSize(20);
                            teScore.setTypeface(face);
                            teScore.setBackgroundColor(0xAAFFFFFF);
                            teScore.setText("You got "+ m_Score+" questions correct out of " + myQuestionData.length+" questions");

                            parent_layout.addView(greet);
                            parent_layout.addView(greet_2);
                            //parent_layout.addView(empty_layout_1,cp);
                            parent_layout.addView(teScore);
                            LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                            parent_layout.addView(divider_layout,cp);
                            parent_layout.addView(btnshare);
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
                        }
                        mQuestion = myQuestionData[m_Index];
                        mQuestionTextView.setText(mQuestion);
                        mQuestion_Number.setText("Question No: "+m_Qn);

                        mQuestion_Number.setBackground(null);
                        mQuestion_Number.setTextColor(0xFF000000);
                        mTrueButton.setBackgroundColor(0xFFf2f2f2);
                        mFalseButton.setBackgroundColor(0xFFf2f2f2);

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
                            m_Count = (m_Count+1);
                            m_Qr = (myQuestionData.length)-m_Count;
                            if(m_Qr==0){
                                mScoreTextView.setText("Hurray 100% progress. You are done!");
                            }else if(m_Qr<0){
                                mScoreTextView.setText("Hurray 100% progress. You are done!");
                            }else {
                                mScoreTextView.setText(m_Qr+" more question to go.");
                            }
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
                                teTag.setMaxLines(4);
                                teTag.setVerticalScrollBarEnabled(true);
                                teTag.setMovementMethod(new ScrollingMovementMethod());
                                parent_layout.setOnTouchListener(new View.OnTouchListener() {

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        findViewById(R.id.p_layout).getParent()
                                                .requestDisallowInterceptTouchEvent(false);
                                        return false;
                                    }
                                });

                                teTag.setOnTouchListener(new View.OnTouchListener() {

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        // Disallow the touch request for parent scroll on touch of  child view
                                        v.getParent().requestDisallowInterceptTouchEvent(true);
                                        return false;
                                    }
                                });
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

                            if(m_Qr==0){
                                btnTag.setText("Finish Quiz >");
                            }else if(m_Qr<0){
                                btnTag.setText("Finish Quiz >");
                            }else{
                                btnTag.setText("Next Question >");
                            }

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
                            m_Count = (m_Count+1);
                            m_Qr = (myQuestionData.length)-m_Count;
                            if(m_Qr==0){
                                mScoreTextView.setText("Hurray 100% progress. You are done!");
                            }else if(m_Qr<0){
                                mScoreTextView.setText("Hurray 100% progress. You are done!");
                            }else {
                                mScoreTextView.setText(m_Qr+" more question to go.");
                            }
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
                                teTag.setMaxLines(4);
                                teTag.setVerticalScrollBarEnabled(true);
                                teTag.setMovementMethod(new ScrollingMovementMethod());
                                parent_layout.setOnTouchListener(new View.OnTouchListener() {

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        findViewById(R.id.p_layout).getParent()
                                                .requestDisallowInterceptTouchEvent(false);
                                        return false;
                                    }
                                });

                                teTag.setOnTouchListener(new View.OnTouchListener() {

                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        // Disallow the touch request for parent scroll on touch of  child view
                                        v.getParent().requestDisallowInterceptTouchEvent(true);
                                        return false;
                                    }
                                });
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

                            if(m_Qr==0){
                                btnTag.setText("Finish Quiz >");
                            }else if(m_Qr<0){
                                btnTag.setText("Finish Quiz >");
                            }else{
                                btnTag.setText("Next Question >");
                            }

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

        String url = "https://script.google.com/macros/s/AKfycbwfvXAADSw7PCH36Rjiut9cqOOzOCjGXp2qg0S8jTMMa7eAaGU/exec?MQK1hyOY2ysqc29O-nnehdEwhP7cC3CUJ";
        AsyncHttpClient client1 = new AsyncHttpClient();
        final RequestHandle requestHandle1 = client1.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("CATKing", "Successful JSON data collection " + response.toString());
                final String[] mySheetID_Data = pdf_SheetData.fromJson_pdfID(response);
                Science_1 = mySheetID_Data[0];
                Geography_1 = mySheetID_Data[1];
                Books_and_Authors_1 = mySheetID_Data[2];
                Olympics_1 = mySheetID_Data[3];
                Sports_and_Achievements_1 = mySheetID_Data[4];
                Art_and_Culture_1 = mySheetID_Data[5];
                History_1 = mySheetID_Data[6];
                Politics_1 = mySheetID_Data[7];
                Constitution_of_India_1 = mySheetID_Data[8];
                Miscellaneous_1 = mySheetID_Data[9];
                Funfacts_1 = mySheetID_Data[10];
                DynamicGK_1 = mySheetID_Data[11];
                Economics_1 = mySheetID_Data[12];
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject responce) {
                Log.e("CATKing", "Fail JSON" + e.toString());
                Log.d("CATKING", " Fail Status Code" + statusCode);
            }

        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_books_author) {
            Intent intent = new Intent(this, view_BooksAndAuthor.class);
            //intent.putExtra("BooksAndAuthor","https://docs.google.com/document/d/1GVmTKAFss3FJYLjpq_cKOhiywxqeHDKOE3eyO3kWMjs/");
            intent.putExtra("booksandauthor","BooksAndAuthor");
            Log.d("CAT_PDF BandA",Books_and_Authors_1);
            //https://docs.google.com/document/d/1GVmTKAFss3FJYLjpq_cKOhiywxqeHDKOE3eyO3kWMjs/
            intent.putExtra("BooksAndAuthor",Books_and_Authors_1);
            this.startActivity(intent);
            // books and author
        } else if (id == R.id.menu_art_culture) {
            Log.d("CAT_PDF AandC",Art_and_Culture_1);
            Intent intent = new Intent(this, view_ArtAndCulture.class);
            intent.putExtra("artandculture","ArtAndCulture");
            intent.putExtra("ArtAndCulture",Art_and_Culture_1);
            this.startActivity(intent);
            // art and culture
        } else if (id == R.id.menu_constitution_of_india) {
            Log.d("CAT_PDF ConstiofI",Constitution_of_India_1);
            Intent intent = new Intent(this, view_ConstitutionOfIndia.class);
            intent.putExtra("constitutionofindia","ConstitutionOfIndia");
            intent.putExtra("ConstitutionOfIndia",Constitution_of_India_1);
            this.startActivity(intent);
            // constitution of india
        } else if (id == R.id.menu_dynamic_gk) {
            Log.d("CAT_PDF Dgk",DynamicGK_1);
            Intent intent = new Intent(this, view_DynamicGK.class);
            intent.putExtra("dynamicgk","DynamicGk");
            intent.putExtra("DynamicGK",DynamicGK_1);
            this.startActivity(intent);
            //dynamic gk
        } else if (id == R.id.menu_olympics) {
            Log.d("CAT_PDF olym",Olympics_1);
            Intent intent = new Intent(this, view_Olympics.class);
            intent.putExtra("olympics","Olympics");
            intent.putExtra("Olympics",Olympics_1);
            this.startActivity(intent);
            //olympics
        }else if (id == R.id.menu_economics) {
            Log.d("CAT_PDF eco",Economics_1);
            Intent intent = new Intent(this, view_Economics.class);
            intent.putExtra("economics","Economics");
            intent.putExtra("Economics",Economics_1);
            this.startActivity(intent);
            //economics
        }else if (id == R.id.menu_science) {
            Log.d("CAT_PDF science",Science_1);
            Intent intent = new Intent(this, view_Science.class);
            intent.putExtra("science","Science");
            //intent.putExtra("Science","1Oal6x5C7qrEU1mB7yiBgDCFbKYVdAwIE");
            intent.putExtra("Science",Science_1);
            this.startActivity(intent);
            //science
        }else if (id == R.id.menu_miscellaneous) {
            Log.d("CAT_PDF misclle",Miscellaneous_1);
            Intent intent = new Intent(this, view_Miscellaneous.class);
            intent.putExtra("miscellaneous","Miscellaneous");
            intent.putExtra("Miscellaneous",Miscellaneous_1);
            this.startActivity(intent);
            //miscellaneous
        }else if (id == R.id.menu_fun_facts) {
            Log.d("CAT_PDF FF",Funfacts_1);
            Intent intent = new Intent(this, view_FunFacts.class);
            intent.putExtra("funfacts","FunFacts");
            intent.putExtra("FunFacts",Funfacts_1);
            this.startActivity(intent);
            //fun facts
        }else if (id == R.id.menu_geography) {
            Log.d("CAT_PDF geo",Geography_1);
            Intent intent = new Intent(this, view_Geography.class);
            intent.putExtra("geography","Geography");
            intent.putExtra("Geography",Geography_1);
            this.startActivity(intent);
            //geography
        }else if (id == R.id.menu_politics) {
            Log.d("CAT_PDF poli",Politics_1);
            Intent intent = new Intent(this, view_Politics.class);
            intent.putExtra("politics","Politics");
            intent.putExtra("Politics",Politics_1);
            this.startActivity(intent);
            //politics
        }else if (id == R.id.menu_history) {
            Log.d("CAT_PDF History",History_1);
            Intent intent = new Intent(this, view_History.class);
            intent.putExtra("history","History");
            intent.putExtra("History",History_1);//1cdiOhKalAJgP2jVixBWJwnuGKdDH4m5v
            this.startActivity(intent);
            //history
        }else if (id == R.id.menu_quiz_tf) {

            Intent intent = new Intent(this, test4.class);
            this.startActivity(intent);
            //true false quiz
        }else if (id == R.id.menu_quiz_mcq) {
            Intent intent = new Intent(this, test3.class);
            this.startActivity(intent);
            //multiple choice question quiz
        } else if (id == R.id.menu_sport_achievement) {
            Log.d("CAT_PDF SaA",Sports_and_Achievements_1);
            Intent intent = new Intent(this, view_Sport_Achievement.class);
            intent.putExtra("sports", "Sports");
            intent.putExtra("Sports",Sports_and_Achievements_1);//1cdiOhKalAJgP2jVixBWJwnuGKdDH4m5v
            this.startActivity(intent);
            //sports and achievements 1wNJXKzxqi9k0US1L7qEeZk7DtENitswy
        } else if (id == R.id.nav_share) {
            Intent SLink = new Intent(Intent.ACTION_SEND);
            SLink.setType("text/plain");
            SLink.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL"); // Email 's Subject
            SLink.putExtra(Intent.EXTRA_TEXT, "https://www.catking.in/appDownload");  //Email 's Greeting text
            startActivity(Intent.createChooser(SLink, "CATKing App link"));
        } else if (id == R.id.nav_send) {
            Intent Email = new Intent(Intent.ACTION_SEND);
            Email.setType("text/email");
            Email.putExtra(Intent.EXTRA_EMAIL,
                    new String[]{"vronpc@gmail.com"});  //developer 's email
            Email.putExtra(Intent.EXTRA_SUBJECT,
                    "My Suggestions to Admin"); // Email 's Subject
            Email.putExtra(Intent.EXTRA_TEXT, "Dear CATKing," + "");  //Email 's Greeting text
            startActivity(Intent.createChooser(Email, "Send Feedback:"));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ScoreKey", m_Score);
        outState.putInt("IndexKey",m_Index);
        outState.putInt("Question_Number",m_Qn);
    }
}
