package in.catking.gkapp.quiz;


// please do not change variables which are already there
//editing this file may result in failed quiz section


//Vishal Raut
//Email me on vr.iitb@gmail.com if you come across any problem
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import in.catking.gkapp.ExpandableListAdapter;
import in.catking.gkapp.MainActivity;
import in.catking.gkapp.MenuModel;
import in.catking.gkapp.R;
import in.catking.gkapp.activity_coming_soon;
import in.catking.gkapp.buy_gk_course;
import in.catking.gkapp.mcq_QuestionData;
import in.catking.gkapp.menuItems.cmat_sa;
import in.catking.gkapp.menuItems.ibps_clerk_sa;
import in.catking.gkapp.menuItems.ibps_po_sa;
import in.catking.gkapp.menuItems.iift_sa;
import in.catking.gkapp.menuItems.mat_sa;
import in.catking.gkapp.menuItems.miCat_sa;
import in.catking.gkapp.menuItems.rbi_gbo_sa;
import in.catking.gkapp.menuItems.rbi_oa_sa;
import in.catking.gkapp.menuItems.rrb_oa_sa;
import in.catking.gkapp.menuItems.rrb_os_sa;
import in.catking.gkapp.menuItems.sbi_clerk_sa;
import in.catking.gkapp.menuItems.sbi_po_sa;
import in.catking.gkapp.menuItems.snap_sa;
import in.catking.gkapp.menuItems.staticGK_sa;
import in.catking.gkapp.menuItems.xat_sa;
import in.catking.gkapp.utils.GifImageView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class newMCQ extends AppCompatActivity{

    // TODO: Declare member variables here:
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    Button mOption_A;
    Button mOption_B;
    Button mOption_C;
    Button mOption_D;
    Button mContinue;
    Button mStartFresh;
    TextView mQuestionText_View;
    TextView mQuestion_Number;
    TextView mScoreText_View;
    ProgressBar mProgress_Bar;
    int m_Index; //will increase after answer selection
    String m_Question;
    String URL = "";
    String uniqueID;
    int m_Qn;
    int m_Qr;
    int m_Count;
    int m_Score;  //will increase with correct answer
    private static int SPLASH_TIME = 5000;
    LayoutInflater inflater;
    CardView cardMCQ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_quiz_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();


        Intent intent = getIntent();
        URL = intent.getStringExtra("url");
        uniqueID = intent.getStringExtra("UID");
        GifImageView gifImageView = (GifImageView) findViewById(R.id.Gif_View);
        gifImageView.setGifImageResource(R.drawable.smartphone_drib);


        AsyncHttpClient client1 = new AsyncHttpClient();
        final RequestHandle requestHandle1 = client1.get(URL, new JsonHttpResponseHandler() {
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

                final SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                m_Index  = sp.getInt(uniqueID+"_MINDEX", 0);
                Log.d("TFC_resq","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);


                final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / myQuestion_Data.length);
                LinearLayout dd = findViewById(R.id.loaded_quiz);
                dd.removeAllViews();

                inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout.LayoutParams de = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                if(m_Index == 0){
                    m_Index  = 0;
                    m_Score = 0;
                    m_Qn = 1;
                    m_Qr = 0;
                    m_Count = 0;
                    LinearLayout activity_stQ = (LinearLayout) inflater.inflate(R.layout.activity_quizcard_layout, null);
                    dd.addView(activity_stQ,de);

                    cardMCQ = (CardView)findViewById(R.id.cardview_mcq);
                    mOption_A = (Button) findViewById(R.id.button_option_A);
                    mOption_B = (Button) findViewById(R.id.button_option_B);
                    mOption_C = (Button) findViewById(R.id.button_option_C);
                    mOption_D = (Button) findViewById(R.id.button_option_D);
                    mQuestion_Number = findViewById(R.id.mcq_qn_view_card);
                    mQuestionText_View = findViewById(R.id.mcq_text_view_card);
                    mScoreText_View = findViewById(R.id.score_mcq_card);
                    mProgress_Bar = (ProgressBar) findViewById(R.id.progress_bar_mcq_card);
                    mProgress_Bar.setProgressTintList(ColorStateList.valueOf(0xAA92D050));
                    mProgress_Bar.setProgressBackgroundTintList(ColorStateList.valueOf(0xFFE9E6E6));
                    mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT*(m_Index));

                    m_Question = myQuestion_Data[m_Index];
                    mQuestionText_View.setText(m_Question);
                    mQuestion_Number.setText("Question No: "+m_Qn);
                    mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                    mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                    mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                    mOption_D.setText("D. "+myOptina_D_data[m_Index]);

                    if(m_Qr ==0 ){
                        m_Qr = (myQuestion_Data.length)-m_Index;
                        Log.d("MCQ_qr", String.valueOf(m_Qr));
                    }

                    mScoreText_View.setText(m_Qr+" question to go.");
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
                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_Index+" mScore:"+m_Score+" mQn:"+m_Qn+" mQr:"+m_Qr+" mCount:"+m_Count);
                        }
                        private void updateQuestion() {
                            Log.d("TFC_funUQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                            if(m_Index==0){
                                inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                LinearLayout empty_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                LinearLayout empty_layout_1 =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                LinearLayout parent_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bott);


                                LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                parent_layout.removeAllViewsInLayout();

                                TextView greet = new TextView(getApplicationContext());
                                greet.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                greet.setPadding(60,40,35,0);
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
                                teScore.setText("You got "+ m_Score+" questions correct out of " + myQuestion_Data.length+" questions");

                                parent_layout.addView(greet);
                                parent_layout.addView(greet_2);
                                parent_layout.addView(teScore);
                                parent_layout.addView(btnshare);


                                Button btnfinish = new Button(getApplicationContext());
                                btnfinish.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                btnfinish.setText("Next Quiz >");
                                btnfinish.setTextColor(0xAAFFFFFF);
                                btnfinish.setBackgroundColor(0xAAA6A6A6);
                                progress_layout.addView(btnfinish);
                                btnfinish.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.clear();
                                        editor.commit();
                                        finish();
                                    }
                                });
                                btnshare.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent share = new Intent(android.content.Intent.ACTION_SEND);
                                        share.setType("text/plain");

                                        share.putExtra(Intent.EXTRA_SUBJECT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                " questions\n Install GK App to keep your General Knowledge up to date");
                                        share.putExtra(Intent.EXTRA_TEXT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                " questions\n Install GK App to keep your General Knowledge up to date "+"https://catking.in/gk-app");

                                        startActivity(Intent.createChooser(share, "Share your result with friends using"));
                                    }
                                });
                            }
                            m_Question = myQuestion_Data[m_Index];
                            mQuestionText_View.setText(m_Question);
                            mQuestion_Number.setText("Question No: "+m_Qn);
                            mQuestion_Number.setTextColor(0xAA000000);
                            mQuestion_Number.setBackground(null);
                            mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                            mOption_A.setBackgroundColor(0xFFf2f2f2);
                            mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                            mOption_B.setBackgroundColor(0xFFf2f2f2);
                            mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                            mOption_C.setBackgroundColor(0xFFf2f2f2);
                            mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                            mOption_D.setBackgroundColor(0xFFf2f2f2);
                            mOption_A.setEnabled(true);
                            mOption_B.setEnabled(true);
                            mOption_C.setEnabled(true);
                            mOption_D.setEnabled(true);

                            LinearLayout ll = findViewById(R.id.p_layout);
                            LinearLayout mm = findViewById(R.id.quiz_layout);
                            final LinearLayout child = (LinearLayout) ll.findViewById(R.id.button_layout);
                            ll.removeView(child);
                            final LinearLayout child2 = (LinearLayout) mm.findViewById(R.id.des_layout);
                            mm.removeView(child2);
                            final LinearLayout child3 = (LinearLayout) mm.findViewById(R.id.divider_layout);
                            mm.removeView(child3);
                            final LinearLayout child4 = (LinearLayout) ll.findViewById(R.id.emptyText_layout);
                            ll.removeView(child4);
                        }

                        private void checkAnswer(String userSelection) {
                            String correctAnswer = myA_Data[m_Index];
                            boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                            if(Aa== true){
                                m_Score = m_Score+1;
                                m_Count = (m_Count+1);
                                m_Qr = (myQuestion_Data.length)-m_Count;

                                if(m_Qr==0){
                                    mScoreText_View.setText("Hurray 100% progress. You are done!");
                                }else if(m_Qr<0){
                                    mScoreText_View.setText("Hurray 100% progress. You are done!");
                                }else{
                                    mScoreText_View.setText(m_Qr+" more question to go.");
                                }

                                mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
                                mOption_A.setBackgroundColor(0xAA81c784);
                                //mOption_A.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                                mQuestion_Number.setText("Hurray!\n" + "You got it right");
                                mQuestion_Number.setTextColor(0xAA385723);
                                mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));

                                inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                                LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                                LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                                LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                                LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                String des = myDescription_data[m_Index];
                                String check ="";
                                boolean de = check.equalsIgnoreCase(des);
                                if(de == false){
                                    //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                    //quiz_layout.addView(divider_layout,cp);
                                    quiz_layout.addView(description_layout,cp);
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
                                    teTag.setText(myDescription_data[m_Index]);
                                    description_layout.addView(teTag);
                                }

                                parent_layout.addView(activity_layout,cp);
                                parent_layout.addView(emptyTextview_layout,cp);
                                Button btnTag = new Button(getApplicationContext());
                                btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                if(m_Qr==0){
                                    btnTag.setText("Finish Quiz >");
                                    SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                    int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                    int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                    int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                    int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                    int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                    Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                }else if(m_Qr<0){
                                    btnTag.setText("Finish Quiz >");
                                    SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                    int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                    int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                    int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                    int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                    int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                    Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                }else{
                                    btnTag.setText("Next Question >");
                                }

                                SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                m_Index = (m_Index + 1) % myQuestion_Data.length;
                                m_Qn = m_Qn+1;
                                editor.putInt(uniqueID+"_MINDEX", m_Index);
                                editor.putInt(uniqueID+"_MQN", m_Qn);
                                editor.putInt(uniqueID+"_MQR", m_Qr);
                                editor.putInt(uniqueID+"_MSCORE", m_Score);
                                editor.commit();

                                btnTag.setTextColor(0xAAFFFFFF);
                                btnTag.setBackgroundColor(0xAAA6A6A6);
                                activity_layout.addView(btnTag);
                                btnTag.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        updateQuestion();
                                        Log.d("MCQC_updateq","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                    }
                                });
                            }else{
                                m_Count = (m_Count+1);
                                Log.d("MCQC_mcount", String.valueOf(m_Count));
                                m_Qr = myQuestion_Data.length-m_Count;
                                Log.d("MCQC_mqr", String.valueOf(m_Qr));

                                if(m_Qr==0){
                                    mScoreText_View.setText("Hurray 100% progress. You are done!");
                                }else if(m_Qr<0){
                                    mScoreText_View.setText("Hurray 100% progress. You are done!");
                                }else{
                                    mScoreText_View.setText(m_Qr+" more question to go.");
                                }
                                mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
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
                                LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);

                                LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                String des = myDescription_data[m_Index];
                                String check ="";
                                boolean de = check.equalsIgnoreCase(des);
                                if(de == false){
                                    //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                    //quiz_layout.addView(divider_layout,cp);
                                    quiz_layout.addView(description_layout,cp);
                                    TextView teTag = new TextView(getApplicationContext());
                                    teTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    teTag.setPadding(70,5,40,5);
                                    teTag.setTextSize(18);
                                    teTag.setVerticalScrollBarEnabled(true);
                                    teTag.setMaxLines(4);
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
                                    teTag.setText(myDescription_data[m_Index]);
                                    description_layout.addView(teTag);
                                }


                                parent_layout.addView(activity_layout,cp);
                                parent_layout.addView(emptyTextview_layout,cp);
                                final Button btnTag = new Button(getApplicationContext());
                                btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                if(m_Qr==0){
                                    btnTag.setText("Finish Quiz >");
                                    SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                    int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                    int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                    int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                    int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                    int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                    Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                }else if(m_Qr<0){
                                    btnTag.setText("Finish Quiz >");
                                    SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                    int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                    int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                    int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                    int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                    int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                    Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                }else{
                                    btnTag.setText("Next Question >");
                                }

                                SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                m_Index = (m_Index + 1) % myQuestion_Data.length;
                                m_Qn = m_Qn+1;
                                editor.putInt(uniqueID+"_MINDEX", m_Index);
                                editor.putInt(uniqueID+"_MQN", m_Qn);
                                editor.putInt(uniqueID+"_MQR", m_Qr);
                                editor.commit();

                                btnTag.setTextColor(0xAAFFFFFF);
                                btnTag.setBackgroundColor(0xAAA6A6A6);
                                activity_layout.addView(btnTag);
                                btnTag.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        btnTag.setEnabled(false);
                                        updateQuestion();
                                        Log.d("TFC_updateQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
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
                        }
                        private void updateQuestion() {
                            Log.d("MCQC_funUQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                            if(m_Index==0){
                                inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                LinearLayout empty_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                LinearLayout empty_layout_1 =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                LinearLayout parent_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bott);


                                LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                parent_layout.removeAllViewsInLayout();

                                TextView greet = new TextView(getApplicationContext());
                                greet.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                greet.setPadding(60,40,35,0);
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
//                            btnshare.setBackground(null);

                                TextView teScore = new TextView(getApplicationContext());
                                teScore.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                teScore.setPadding(60,60,30,60);
                                teScore.setTextSize(20);
                                teScore.setTypeface(face);
                                teScore.setBackgroundColor(0xAAFFFFFF);
                                teScore.setText("You got "+ m_Score+" questions correct out of " + myQuestion_Data.length+" questions");

                                parent_layout.addView(greet);
                                parent_layout.addView(greet_2);
                                //parent_layout.addView(empty_layout_1,cp);
                                parent_layout.addView(teScore);
                                parent_layout.addView(btnshare);


                                //progress_layout.addView(empty_layout,cp);
                                Button btnfinish = new Button(getApplicationContext());
                                btnfinish.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                btnfinish.setText("Next Quiz >");
                                btnfinish.setTextColor(0xAAFFFFFF);
                                btnfinish.setBackgroundColor(0xAAA6A6A6);
                                progress_layout.addView(btnfinish);
                                btnfinish.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.clear();
                                        editor.commit();
                                        finish();
                                    }
                                });
                                btnshare.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent share = new Intent(android.content.Intent.ACTION_SEND);
                                        share.setType("text/plain");

                                        share.putExtra(Intent.EXTRA_SUBJECT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                " questions\n Install GK App to keep your General Knowledge up to date");
                                        share.putExtra(Intent.EXTRA_TEXT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                " questions\n Install GK App to keep your General Knowledge up to date "+"https://catking.in/gk-app");

                                        startActivity(Intent.createChooser(share, "Share your result with friends using"));
                                    }
                                });
                            }
                            m_Question = myQuestion_Data[m_Index];
                            mQuestionText_View.setText(m_Question);
                            mQuestion_Number.setText("Question No: "+m_Qn);
                            mQuestion_Number.setTextColor(0xAA000000);
                            mQuestion_Number.setBackground(null);
                            mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                            mOption_A.setBackgroundColor(0xFFf2f2f2);
                            mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                            mOption_B.setBackgroundColor(0xFFf2f2f2);
                            mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                            mOption_C.setBackgroundColor(0xFFf2f2f2);
                            mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                            mOption_D.setBackgroundColor(0xFFf2f2f2);
                            mOption_A.setEnabled(true);
                            mOption_B.setEnabled(true);
                            mOption_C.setEnabled(true);
                            mOption_D.setEnabled(true);
                            //mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100

                            LinearLayout ll = findViewById(R.id.p_layout);
                            LinearLayout mm = findViewById(R.id.quiz_layout);
                            final LinearLayout child = (LinearLayout) ll.findViewById(R.id.button_layout);
                            ll.removeView(child);
                            final LinearLayout child2 = (LinearLayout) mm.findViewById(R.id.des_layout);
                            mm.removeView(child2);
                            final LinearLayout child3 = (LinearLayout) mm.findViewById(R.id.divider_layout);
                            mm.removeView(child3);
                            final LinearLayout child4 = (LinearLayout) ll.findViewById(R.id.emptyText_layout);
                            ll.removeView(child4);
                        }

                        private void checkAnswer(String userSelection) {
                            String correctAnswer = myA_Data[m_Index];
                            boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                            if(Aa== true){
                                m_Score = m_Score+1;
                                m_Count = (m_Count+1);
                                m_Qr = (myQuestion_Data.length)-m_Count;

                                if(m_Qr==0){
                                    mScoreText_View.setText("Hurray 100% progress. You are done!");
                                }else if(m_Qr<0){
                                    mScoreText_View.setText("Hurray 100% progress. You are done!");
                                }else{
                                    mScoreText_View.setText(m_Qr+" more question to go.");
                                }

                                mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
                                mOption_B.setBackgroundColor(0xAA81c784);
                                mQuestion_Number.setText("Hurray!\n" + "You got it right");
                                mQuestion_Number.setTextColor(0xAA385723);
                                mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));

                                inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                                LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                                LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                                LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                                LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                String des = myDescription_data[m_Index];
                                String check ="";
                                boolean de = check.equalsIgnoreCase(des);
                                if(de == false){
                                    //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                    //quiz_layout.addView(divider_layout,cp);
                                    quiz_layout.addView(description_layout,cp);
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
                                    teTag.setText(myDescription_data[m_Index]);
                                    description_layout.addView(teTag);
                                }

                                parent_layout.addView(activity_layout,cp);
                                parent_layout.addView(emptyTextview_layout,cp);
                                Button btnTag = new Button(getApplicationContext());
                                btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                if(m_Qr==0){
                                    btnTag.setText("Finish Quiz >");
                                    SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                    int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                    int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                    int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                    int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                    int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                    Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                }else if(m_Qr<0){
                                    btnTag.setText("Finish Quiz >");
                                    SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                    int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                    int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                    int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                    int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                    int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                    Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                }else{
                                    btnTag.setText("Next Question >");
                                }

                                SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                m_Index = (m_Index + 1) % myQuestion_Data.length;
                                m_Qn = m_Qn+1;
                                editor.putInt(uniqueID+"_MINDEX", m_Index);
                                editor.putInt(uniqueID+"_MQN", m_Qn);
                                editor.putInt(uniqueID+"_MQR", m_Qr);
                                editor.putInt(uniqueID+"_MSCORE", m_Score);
                                editor.commit();

                                btnTag.setTextColor(0xAAFFFFFF);
                                btnTag.setBackgroundColor(0xAAA6A6A6);
                                activity_layout.addView(btnTag);
                                btnTag.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        updateQuestion();
                                        Log.d("MCQC_updateq","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                    }
                                });
                            }else{
                                m_Count = (m_Count+1);
                                Log.d("MCQC_mcount", String.valueOf(m_Count));
                                m_Qr = myQuestion_Data.length-m_Count;
                                Log.d("MCQC_mqr", String.valueOf(m_Qr));

                                if(m_Qr==0){
                                    mScoreText_View.setText("Hurray 100% progress. You are done!");
                                }else if(m_Qr<0){
                                    mScoreText_View.setText("Hurray 100% progress. You are done!");
                                }else{
                                    mScoreText_View.setText(m_Qr+" more question to go.");
                                }
                                mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
                                mOption_B.setBackgroundColor(0xAAe57272);
                                mQuestion_Number.setText("Oops!\n" + "You got it wrong");
                                mQuestion_Number.setTextColor(0xAAFFFFFF);
                                mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_false));
                                inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                                LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                                LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                                LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                                LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);

                                LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                String des = myDescription_data[m_Index];
                                String check ="";
                                boolean de = check.equalsIgnoreCase(des);
                                if(de == false){
                                    quiz_layout.addView(description_layout,cp);
                                    TextView teTag = new TextView(getApplicationContext());
                                    teTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    teTag.setPadding(70,5,40,5);
                                    teTag.setTextSize(18);
                                    teTag.setVerticalScrollBarEnabled(true);
                                    teTag.setMaxLines(4);
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
                                    teTag.setText(myDescription_data[m_Index]);
                                    description_layout.addView(teTag);
                                }


                                parent_layout.addView(activity_layout,cp);
                                parent_layout.addView(emptyTextview_layout,cp);
                                final Button btnTag = new Button(getApplicationContext());
                                btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                if(m_Qr==0){
                                    btnTag.setText("Finish Quiz >");
                                    SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                    int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                    int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                    int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                    int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                    int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                    Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                }else if(m_Qr<0){
                                    btnTag.setText("Finish Quiz >");
                                    SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                    int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                    int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                    int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                    int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                    int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                    Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                }else{
                                    btnTag.setText("Next Question >");
                                }

                                SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                m_Index = (m_Index + 1) % myQuestion_Data.length;
                                m_Qn = m_Qn+1;
                                editor.putInt(uniqueID+"_MINDEX", m_Index);
                                editor.putInt(uniqueID+"_MQN", m_Qn);
                                editor.putInt(uniqueID+"_MQR", m_Qr);
                                editor.commit();

                                btnTag.setTextColor(0xAAFFFFFF);
                                btnTag.setBackgroundColor(0xAAA6A6A6);
                                activity_layout.addView(btnTag);
                                btnTag.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        btnTag.setEnabled(false);
                                        updateQuestion();
                                        Log.d("TFC_updateQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
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
                        }
                        private void updateQuestion() {
                            Log.d("MCQC_funUQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                            if(m_Index==0){
                                inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                LinearLayout empty_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                LinearLayout empty_layout_1 =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                LinearLayout parent_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bott);


                                LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                parent_layout.removeAllViewsInLayout();

                                TextView greet = new TextView(getApplicationContext());
                                greet.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                greet.setPadding(60,40,35,0);
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
//                            btnshare.setBackground(null);

                                TextView teScore = new TextView(getApplicationContext());
                                teScore.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                teScore.setPadding(60,60,30,60);
                                teScore.setTextSize(20);
                                teScore.setTypeface(face);
                                teScore.setBackgroundColor(0xAAFFFFFF);
                                teScore.setText("You got "+ m_Score+" questions correct out of " + myQuestion_Data.length+" questions");

                                parent_layout.addView(greet);
                                parent_layout.addView(greet_2);
                                //parent_layout.addView(empty_layout_1,cp);
                                parent_layout.addView(teScore);
                                parent_layout.addView(btnshare);


                                //progress_layout.addView(empty_layout,cp);
                                Button btnfinish = new Button(getApplicationContext());
                                btnfinish.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                btnfinish.setText("Next Quiz >");
                                btnfinish.setTextColor(0xAAFFFFFF);
                                btnfinish.setBackgroundColor(0xAAA6A6A6);
                                progress_layout.addView(btnfinish);
                                btnfinish.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.clear();
                                        editor.commit();
                                        finish();
                                    }
                                });
                                btnshare.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent share = new Intent(android.content.Intent.ACTION_SEND);
                                        share.setType("text/plain");

                                        share.putExtra(Intent.EXTRA_SUBJECT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                " questions\n Install GK App to keep your General Knowledge up to date");
                                        share.putExtra(Intent.EXTRA_TEXT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                " questions\n Install GK App to keep your General Knowledge up to date "+"https://catking.in/gk-app");

                                        startActivity(Intent.createChooser(share, "Share your result with friends using"));
                                    }
                                });
                            }
                            m_Question = myQuestion_Data[m_Index];
                            mQuestionText_View.setText(m_Question);
                            mQuestion_Number.setText("Question No: "+m_Qn);
                            mQuestion_Number.setTextColor(0xAA000000);
                            mQuestion_Number.setBackground(null);
                            mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                            mOption_A.setBackgroundColor(0xFFf2f2f2);
                            mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                            mOption_B.setBackgroundColor(0xFFf2f2f2);
                            mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                            mOption_C.setBackgroundColor(0xFFf2f2f2);
                            mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                            mOption_D.setBackgroundColor(0xFFf2f2f2);
                            mOption_A.setEnabled(true);
                            mOption_B.setEnabled(true);
                            mOption_C.setEnabled(true);
                            mOption_D.setEnabled(true);
                            //mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100

                            LinearLayout ll = findViewById(R.id.p_layout);
                            LinearLayout mm = findViewById(R.id.quiz_layout);
                            final LinearLayout child = (LinearLayout) ll.findViewById(R.id.button_layout);
                            ll.removeView(child);
                            final LinearLayout child2 = (LinearLayout) mm.findViewById(R.id.des_layout);
                            mm.removeView(child2);
                            final LinearLayout child3 = (LinearLayout) mm.findViewById(R.id.divider_layout);
                            mm.removeView(child3);
                            final LinearLayout child4 = (LinearLayout) ll.findViewById(R.id.emptyText_layout);
                            ll.removeView(child4);

                        }

                        private void checkAnswer(String userSelection) {
                            String correctAnswer = myA_Data[m_Index];
                            boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                            if(Aa== true){
                                m_Score = m_Score+1;
                                m_Count = (m_Count+1);
                                Log.d("TFC_mcount", String.valueOf(m_Count));
                                m_Qr = myQuestion_Data.length-m_Count;
                                Log.d("TFC_mqr", String.valueOf(m_Qr));

                                if(m_Qr==0){
                                    mScoreText_View.setText("Hurray 100% progress. You are done!");
                                }else if(m_Qr<0){
                                    mScoreText_View.setText("Hurray 100% progress. You are done!");
                                }else{
                                    mScoreText_View.setText(m_Qr+" more question to go.");
                                }
                                mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);


                                mOption_C.setBackgroundColor(0xAA81c784);
                                //mOption_A.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                                mQuestion_Number.setText("Hurray!\n" + "You got it right");
                                mQuestion_Number.setTextColor(0xAA385723);
                                mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));


                                inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                                LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                                LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                                LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                                LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                String des = myDescription_data[m_Index];
                                String check ="";
                                boolean de = check.equalsIgnoreCase(des);
                                if(de == false){
                                    //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                    //parent_layout.addView(divider_layout,cp);

                                    quiz_layout.addView(description_layout,cp);
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
                                    teTag.setText(myDescription_data[m_Index]);
                                    description_layout.addView(teTag);
                                }

                                parent_layout.addView(activity_layout,cp);
                                parent_layout.addView(emptyTextview_layout,cp);
                                Button btnTag = new Button(getApplicationContext());
                                btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                if(m_Qr==0){
                                    btnTag.setText("Finish Quiz >");
                                    SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                    int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                    int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                    int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                    int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                    int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                    Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                }else if(m_Qr<0){
                                    btnTag.setText("Finish Quiz >");
                                    SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                    int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                    int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                    int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                    int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                    int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                    Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                }else{
                                    btnTag.setText("Next Question >");
                                }

                                SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                m_Index = (m_Index + 1) % myQuestion_Data.length;
                                m_Qn = m_Qn+1;
                                editor.putInt(uniqueID+"_MINDEX", m_Index);
                                editor.putInt(uniqueID+"_MQN", m_Qn);
                                editor.putInt(uniqueID+"_MQR", m_Qr);
                                editor.putInt(uniqueID+"_MSCORE", m_Score);
                                editor.commit();

                                btnTag.setTextColor(0xAAFFFFFF);
                                btnTag.setBackgroundColor(0xAAA6A6A6);
                                activity_layout.addView(btnTag);
                                btnTag.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        updateQuestion();
                                        Log.d("MCQC_updateq","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                    }
                                });
                            }else{
                                m_Count = (m_Count+1);
                                Log.d("TFC_mcount", String.valueOf(m_Count));
                                m_Qr = myQuestion_Data.length-m_Count;
                                Log.d("TFC_mqr", String.valueOf(m_Qr));

                                if(m_Qr==0){
                                    mScoreText_View.setText("Hurray 100% progress. You are done!");
                                }else if(m_Qr<0){
                                    mScoreText_View.setText("Hurray 100% progress. You are done!");
                                }else{
                                    mScoreText_View.setText(m_Qr+" more question to go.");
                                }
                                mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);


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
                                LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);

                                LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                String des = myDescription_data[m_Index];
                                String check ="";
                                boolean de = check.equalsIgnoreCase(des);
                                if(de == false){
                                    //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                    //parent_layout.addView(divider_layout,cp);
//                                ImageView divider = new ImageView(getApplicationContext());
//                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                                lp.setMargins(0, 0, 0, 0);
//                                divider.setLayoutParams(lp);
//                                divider.setBackgroundColor(0xAAAAAAAA);
                                    quiz_layout.addView(description_layout,cp);
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
                                    teTag.setText(myDescription_data[m_Index]);
                                    description_layout.addView(teTag);
                                }


                                parent_layout.addView(activity_layout,cp);
                                parent_layout.addView(emptyTextview_layout,cp);
                                final Button btnTag = new Button(getApplicationContext());
                                btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                if(m_Qr==0){
                                    btnTag.setText("Finish Quiz >");
                                    SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                    int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                    int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                    int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                    int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                    int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                    Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                }else if(m_Qr<0){
                                    btnTag.setText("Finish Quiz >");
                                    SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                    int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                    int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                    int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                    int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                    int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                    Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                }else{
                                    btnTag.setText("Next Question >");
                                }
                                SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                m_Index = (m_Index + 1) % myQuestion_Data.length;
                                m_Qn = m_Qn+1;
                                editor.putInt(uniqueID+"_MINDEX", m_Index);
                                editor.putInt(uniqueID+"_MQN", m_Qn);
                                editor.putInt(uniqueID+"_MQR", m_Qr);
                                editor.commit();

                                btnTag.setTextColor(0xAAFFFFFF);
                                btnTag.setBackgroundColor(0xAAA6A6A6);
                                activity_layout.addView(btnTag);
                                btnTag.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        btnTag.setEnabled(false);
                                        updateQuestion();
                                        Log.d("MCQC_updateQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
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
                    mOption_D.setOnClickListener(new View.OnClickListener()  {
                        @Override
                        public void onClick(View v) {
                            mOption_A.setEnabled(false);
                            mOption_B.setEnabled(false);
                            mOption_C.setEnabled(false);
                            mOption_D.setEnabled(false);

                            String a = myA_Data[m_Index];
                            setAnswer(a);
                            checkAnswer("d");
                        }
                        private void updateQuestion() {
                            Log.d("MCQC_funUQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                            if(m_Index==0){
                                inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                LinearLayout empty_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                LinearLayout empty_layout_1 =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                LinearLayout parent_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bott);


                                LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                parent_layout.removeAllViewsInLayout();

                                TextView greet = new TextView(getApplicationContext());
                                greet.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                greet.setPadding(60,40,35,0);
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
//                            btnshare.setBackground(null);

                                TextView teScore = new TextView(getApplicationContext());
                                teScore.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                teScore.setPadding(60,60,30,60);
                                teScore.setTextSize(20);
                                teScore.setTypeface(face);
                                teScore.setBackgroundColor(0xAAFFFFFF);
                                teScore.setText("You got "+ m_Score+" questions correct out of " + myQuestion_Data.length+" questions");

                                parent_layout.addView(greet);
                                parent_layout.addView(greet_2);
                                //parent_layout.addView(empty_layout_1,cp);
                                parent_layout.addView(teScore);
                                parent_layout.addView(btnshare);


                                //progress_layout.addView(empty_layout,cp);
                                Button btnfinish = new Button(getApplicationContext());
                                btnfinish.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                btnfinish.setText("Next Quiz >");
                                btnfinish.setTextColor(0xAAFFFFFF);
                                btnfinish.setBackgroundColor(0xAAA6A6A6);
                                progress_layout.addView(btnfinish);
                                btnfinish.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.clear();
                                        editor.commit();
                                        finish();
                                    }
                                });
                                btnshare.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent share = new Intent(android.content.Intent.ACTION_SEND);
                                        share.setType("text/plain");

                                        share.putExtra(Intent.EXTRA_SUBJECT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                " questions\n Install GK App to keep your General Knowledge up to date");
                                        share.putExtra(Intent.EXTRA_TEXT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                " questions\n Install GK App to keep your General Knowledge up to date "+"https://catking.in/gk-app");

                                        startActivity(Intent.createChooser(share, "Share your result with friends using"));
                                    }
                                });
                            }
                            m_Question = myQuestion_Data[m_Index];
                            mQuestionText_View.setText(m_Question);
                            mQuestion_Number.setText("Question No: "+m_Qn);
                            mQuestion_Number.setTextColor(0xAA000000);
                            mQuestion_Number.setBackground(null);
                            mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                            mOption_A.setBackgroundColor(0xFFf2f2f2);
                            mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                            mOption_B.setBackgroundColor(0xFFf2f2f2);
                            mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                            mOption_C.setBackgroundColor(0xFFf2f2f2);
                            mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                            mOption_D.setBackgroundColor(0xFFf2f2f2);
                            mOption_A.setEnabled(true);
                            mOption_B.setEnabled(true);
                            mOption_C.setEnabled(true);
                            mOption_D.setEnabled(true);
                            //mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100

                            LinearLayout ll = findViewById(R.id.p_layout);
                            LinearLayout mm = findViewById(R.id.quiz_layout);
                            final LinearLayout child = (LinearLayout) ll.findViewById(R.id.button_layout);
                            ll.removeView(child);
                            final LinearLayout child2 = (LinearLayout) mm.findViewById(R.id.des_layout);
                            mm.removeView(child2);
                            final LinearLayout child3 = (LinearLayout) mm.findViewById(R.id.divider_layout);
                            mm.removeView(child3);
                            final LinearLayout child4 = (LinearLayout) ll.findViewById(R.id.emptyText_layout);
                            ll.removeView(child4);
                        }

                        private void checkAnswer(String userSelection) {
                            String correctAnswer = myA_Data[m_Index];
                            boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                            if(Aa== true){
                                m_Score = m_Score+1;
                                m_Count = (m_Count+1);
                                Log.d("TFC_mcount", String.valueOf(m_Count));
                                m_Qr = myQuestion_Data.length-m_Count;
                                Log.d("TFC_mqr", String.valueOf(m_Qr));

                                if(m_Qr==0){
                                    mScoreText_View.setText("Hurray 100% progress. You are done!");
                                }else if(m_Qr<0){
                                    mScoreText_View.setText("Hurray 100% progress. You are done!");
                                }else{
                                    mScoreText_View.setText(m_Qr+" more question to go.");
                                }
                                mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);


                                mOption_D.setBackgroundColor(0xAA81c784);
                                //mOption_D.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                                mQuestion_Number.setText("Hurray!\n" + "You got it right");
                                mQuestion_Number.setTextColor(0xAA385723);
                                mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));


                                inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                                LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                                LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                                LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                                LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                String des = myDescription_data[m_Index];
                                String check ="";
                                boolean de = check.equalsIgnoreCase(des);
                                if(de == false){
                                    //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                    //parent_layout.addView(divider_layout,cp);

                                    quiz_layout.addView(description_layout,cp);
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
                                    teTag.setText(myDescription_data[m_Index]);
                                    description_layout.addView(teTag);
                                }

                                parent_layout.addView(activity_layout,cp);
                                parent_layout.addView(emptyTextview_layout,cp);
                                Button btnTag = new Button(getApplicationContext());
                                btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                if(m_Qr==0){
                                    btnTag.setText("Finish Quiz >");
                                    SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                    int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                    int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                    int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                    int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                    int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                    Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                }else if(m_Qr<0){
                                    btnTag.setText("Finish Quiz >");
                                    SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                    int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                    int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                    int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                    int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                    int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                    Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                }else{
                                    btnTag.setText("Next Question >");
                                }

                                SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                m_Index = (m_Index + 1) % myQuestion_Data.length;
                                m_Qn = m_Qn+1;
                                editor.putInt(uniqueID+"_MINDEX", m_Index);
                                editor.putInt(uniqueID+"_MQN", m_Qn);
                                editor.putInt(uniqueID+"_MQR", m_Qr);
                                editor.putInt(uniqueID+"_MSCORE", m_Score);
                                editor.commit();

                                btnTag.setTextColor(0xAAFFFFFF);
                                btnTag.setBackgroundColor(0xAAA6A6A6);
                                activity_layout.addView(btnTag);
                                btnTag.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("MCQC_updateq","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                        updateQuestion();
                                    }
                                });
                            }else{
                                m_Count = (m_Count+1);
                                Log.d("TFC_mcount", String.valueOf(m_Count));
                                m_Qr = myQuestion_Data.length-m_Count;
                                Log.d("TFC_mqr", String.valueOf(m_Qr));

                                if(m_Qr==0){
                                    mScoreText_View.setText("Hurray 100% progress. You are done!");
                                }else if(m_Qr<0){
                                    mScoreText_View.setText("Hurray 100% progress. You are done!");
                                }else{
                                    mScoreText_View.setText(m_Qr+" more question to go.");
                                }
                                mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);


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
                                LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);

                                LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                String des = myDescription_data[m_Index];
                                String check ="";
                                boolean de = check.equalsIgnoreCase(des);
                                if(de == false){
                                    //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                    //parent_layout.addView(divider_layout,cp);
//                                ImageView divider = new ImageView(getApplicationContext());
//                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                                lp.setMargins(0, 0, 0, 0);
//                                divider.setLayoutParams(lp);
//                                divider.setBackgroundColor(0xAAAAAAAA);
                                    quiz_layout.addView(description_layout,cp);
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
                                    teTag.setText(myDescription_data[m_Index]);
                                    description_layout.addView(teTag);
                                }


                                parent_layout.addView(activity_layout,cp);
                                parent_layout.addView(emptyTextview_layout,cp);
                                final Button btnTag = new Button(getApplicationContext());
                                btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                if(m_Qr==0){
                                    btnTag.setText("Finish Quiz >");
                                    SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                    int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                    int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                    int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                    int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                    int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                    Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                }else if(m_Qr<0){
                                    btnTag.setText("Finish Quiz >");
                                    SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                    int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                    int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                    int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                    int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                    int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                    Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                }else{
                                    btnTag.setText("Next Question >");
                                }

                                SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                m_Index = (m_Index + 1) % myQuestion_Data.length;
                                m_Qn = m_Qn+1;
                                editor.putInt(uniqueID+"_MINDEX", m_Index);
                                editor.putInt(uniqueID+"_MQN", m_Qn);
                                editor.putInt(uniqueID+"_MQR", m_Qr);
                                editor.commit();

                                btnTag.setTextColor(0xAAFFFFFF);
                                btnTag.setBackgroundColor(0xAAA6A6A6);
                                activity_layout.addView(btnTag);
                                btnTag.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        btnTag.setEnabled(false);
                                        updateQuestion();
                                        Log.d("MCQC_updateQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
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
                }else {
                    LinearLayout resume_quiz = (LinearLayout) inflater.inflate(R.layout.activity_resumecard_layout, null);
                    dd.addView(resume_quiz);
                    mContinue = (Button) findViewById(R.id.button_continue);
                    mStartFresh =(Button) findViewById(R.id.button_sf);

                    mContinue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                            m_Index  = sp.getInt(uniqueID+"_MINDEX", 0);
                            m_Score = sp.getInt(uniqueID+"_MSCORE",0);
                            m_Qn = sp.getInt(uniqueID+"_MQN",1);
                            m_Qr = sp.getInt(uniqueID+"_MQR",0);
                            m_Count = sp.getInt(uniqueID+"_MCOUNT",0);
                            LinearLayout dd = findViewById(R.id.loaded_quiz);
                            dd.removeAllViews();

                            LinearLayout activity_stQ = (LinearLayout) inflater.inflate(R.layout.activity_quizcard_layout, null);
                            dd.addView(activity_stQ,de);

                            cardMCQ = (CardView)findViewById(R.id.cardview_mcq);
                            mOption_A = (Button) findViewById(R.id.button_option_A);
                            mOption_B = (Button) findViewById(R.id.button_option_B);
                            mOption_C = (Button) findViewById(R.id.button_option_C);
                            mOption_D = (Button) findViewById(R.id.button_option_D);
                            mQuestion_Number = findViewById(R.id.mcq_qn_view_card);
                            mQuestionText_View = findViewById(R.id.mcq_text_view_card);
                            mScoreText_View = findViewById(R.id.score_mcq_card);
                            mProgress_Bar = (ProgressBar) findViewById(R.id.progress_bar_mcq_card);
                            mProgress_Bar.setProgressTintList(ColorStateList.valueOf(0xAA92D050));
                            mProgress_Bar.setProgressBackgroundTintList(ColorStateList.valueOf(0xFFE9E6E6));
                            mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT*(m_Index));

                            m_Question = myQuestion_Data[m_Index];
                            mQuestionText_View.setText(m_Question);
                            mQuestion_Number.setText("Question No: "+m_Qn);
                            mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                            mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                            mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                            mOption_D.setText("D. "+myOptina_D_data[m_Index]);

                            if(m_Qr ==0 ){
                                m_Qr = (myQuestion_Data.length)-m_Index;
                                Log.d("MCQ_qr", String.valueOf(m_Qr));
                            }

                            mScoreText_View.setText(m_Qr+" question to go.");
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
                                    Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_Index+" mScore:"+m_Score+" mQn:"+m_Qn+" mQr:"+m_Qr+" mCount:"+m_Count);
                                }
                                private void updateQuestion() {
                                    Log.d("TFC_funUQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                    if(m_Index==0){
                                        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        LinearLayout empty_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                        LinearLayout empty_layout_1 =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                        //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                        LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bott);


                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        parent_layout.removeAllViewsInLayout();

                                        TextView greet = new TextView(getApplicationContext());
                                        greet.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        greet.setPadding(60,40,35,0);
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
                                        teScore.setText("You got "+ m_Score+" questions correct out of " + myQuestion_Data.length+" questions");

                                        parent_layout.addView(greet);
                                        parent_layout.addView(greet_2);
                                        parent_layout.addView(teScore);
                                        parent_layout.addView(btnshare);


                                        Button btnfinish = new Button(getApplicationContext());
                                        btnfinish.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        btnfinish.setText("Next Quiz >");
                                        btnfinish.setTextColor(0xAAFFFFFF);
                                        btnfinish.setBackgroundColor(0xAAA6A6A6);
                                        progress_layout.addView(btnfinish);
                                        btnfinish.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                SharedPreferences.Editor editor = sp.edit();
                                                editor.clear();
                                                editor.commit();
                                                finish();
                                            }
                                        });
                                        btnshare.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                                                share.setType("text/plain");

                                                share.putExtra(Intent.EXTRA_SUBJECT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                        " questions\n Install GK App to keep your General Knowledge up to date");
                                                share.putExtra(Intent.EXTRA_TEXT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                        " questions\n Install GK App to keep your General Knowledge up to date "+"https://catking.in/gk-app");

                                                startActivity(Intent.createChooser(share, "Share your result with friends using"));
                                            }
                                        });
                                    }
                                    m_Question = myQuestion_Data[m_Index];
                                    mQuestionText_View.setText(m_Question);
                                    mQuestion_Number.setText("Question No: "+m_Qn);
                                    mQuestion_Number.setTextColor(0xAA000000);
                                    mQuestion_Number.setBackground(null);
                                    mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                                    mOption_A.setBackgroundColor(0xFFf2f2f2);
                                    mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                                    mOption_B.setBackgroundColor(0xFFf2f2f2);
                                    mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                                    mOption_C.setBackgroundColor(0xFFf2f2f2);
                                    mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                                    mOption_D.setBackgroundColor(0xFFf2f2f2);
                                    mOption_A.setEnabled(true);
                                    mOption_B.setEnabled(true);
                                    mOption_C.setEnabled(true);
                                    mOption_D.setEnabled(true);

                                    LinearLayout ll = findViewById(R.id.p_layout);
                                    LinearLayout mm = findViewById(R.id.quiz_layout);
                                    final LinearLayout child = (LinearLayout) ll.findViewById(R.id.button_layout);
                                    ll.removeView(child);
                                    final LinearLayout child2 = (LinearLayout) mm.findViewById(R.id.des_layout);
                                    mm.removeView(child2);
                                    final LinearLayout child3 = (LinearLayout) mm.findViewById(R.id.divider_layout);
                                    mm.removeView(child3);
                                    final LinearLayout child4 = (LinearLayout) ll.findViewById(R.id.emptyText_layout);
                                    ll.removeView(child4);
                                }

                                private void checkAnswer(String userSelection) {
                                    String correctAnswer = myA_Data[m_Index];
                                    boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                                    if(Aa== true){
                                        m_Score = m_Score+1;
                                        m_Count = (m_Count+1);
                                        m_Qr = (myQuestion_Data.length)-m_Count;

                                        if(m_Qr==0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else if(m_Qr<0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else{
                                            mScoreText_View.setText(m_Qr+" more question to go.");
                                        }

                                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
                                        mOption_A.setBackgroundColor(0xAA81c784);
                                        //mOption_A.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                                        mQuestion_Number.setText("Hurray!\n" + "You got it right");
                                        mQuestion_Number.setTextColor(0xAA385723);
                                        mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));

                                        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                                        LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                                        LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                                        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                                        LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        String des = myDescription_data[m_Index];
                                        String check ="";
                                        boolean de = check.equalsIgnoreCase(des);
                                        if(de == false){
                                            //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                            //quiz_layout.addView(divider_layout,cp);
                                            quiz_layout.addView(description_layout,cp);
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
                                            teTag.setText(myDescription_data[m_Index]);
                                            description_layout.addView(teTag);
                                        }

                                        parent_layout.addView(activity_layout,cp);
                                        parent_layout.addView(emptyTextview_layout,cp);
                                        Button btnTag = new Button(getApplicationContext());
                                        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        if(m_Qr==0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else if(m_Qr<0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else{
                                            btnTag.setText("Next Question >");
                                        }

                                        SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                        m_Index = (m_Index + 1) % myQuestion_Data.length;
                                        m_Qn = m_Qn+1;
                                        editor.putInt(uniqueID+"_MINDEX", m_Index);
                                        editor.putInt(uniqueID+"_MQN", m_Qn);
                                        editor.putInt(uniqueID+"_MQR", m_Qr);
                                        editor.putInt(uniqueID+"_MSCORE", m_Score);
                                        editor.commit();

                                        btnTag.setTextColor(0xAAFFFFFF);
                                        btnTag.setBackgroundColor(0xAAA6A6A6);
                                        activity_layout.addView(btnTag);
                                        btnTag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                updateQuestion();
                                                Log.d("MCQC_updateq","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                            }
                                        });
                                    }else{
                                        m_Count = (m_Count+1);
                                        Log.d("MCQC_mcount", String.valueOf(m_Count));
                                        m_Qr = myQuestion_Data.length-m_Count;
                                        Log.d("MCQC_mqr", String.valueOf(m_Qr));

                                        if(m_Qr==0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else if(m_Qr<0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else{
                                            mScoreText_View.setText(m_Qr+" more question to go.");
                                        }
                                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
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
                                        LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);

                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        String des = myDescription_data[m_Index];
                                        String check ="";
                                        boolean de = check.equalsIgnoreCase(des);
                                        if(de == false){
                                            //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                            //quiz_layout.addView(divider_layout,cp);
                                            quiz_layout.addView(description_layout,cp);
                                            TextView teTag = new TextView(getApplicationContext());
                                            teTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                            teTag.setPadding(70,5,40,5);
                                            teTag.setTextSize(18);
                                            teTag.setVerticalScrollBarEnabled(true);
                                            teTag.setMaxLines(4);
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
                                            teTag.setText(myDescription_data[m_Index]);
                                            description_layout.addView(teTag);
                                        }


                                        parent_layout.addView(activity_layout,cp);
                                        parent_layout.addView(emptyTextview_layout,cp);
                                        final Button btnTag = new Button(getApplicationContext());
                                        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        if(m_Qr==0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else if(m_Qr<0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else{
                                            btnTag.setText("Next Question >");
                                        }

                                        SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                        m_Index = (m_Index + 1) % myQuestion_Data.length;
                                        m_Qn = m_Qn+1;
                                        editor.putInt(uniqueID+"_MINDEX", m_Index);
                                        editor.putInt(uniqueID+"_MQN", m_Qn);
                                        editor.putInt(uniqueID+"_MQR", m_Qr);
                                        editor.commit();

                                        btnTag.setTextColor(0xAAFFFFFF);
                                        btnTag.setBackgroundColor(0xAAA6A6A6);
                                        activity_layout.addView(btnTag);
                                        btnTag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                btnTag.setEnabled(false);
                                                updateQuestion();
                                                Log.d("TFC_updateQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
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
                                }
                                private void updateQuestion() {
                                    Log.d("MCQC_funUQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                    if(m_Index==0){
                                        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        LinearLayout empty_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                        LinearLayout empty_layout_1 =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                        //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                        LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bott);


                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        parent_layout.removeAllViewsInLayout();

                                        TextView greet = new TextView(getApplicationContext());
                                        greet.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        greet.setPadding(60,40,35,0);
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
//                            btnshare.setBackground(null);

                                        TextView teScore = new TextView(getApplicationContext());
                                        teScore.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        teScore.setPadding(60,60,30,60);
                                        teScore.setTextSize(20);
                                        teScore.setTypeface(face);
                                        teScore.setBackgroundColor(0xAAFFFFFF);
                                        teScore.setText("You got "+ m_Score+" questions correct out of " + myQuestion_Data.length+" questions");

                                        parent_layout.addView(greet);
                                        parent_layout.addView(greet_2);
                                        //parent_layout.addView(empty_layout_1,cp);
                                        parent_layout.addView(teScore);
                                        parent_layout.addView(btnshare);


                                        //progress_layout.addView(empty_layout,cp);
                                        Button btnfinish = new Button(getApplicationContext());
                                        btnfinish.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        btnfinish.setText("Next Quiz >");
                                        btnfinish.setTextColor(0xAAFFFFFF);
                                        btnfinish.setBackgroundColor(0xAAA6A6A6);
                                        progress_layout.addView(btnfinish);
                                        btnfinish.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                SharedPreferences.Editor editor = sp.edit();
                                                editor.clear();
                                                editor.commit();
                                                finish();
                                            }
                                        });
                                        btnshare.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                                                share.setType("text/plain");

                                                share.putExtra(Intent.EXTRA_SUBJECT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                        " questions\n Install GK App to keep your General Knowledge up to date");
                                                share.putExtra(Intent.EXTRA_TEXT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                        " questions\n Install GK App to keep your General Knowledge up to date "+"https://catking.in/gk-app");

                                                startActivity(Intent.createChooser(share, "Share your result with friends using"));
                                            }
                                        });
                                    }
                                    m_Question = myQuestion_Data[m_Index];
                                    mQuestionText_View.setText(m_Question);
                                    mQuestion_Number.setText("Question No: "+m_Qn);
                                    mQuestion_Number.setTextColor(0xAA000000);
                                    mQuestion_Number.setBackground(null);
                                    mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                                    mOption_A.setBackgroundColor(0xFFf2f2f2);
                                    mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                                    mOption_B.setBackgroundColor(0xFFf2f2f2);
                                    mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                                    mOption_C.setBackgroundColor(0xFFf2f2f2);
                                    mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                                    mOption_D.setBackgroundColor(0xFFf2f2f2);
                                    mOption_A.setEnabled(true);
                                    mOption_B.setEnabled(true);
                                    mOption_C.setEnabled(true);
                                    mOption_D.setEnabled(true);
                                    //mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100

                                    LinearLayout ll = findViewById(R.id.p_layout);
                                    LinearLayout mm = findViewById(R.id.quiz_layout);
                                    final LinearLayout child = (LinearLayout) ll.findViewById(R.id.button_layout);
                                    ll.removeView(child);
                                    final LinearLayout child2 = (LinearLayout) mm.findViewById(R.id.des_layout);
                                    mm.removeView(child2);
                                    final LinearLayout child3 = (LinearLayout) mm.findViewById(R.id.divider_layout);
                                    mm.removeView(child3);
                                    final LinearLayout child4 = (LinearLayout) ll.findViewById(R.id.emptyText_layout);
                                    ll.removeView(child4);
                                }

                                private void checkAnswer(String userSelection) {
                                    String correctAnswer = myA_Data[m_Index];
                                    boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                                    if(Aa== true){
                                        m_Score = m_Score+1;
                                        m_Count = (m_Count+1);
                                        m_Qr = (myQuestion_Data.length)-m_Count;

                                        if(m_Qr==0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else if(m_Qr<0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else{
                                            mScoreText_View.setText(m_Qr+" more question to go.");
                                        }

                                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
                                        mOption_B.setBackgroundColor(0xAA81c784);
                                        mQuestion_Number.setText("Hurray!\n" + "You got it right");
                                        mQuestion_Number.setTextColor(0xAA385723);
                                        mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));

                                        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                                        LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                                        LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                                        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                                        LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        String des = myDescription_data[m_Index];
                                        String check ="";
                                        boolean de = check.equalsIgnoreCase(des);
                                        if(de == false){
                                            //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                            //quiz_layout.addView(divider_layout,cp);
                                            quiz_layout.addView(description_layout,cp);
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
                                            teTag.setText(myDescription_data[m_Index]);
                                            description_layout.addView(teTag);
                                        }

                                        parent_layout.addView(activity_layout,cp);
                                        parent_layout.addView(emptyTextview_layout,cp);
                                        Button btnTag = new Button(getApplicationContext());
                                        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        if(m_Qr==0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else if(m_Qr<0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else{
                                            btnTag.setText("Next Question >");
                                        }

                                        SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                        m_Index = (m_Index + 1) % myQuestion_Data.length;
                                        m_Qn = m_Qn+1;
                                        editor.putInt(uniqueID+"_MINDEX", m_Index);
                                        editor.putInt(uniqueID+"_MQN", m_Qn);
                                        editor.putInt(uniqueID+"_MQR", m_Qr);
                                        editor.putInt(uniqueID+"_MSCORE", m_Score);
                                        editor.commit();

                                        btnTag.setTextColor(0xAAFFFFFF);
                                        btnTag.setBackgroundColor(0xAAA6A6A6);
                                        activity_layout.addView(btnTag);
                                        btnTag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                updateQuestion();
                                                Log.d("MCQC_updateq","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                            }
                                        });
                                    }else{
                                        m_Count = (m_Count+1);
                                        Log.d("MCQC_mcount", String.valueOf(m_Count));
                                        m_Qr = myQuestion_Data.length-m_Count;
                                        Log.d("MCQC_mqr", String.valueOf(m_Qr));

                                        if(m_Qr==0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else if(m_Qr<0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else{
                                            mScoreText_View.setText(m_Qr+" more question to go.");
                                        }
                                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
                                        mOption_B.setBackgroundColor(0xAAe57272);
                                        mQuestion_Number.setText("Oops!\n" + "You got it wrong");
                                        mQuestion_Number.setTextColor(0xAAFFFFFF);
                                        mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_false));
                                        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                                        LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                                        LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                                        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                                        LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);

                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        String des = myDescription_data[m_Index];
                                        String check ="";
                                        boolean de = check.equalsIgnoreCase(des);
                                        if(de == false){
                                            quiz_layout.addView(description_layout,cp);
                                            TextView teTag = new TextView(getApplicationContext());
                                            teTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                            teTag.setPadding(70,5,40,5);
                                            teTag.setTextSize(18);
                                            teTag.setVerticalScrollBarEnabled(true);
                                            teTag.setMaxLines(4);
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
                                            teTag.setText(myDescription_data[m_Index]);
                                            description_layout.addView(teTag);
                                        }


                                        parent_layout.addView(activity_layout,cp);
                                        parent_layout.addView(emptyTextview_layout,cp);
                                        final Button btnTag = new Button(getApplicationContext());
                                        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        if(m_Qr==0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else if(m_Qr<0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else{
                                            btnTag.setText("Next Question >");
                                        }

                                        SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                        m_Index = (m_Index + 1) % myQuestion_Data.length;
                                        m_Qn = m_Qn+1;
                                        editor.putInt(uniqueID+"_MINDEX", m_Index);
                                        editor.putInt(uniqueID+"_MQN", m_Qn);
                                        editor.putInt(uniqueID+"_MQR", m_Qr);
                                        editor.commit();

                                        btnTag.setTextColor(0xAAFFFFFF);
                                        btnTag.setBackgroundColor(0xAAA6A6A6);
                                        activity_layout.addView(btnTag);
                                        btnTag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                btnTag.setEnabled(false);
                                                updateQuestion();
                                                Log.d("TFC_updateQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
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
                                }
                                private void updateQuestion() {
                                    Log.d("MCQC_funUQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                    if(m_Index==0){
                                        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        LinearLayout empty_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                        LinearLayout empty_layout_1 =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                        //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                        LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bott);


                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        parent_layout.removeAllViewsInLayout();

                                        TextView greet = new TextView(getApplicationContext());
                                        greet.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        greet.setPadding(60,40,35,0);
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
//                            btnshare.setBackground(null);

                                        TextView teScore = new TextView(getApplicationContext());
                                        teScore.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        teScore.setPadding(60,60,30,60);
                                        teScore.setTextSize(20);
                                        teScore.setTypeface(face);
                                        teScore.setBackgroundColor(0xAAFFFFFF);
                                        teScore.setText("You got "+ m_Score+" questions correct out of " + myQuestion_Data.length+" questions");

                                        parent_layout.addView(greet);
                                        parent_layout.addView(greet_2);
                                        //parent_layout.addView(empty_layout_1,cp);
                                        parent_layout.addView(teScore);
                                        parent_layout.addView(btnshare);


                                        //progress_layout.addView(empty_layout,cp);
                                        Button btnfinish = new Button(getApplicationContext());
                                        btnfinish.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        btnfinish.setText("Next Quiz >");
                                        btnfinish.setTextColor(0xAAFFFFFF);
                                        btnfinish.setBackgroundColor(0xAAA6A6A6);
                                        progress_layout.addView(btnfinish);
                                        btnfinish.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                SharedPreferences.Editor editor = sp.edit();
                                                editor.clear();
                                                editor.commit();
                                                finish();
                                            }
                                        });
                                        btnshare.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                                                share.setType("text/plain");

                                                share.putExtra(Intent.EXTRA_SUBJECT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                        " questions\n Install GK App to keep your General Knowledge up to date");
                                                share.putExtra(Intent.EXTRA_TEXT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                        " questions\n Install GK App to keep your General Knowledge up to date "+"https://catking.in/gk-app");

                                                startActivity(Intent.createChooser(share, "Share your result with friends using"));
                                            }
                                        });
                                    }
                                    m_Question = myQuestion_Data[m_Index];
                                    mQuestionText_View.setText(m_Question);
                                    mQuestion_Number.setText("Question No: "+m_Qn);
                                    mQuestion_Number.setTextColor(0xAA000000);
                                    mQuestion_Number.setBackground(null);
                                    mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                                    mOption_A.setBackgroundColor(0xFFf2f2f2);
                                    mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                                    mOption_B.setBackgroundColor(0xFFf2f2f2);
                                    mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                                    mOption_C.setBackgroundColor(0xFFf2f2f2);
                                    mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                                    mOption_D.setBackgroundColor(0xFFf2f2f2);
                                    mOption_A.setEnabled(true);
                                    mOption_B.setEnabled(true);
                                    mOption_C.setEnabled(true);
                                    mOption_D.setEnabled(true);
                                    //mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100

                                    LinearLayout ll = findViewById(R.id.p_layout);
                                    LinearLayout mm = findViewById(R.id.quiz_layout);
                                    final LinearLayout child = (LinearLayout) ll.findViewById(R.id.button_layout);
                                    ll.removeView(child);
                                    final LinearLayout child2 = (LinearLayout) mm.findViewById(R.id.des_layout);
                                    mm.removeView(child2);
                                    final LinearLayout child3 = (LinearLayout) mm.findViewById(R.id.divider_layout);
                                    mm.removeView(child3);
                                    final LinearLayout child4 = (LinearLayout) ll.findViewById(R.id.emptyText_layout);
                                    ll.removeView(child4);

                                }

                                private void checkAnswer(String userSelection) {
                                    String correctAnswer = myA_Data[m_Index];
                                    boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                                    if(Aa== true){
                                        m_Score = m_Score+1;
                                        m_Count = (m_Count+1);
                                        Log.d("TFC_mcount", String.valueOf(m_Count));
                                        m_Qr = myQuestion_Data.length-m_Count;
                                        Log.d("TFC_mqr", String.valueOf(m_Qr));

                                        if(m_Qr==0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else if(m_Qr<0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else{
                                            mScoreText_View.setText(m_Qr+" more question to go.");
                                        }
                                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);


                                        mOption_C.setBackgroundColor(0xAA81c784);
                                        //mOption_A.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                                        mQuestion_Number.setText("Hurray!\n" + "You got it right");
                                        mQuestion_Number.setTextColor(0xAA385723);
                                        mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));


                                        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                                        LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                                        LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                                        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                                        LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        String des = myDescription_data[m_Index];
                                        String check ="";
                                        boolean de = check.equalsIgnoreCase(des);
                                        if(de == false){
                                            //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                            //parent_layout.addView(divider_layout,cp);

                                            quiz_layout.addView(description_layout,cp);
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
                                            teTag.setText(myDescription_data[m_Index]);
                                            description_layout.addView(teTag);
                                        }

                                        parent_layout.addView(activity_layout,cp);
                                        parent_layout.addView(emptyTextview_layout,cp);
                                        Button btnTag = new Button(getApplicationContext());
                                        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        if(m_Qr==0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else if(m_Qr<0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else{
                                            btnTag.setText("Next Question >");
                                        }

                                        SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                        m_Index = (m_Index + 1) % myQuestion_Data.length;
                                        m_Qn = m_Qn+1;
                                        editor.putInt(uniqueID+"_MINDEX", m_Index);
                                        editor.putInt(uniqueID+"_MQN", m_Qn);
                                        editor.putInt(uniqueID+"_MQR", m_Qr);
                                        editor.putInt(uniqueID+"_MSCORE", m_Score);
                                        editor.commit();

                                        btnTag.setTextColor(0xAAFFFFFF);
                                        btnTag.setBackgroundColor(0xAAA6A6A6);
                                        activity_layout.addView(btnTag);
                                        btnTag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                updateQuestion();
                                                Log.d("MCQC_updateq","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                            }
                                        });
                                    }else{
                                        m_Count = (m_Count+1);
                                        Log.d("TFC_mcount", String.valueOf(m_Count));
                                        m_Qr = myQuestion_Data.length-m_Count;
                                        Log.d("TFC_mqr", String.valueOf(m_Qr));

                                        if(m_Qr==0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else if(m_Qr<0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else{
                                            mScoreText_View.setText(m_Qr+" more question to go.");
                                        }
                                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);


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
                                        LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);

                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        String des = myDescription_data[m_Index];
                                        String check ="";
                                        boolean de = check.equalsIgnoreCase(des);
                                        if(de == false){
                                            //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                            //parent_layout.addView(divider_layout,cp);
//                                ImageView divider = new ImageView(getApplicationContext());
//                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                                lp.setMargins(0, 0, 0, 0);
//                                divider.setLayoutParams(lp);
//                                divider.setBackgroundColor(0xAAAAAAAA);
                                            quiz_layout.addView(description_layout,cp);
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
                                            teTag.setText(myDescription_data[m_Index]);
                                            description_layout.addView(teTag);
                                        }


                                        parent_layout.addView(activity_layout,cp);
                                        parent_layout.addView(emptyTextview_layout,cp);
                                        final Button btnTag = new Button(getApplicationContext());
                                        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        if(m_Qr==0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else if(m_Qr<0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else{
                                            btnTag.setText("Next Question >");
                                        }
                                        SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                        m_Index = (m_Index + 1) % myQuestion_Data.length;
                                        m_Qn = m_Qn+1;
                                        editor.putInt(uniqueID+"_MINDEX", m_Index);
                                        editor.putInt(uniqueID+"_MQN", m_Qn);
                                        editor.putInt(uniqueID+"_MQR", m_Qr);
                                        editor.commit();

                                        btnTag.setTextColor(0xAAFFFFFF);
                                        btnTag.setBackgroundColor(0xAAA6A6A6);
                                        activity_layout.addView(btnTag);
                                        btnTag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                btnTag.setEnabled(false);
                                                updateQuestion();
                                                Log.d("MCQC_updateQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
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
                                }
                                private void updateQuestion() {
                                    Log.d("MCQC_funUQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                    if(m_Index==0){
                                        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        LinearLayout empty_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                        LinearLayout empty_layout_1 =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                        //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                        LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bott);


                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        parent_layout.removeAllViewsInLayout();

                                        TextView greet = new TextView(getApplicationContext());
                                        greet.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        greet.setPadding(60,40,35,0);
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
//                            btnshare.setBackground(null);

                                        TextView teScore = new TextView(getApplicationContext());
                                        teScore.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        teScore.setPadding(60,60,30,60);
                                        teScore.setTextSize(20);
                                        teScore.setTypeface(face);
                                        teScore.setBackgroundColor(0xAAFFFFFF);
                                        teScore.setText("You got "+ m_Score+" questions correct out of " + myQuestion_Data.length+" questions");

                                        parent_layout.addView(greet);
                                        parent_layout.addView(greet_2);
                                        //parent_layout.addView(empty_layout_1,cp);
                                        parent_layout.addView(teScore);
                                        parent_layout.addView(btnshare);


                                        //progress_layout.addView(empty_layout,cp);
                                        Button btnfinish = new Button(getApplicationContext());
                                        btnfinish.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        btnfinish.setText("Next Quiz >");
                                        btnfinish.setTextColor(0xAAFFFFFF);
                                        btnfinish.setBackgroundColor(0xAAA6A6A6);
                                        progress_layout.addView(btnfinish);
                                        btnfinish.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                SharedPreferences.Editor editor = sp.edit();
                                                editor.clear();
                                                editor.commit();
                                                finish();
                                            }
                                        });
                                        btnshare.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                                                share.setType("text/plain");

                                                share.putExtra(Intent.EXTRA_SUBJECT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                        " questions\n Install GK App to keep your General Knowledge up to date");
                                                share.putExtra(Intent.EXTRA_TEXT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                        " questions\n Install GK App to keep your General Knowledge up to date "+"https://catking.in/gk-app");

                                                startActivity(Intent.createChooser(share, "Share your result with friends using"));
                                            }
                                        });
                                    }
                                    m_Question = myQuestion_Data[m_Index];
                                    mQuestionText_View.setText(m_Question);
                                    mQuestion_Number.setText("Question No: "+m_Qn);
                                    mQuestion_Number.setTextColor(0xAA000000);
                                    mQuestion_Number.setBackground(null);
                                    mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                                    mOption_A.setBackgroundColor(0xFFf2f2f2);
                                    mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                                    mOption_B.setBackgroundColor(0xFFf2f2f2);
                                    mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                                    mOption_C.setBackgroundColor(0xFFf2f2f2);
                                    mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                                    mOption_D.setBackgroundColor(0xFFf2f2f2);
                                    mOption_A.setEnabled(true);
                                    mOption_B.setEnabled(true);
                                    mOption_C.setEnabled(true);
                                    mOption_D.setEnabled(true);
                                    //mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100

                                    LinearLayout ll = findViewById(R.id.p_layout);
                                    LinearLayout mm = findViewById(R.id.quiz_layout);
                                    final LinearLayout child = (LinearLayout) ll.findViewById(R.id.button_layout);
                                    ll.removeView(child);
                                    final LinearLayout child2 = (LinearLayout) mm.findViewById(R.id.des_layout);
                                    mm.removeView(child2);
                                    final LinearLayout child3 = (LinearLayout) mm.findViewById(R.id.divider_layout);
                                    mm.removeView(child3);
                                    final LinearLayout child4 = (LinearLayout) ll.findViewById(R.id.emptyText_layout);
                                    ll.removeView(child4);
                                }

                                private void checkAnswer(String userSelection) {
                                    String correctAnswer = myA_Data[m_Index];
                                    boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                                    if(Aa== true){
                                        m_Score = m_Score+1;
                                        m_Count = (m_Count+1);
                                        Log.d("TFC_mcount", String.valueOf(m_Count));
                                        m_Qr = myQuestion_Data.length-m_Count;
                                        Log.d("TFC_mqr", String.valueOf(m_Qr));

                                        if(m_Qr==0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else if(m_Qr<0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else{
                                            mScoreText_View.setText(m_Qr+" more question to go.");
                                        }
                                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);


                                        mOption_D.setBackgroundColor(0xAA81c784);
                                        //mOption_D.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                                        mQuestion_Number.setText("Hurray!\n" + "You got it right");
                                        mQuestion_Number.setTextColor(0xAA385723);
                                        mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));


                                        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                                        LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                                        LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                                        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                                        LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        String des = myDescription_data[m_Index];
                                        String check ="";
                                        boolean de = check.equalsIgnoreCase(des);
                                        if(de == false){
                                            //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                            //parent_layout.addView(divider_layout,cp);

                                            quiz_layout.addView(description_layout,cp);
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
                                            teTag.setText(myDescription_data[m_Index]);
                                            description_layout.addView(teTag);
                                        }

                                        parent_layout.addView(activity_layout,cp);
                                        parent_layout.addView(emptyTextview_layout,cp);
                                        Button btnTag = new Button(getApplicationContext());
                                        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        if(m_Qr==0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else if(m_Qr<0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else{
                                            btnTag.setText("Next Question >");
                                        }

                                        SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                        m_Index = (m_Index + 1) % myQuestion_Data.length;
                                        m_Qn = m_Qn+1;
                                        editor.putInt(uniqueID+"_MINDEX", m_Index);
                                        editor.putInt(uniqueID+"_MQN", m_Qn);
                                        editor.putInt(uniqueID+"_MQR", m_Qr);
                                        editor.putInt(uniqueID+"_MSCORE", m_Score);
                                        editor.commit();

                                        btnTag.setTextColor(0xAAFFFFFF);
                                        btnTag.setBackgroundColor(0xAAA6A6A6);
                                        activity_layout.addView(btnTag);
                                        btnTag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Log.d("MCQC_updateq","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                                updateQuestion();
                                            }
                                        });
                                    }else{
                                        m_Count = (m_Count+1);
                                        Log.d("TFC_mcount", String.valueOf(m_Count));
                                        m_Qr = myQuestion_Data.length-m_Count;
                                        Log.d("TFC_mqr", String.valueOf(m_Qr));

                                        if(m_Qr==0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else if(m_Qr<0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else{
                                            mScoreText_View.setText(m_Qr+" more question to go.");
                                        }
                                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);


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
                                        LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);

                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        String des = myDescription_data[m_Index];
                                        String check ="";
                                        boolean de = check.equalsIgnoreCase(des);
                                        if(de == false){
                                            //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                            //parent_layout.addView(divider_layout,cp);
//                                ImageView divider = new ImageView(getApplicationContext());
//                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                                lp.setMargins(0, 0, 0, 0);
//                                divider.setLayoutParams(lp);
//                                divider.setBackgroundColor(0xAAAAAAAA);
                                            quiz_layout.addView(description_layout,cp);
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
                                            teTag.setText(myDescription_data[m_Index]);
                                            description_layout.addView(teTag);
                                        }


                                        parent_layout.addView(activity_layout,cp);
                                        parent_layout.addView(emptyTextview_layout,cp);
                                        final Button btnTag = new Button(getApplicationContext());
                                        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        if(m_Qr==0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else if(m_Qr<0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else{
                                            btnTag.setText("Next Question >");
                                        }

                                        SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                        m_Index = (m_Index + 1) % myQuestion_Data.length;
                                        m_Qn = m_Qn+1;
                                        editor.putInt(uniqueID+"_MINDEX", m_Index);
                                        editor.putInt(uniqueID+"_MQN", m_Qn);
                                        editor.putInt(uniqueID+"_MQR", m_Qr);
                                        editor.commit();

                                        btnTag.setTextColor(0xAAFFFFFF);
                                        btnTag.setBackgroundColor(0xAAA6A6A6);
                                        activity_layout.addView(btnTag);
                                        btnTag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                btnTag.setEnabled(false);
                                                updateQuestion();
                                                Log.d("MCQC_updateQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
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
                    });

                    mStartFresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            m_Index  = 0;
                            m_Score = 0;
                            m_Qn = 1;
                            m_Qr = 0;
                            m_Count = 0;
                            LinearLayout dd = findViewById(R.id.loaded_quiz);
                            dd.removeAllViews();

                            LinearLayout activity_stQ = (LinearLayout) inflater.inflate(R.layout.activity_quizcard_layout, null);
                            dd.addView(activity_stQ,de);

                            cardMCQ = (CardView)findViewById(R.id.cardview_mcq);
                            mOption_A = (Button) findViewById(R.id.button_option_A);
                            mOption_B = (Button) findViewById(R.id.button_option_B);
                            mOption_C = (Button) findViewById(R.id.button_option_C);
                            mOption_D = (Button) findViewById(R.id.button_option_D);
                            mQuestion_Number = findViewById(R.id.mcq_qn_view_card);
                            mQuestionText_View = findViewById(R.id.mcq_text_view_card);
                            mScoreText_View = findViewById(R.id.score_mcq_card);
                            mProgress_Bar = (ProgressBar) findViewById(R.id.progress_bar_mcq_card);
                            mProgress_Bar.setProgressTintList(ColorStateList.valueOf(0xAA92D050));
                            mProgress_Bar.setProgressBackgroundTintList(ColorStateList.valueOf(0xFFE9E6E6));
                            mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT*(m_Index));

                            m_Question = myQuestion_Data[m_Index];
                            mQuestionText_View.setText(m_Question);
                            mQuestion_Number.setText("Question No: "+m_Qn);
                            mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                            mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                            mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                            mOption_D.setText("D. "+myOptina_D_data[m_Index]);

                            if(m_Qr ==0 ){
                                m_Qr = (myQuestion_Data.length)-m_Index;
                                Log.d("MCQ_qr", String.valueOf(m_Qr));
                            }

                            mScoreText_View.setText(m_Qr+" question to go.");
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
                                    Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_Index+" mScore:"+m_Score+" mQn:"+m_Qn+" mQr:"+m_Qr+" mCount:"+m_Count);
                                }
                                private void updateQuestion() {
                                    Log.d("TFC_funUQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                    if(m_Index==0){
                                        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        LinearLayout empty_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                        LinearLayout empty_layout_1 =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                        //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                        LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bott);


                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        parent_layout.removeAllViewsInLayout();

                                        TextView greet = new TextView(getApplicationContext());
                                        greet.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        greet.setPadding(60,40,35,0);
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
                                        teScore.setText("You got "+ m_Score+" questions correct out of " + myQuestion_Data.length+" questions");

                                        parent_layout.addView(greet);
                                        parent_layout.addView(greet_2);
                                        parent_layout.addView(teScore);
                                        parent_layout.addView(btnshare);


                                        Button btnfinish = new Button(getApplicationContext());
                                        btnfinish.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        btnfinish.setText("Next Quiz >");
                                        btnfinish.setTextColor(0xAAFFFFFF);
                                        btnfinish.setBackgroundColor(0xAAA6A6A6);
                                        progress_layout.addView(btnfinish);
                                        btnfinish.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                SharedPreferences.Editor editor = sp.edit();
                                                editor.clear();
                                                editor.commit();
                                                finish();
                                            }
                                        });
                                        btnshare.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                                                share.setType("text/plain");

                                                share.putExtra(Intent.EXTRA_SUBJECT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                        " questions\n Install GK App to keep your General Knowledge up to date");
                                                share.putExtra(Intent.EXTRA_TEXT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                        " questions\n Install GK App to keep your General Knowledge up to date "+"https://catking.in/gk-app");

                                                startActivity(Intent.createChooser(share, "Share your result with friends using"));
                                            }
                                        });
                                    }
                                    m_Question = myQuestion_Data[m_Index];
                                    mQuestionText_View.setText(m_Question);
                                    mQuestion_Number.setText("Question No: "+m_Qn);
                                    mQuestion_Number.setTextColor(0xAA000000);
                                    mQuestion_Number.setBackground(null);
                                    mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                                    mOption_A.setBackgroundColor(0xFFf2f2f2);
                                    mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                                    mOption_B.setBackgroundColor(0xFFf2f2f2);
                                    mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                                    mOption_C.setBackgroundColor(0xFFf2f2f2);
                                    mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                                    mOption_D.setBackgroundColor(0xFFf2f2f2);
                                    mOption_A.setEnabled(true);
                                    mOption_B.setEnabled(true);
                                    mOption_C.setEnabled(true);
                                    mOption_D.setEnabled(true);

                                    LinearLayout ll = findViewById(R.id.p_layout);
                                    LinearLayout mm = findViewById(R.id.quiz_layout);
                                    final LinearLayout child = (LinearLayout) ll.findViewById(R.id.button_layout);
                                    ll.removeView(child);
                                    final LinearLayout child2 = (LinearLayout) mm.findViewById(R.id.des_layout);
                                    mm.removeView(child2);
                                    final LinearLayout child3 = (LinearLayout) mm.findViewById(R.id.divider_layout);
                                    mm.removeView(child3);
                                    final LinearLayout child4 = (LinearLayout) ll.findViewById(R.id.emptyText_layout);
                                    ll.removeView(child4);
                                }

                                private void checkAnswer(String userSelection) {
                                    String correctAnswer = myA_Data[m_Index];
                                    boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                                    if(Aa== true){
                                        m_Score = m_Score+1;
                                        m_Count = (m_Count+1);
                                        m_Qr = (myQuestion_Data.length)-m_Count;

                                        if(m_Qr==0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else if(m_Qr<0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else{
                                            mScoreText_View.setText(m_Qr+" more question to go.");
                                        }

                                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
                                        mOption_A.setBackgroundColor(0xAA81c784);
                                        //mOption_A.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                                        mQuestion_Number.setText("Hurray!\n" + "You got it right");
                                        mQuestion_Number.setTextColor(0xAA385723);
                                        mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));

                                        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                                        LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                                        LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                                        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                                        LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        String des = myDescription_data[m_Index];
                                        String check ="";
                                        boolean de = check.equalsIgnoreCase(des);
                                        if(de == false){
                                            //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                            //quiz_layout.addView(divider_layout,cp);
                                            quiz_layout.addView(description_layout,cp);
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
                                            teTag.setText(myDescription_data[m_Index]);
                                            description_layout.addView(teTag);
                                        }

                                        parent_layout.addView(activity_layout,cp);
                                        parent_layout.addView(emptyTextview_layout,cp);
                                        Button btnTag = new Button(getApplicationContext());
                                        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        if(m_Qr==0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else if(m_Qr<0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else{
                                            btnTag.setText("Next Question >");
                                        }

                                        SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                        m_Index = (m_Index + 1) % myQuestion_Data.length;
                                        m_Qn = m_Qn+1;
                                        editor.putInt(uniqueID+"_MINDEX", m_Index);
                                        editor.putInt(uniqueID+"_MQN", m_Qn);
                                        editor.putInt(uniqueID+"_MQR", m_Qr);
                                        editor.putInt(uniqueID+"_MSCORE", m_Score);
                                        editor.commit();

                                        btnTag.setTextColor(0xAAFFFFFF);
                                        btnTag.setBackgroundColor(0xAAA6A6A6);
                                        activity_layout.addView(btnTag);
                                        btnTag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                updateQuestion();
                                                Log.d("MCQC_updateq","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                            }
                                        });
                                    }else{
                                        m_Count = (m_Count+1);
                                        Log.d("MCQC_mcount", String.valueOf(m_Count));
                                        m_Qr = myQuestion_Data.length-m_Count;
                                        Log.d("MCQC_mqr", String.valueOf(m_Qr));

                                        if(m_Qr==0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else if(m_Qr<0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else{
                                            mScoreText_View.setText(m_Qr+" more question to go.");
                                        }
                                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
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
                                        LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);

                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        String des = myDescription_data[m_Index];
                                        String check ="";
                                        boolean de = check.equalsIgnoreCase(des);
                                        if(de == false){
                                            //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                            //quiz_layout.addView(divider_layout,cp);
                                            quiz_layout.addView(description_layout,cp);
                                            TextView teTag = new TextView(getApplicationContext());
                                            teTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                            teTag.setPadding(70,5,40,5);
                                            teTag.setTextSize(18);
                                            teTag.setVerticalScrollBarEnabled(true);
                                            teTag.setMaxLines(4);
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
                                            teTag.setText(myDescription_data[m_Index]);
                                            description_layout.addView(teTag);
                                        }


                                        parent_layout.addView(activity_layout,cp);
                                        parent_layout.addView(emptyTextview_layout,cp);
                                        final Button btnTag = new Button(getApplicationContext());
                                        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        if(m_Qr==0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else if(m_Qr<0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else{
                                            btnTag.setText("Next Question >");
                                        }

                                        SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                        m_Index = (m_Index + 1) % myQuestion_Data.length;
                                        m_Qn = m_Qn+1;
                                        editor.putInt(uniqueID+"_MINDEX", m_Index);
                                        editor.putInt(uniqueID+"_MQN", m_Qn);
                                        editor.putInt(uniqueID+"_MQR", m_Qr);
                                        editor.commit();

                                        btnTag.setTextColor(0xAAFFFFFF);
                                        btnTag.setBackgroundColor(0xAAA6A6A6);
                                        activity_layout.addView(btnTag);
                                        btnTag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                btnTag.setEnabled(false);
                                                updateQuestion();
                                                Log.d("TFC_updateQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
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
                                }
                                private void updateQuestion() {
                                    Log.d("MCQC_funUQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                    if(m_Index==0){
                                        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        LinearLayout empty_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                        LinearLayout empty_layout_1 =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                        //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                        LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bott);


                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        parent_layout.removeAllViewsInLayout();

                                        TextView greet = new TextView(getApplicationContext());
                                        greet.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        greet.setPadding(60,40,35,0);
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
//                            btnshare.setBackground(null);

                                        TextView teScore = new TextView(getApplicationContext());
                                        teScore.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        teScore.setPadding(60,60,30,60);
                                        teScore.setTextSize(20);
                                        teScore.setTypeface(face);
                                        teScore.setBackgroundColor(0xAAFFFFFF);
                                        teScore.setText("You got "+ m_Score+" questions correct out of " + myQuestion_Data.length+" questions");

                                        parent_layout.addView(greet);
                                        parent_layout.addView(greet_2);
                                        //parent_layout.addView(empty_layout_1,cp);
                                        parent_layout.addView(teScore);
                                        parent_layout.addView(btnshare);


                                        //progress_layout.addView(empty_layout,cp);
                                        Button btnfinish = new Button(getApplicationContext());
                                        btnfinish.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        btnfinish.setText("Next Quiz >");
                                        btnfinish.setTextColor(0xAAFFFFFF);
                                        btnfinish.setBackgroundColor(0xAAA6A6A6);
                                        progress_layout.addView(btnfinish);
                                        btnfinish.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                SharedPreferences.Editor editor = sp.edit();
                                                editor.clear();
                                                editor.commit();
                                                finish();
                                            }
                                        });
                                        btnshare.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                                                share.setType("text/plain");

                                                share.putExtra(Intent.EXTRA_SUBJECT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                        " questions\n Install GK App to keep your General Knowledge up to date");
                                                share.putExtra(Intent.EXTRA_TEXT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                        " questions\n Install GK App to keep your General Knowledge up to date "+"https://catking.in/gk-app");

                                                startActivity(Intent.createChooser(share, "Share your result with friends using"));
                                            }
                                        });
                                    }
                                    m_Question = myQuestion_Data[m_Index];
                                    mQuestionText_View.setText(m_Question);
                                    mQuestion_Number.setText("Question No: "+m_Qn);
                                    mQuestion_Number.setTextColor(0xAA000000);
                                    mQuestion_Number.setBackground(null);
                                    mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                                    mOption_A.setBackgroundColor(0xFFf2f2f2);
                                    mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                                    mOption_B.setBackgroundColor(0xFFf2f2f2);
                                    mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                                    mOption_C.setBackgroundColor(0xFFf2f2f2);
                                    mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                                    mOption_D.setBackgroundColor(0xFFf2f2f2);
                                    mOption_A.setEnabled(true);
                                    mOption_B.setEnabled(true);
                                    mOption_C.setEnabled(true);
                                    mOption_D.setEnabled(true);
                                    //mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100

                                    LinearLayout ll = findViewById(R.id.p_layout);
                                    LinearLayout mm = findViewById(R.id.quiz_layout);
                                    final LinearLayout child = (LinearLayout) ll.findViewById(R.id.button_layout);
                                    ll.removeView(child);
                                    final LinearLayout child2 = (LinearLayout) mm.findViewById(R.id.des_layout);
                                    mm.removeView(child2);
                                    final LinearLayout child3 = (LinearLayout) mm.findViewById(R.id.divider_layout);
                                    mm.removeView(child3);
                                    final LinearLayout child4 = (LinearLayout) ll.findViewById(R.id.emptyText_layout);
                                    ll.removeView(child4);
                                }

                                private void checkAnswer(String userSelection) {
                                    String correctAnswer = myA_Data[m_Index];
                                    boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                                    if(Aa== true){
                                        m_Score = m_Score+1;
                                        m_Count = (m_Count+1);
                                        m_Qr = (myQuestion_Data.length)-m_Count;

                                        if(m_Qr==0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else if(m_Qr<0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else{
                                            mScoreText_View.setText(m_Qr+" more question to go.");
                                        }

                                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
                                        mOption_B.setBackgroundColor(0xAA81c784);
                                        mQuestion_Number.setText("Hurray!\n" + "You got it right");
                                        mQuestion_Number.setTextColor(0xAA385723);
                                        mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));

                                        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                                        LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                                        LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                                        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                                        LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        String des = myDescription_data[m_Index];
                                        String check ="";
                                        boolean de = check.equalsIgnoreCase(des);
                                        if(de == false){
                                            //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                            //quiz_layout.addView(divider_layout,cp);
                                            quiz_layout.addView(description_layout,cp);
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
                                            teTag.setText(myDescription_data[m_Index]);
                                            description_layout.addView(teTag);
                                        }

                                        parent_layout.addView(activity_layout,cp);
                                        parent_layout.addView(emptyTextview_layout,cp);
                                        Button btnTag = new Button(getApplicationContext());
                                        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        if(m_Qr==0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else if(m_Qr<0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else{
                                            btnTag.setText("Next Question >");
                                        }

                                        SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                        m_Index = (m_Index + 1) % myQuestion_Data.length;
                                        m_Qn = m_Qn+1;
                                        editor.putInt(uniqueID+"_MINDEX", m_Index);
                                        editor.putInt(uniqueID+"_MQN", m_Qn);
                                        editor.putInt(uniqueID+"_MQR", m_Qr);
                                        editor.putInt(uniqueID+"_MSCORE", m_Score);
                                        editor.commit();

                                        btnTag.setTextColor(0xAAFFFFFF);
                                        btnTag.setBackgroundColor(0xAAA6A6A6);
                                        activity_layout.addView(btnTag);
                                        btnTag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                updateQuestion();
                                                Log.d("MCQC_updateq","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                            }
                                        });
                                    }else{
                                        m_Count = (m_Count+1);
                                        Log.d("MCQC_mcount", String.valueOf(m_Count));
                                        m_Qr = myQuestion_Data.length-m_Count;
                                        Log.d("MCQC_mqr", String.valueOf(m_Qr));

                                        if(m_Qr==0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else if(m_Qr<0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else{
                                            mScoreText_View.setText(m_Qr+" more question to go.");
                                        }
                                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
                                        mOption_B.setBackgroundColor(0xAAe57272);
                                        mQuestion_Number.setText("Oops!\n" + "You got it wrong");
                                        mQuestion_Number.setTextColor(0xAAFFFFFF);
                                        mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_false));
                                        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                                        LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                                        LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                                        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                                        LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);

                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        String des = myDescription_data[m_Index];
                                        String check ="";
                                        boolean de = check.equalsIgnoreCase(des);
                                        if(de == false){
                                            quiz_layout.addView(description_layout,cp);
                                            TextView teTag = new TextView(getApplicationContext());
                                            teTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                            teTag.setPadding(70,5,40,5);
                                            teTag.setTextSize(18);
                                            teTag.setVerticalScrollBarEnabled(true);
                                            teTag.setMaxLines(4);
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
                                            teTag.setText(myDescription_data[m_Index]);
                                            description_layout.addView(teTag);
                                        }


                                        parent_layout.addView(activity_layout,cp);
                                        parent_layout.addView(emptyTextview_layout,cp);
                                        final Button btnTag = new Button(getApplicationContext());
                                        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        if(m_Qr==0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else if(m_Qr<0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else{
                                            btnTag.setText("Next Question >");
                                        }

                                        SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                        m_Index = (m_Index + 1) % myQuestion_Data.length;
                                        m_Qn = m_Qn+1;
                                        editor.putInt(uniqueID+"_MINDEX", m_Index);
                                        editor.putInt(uniqueID+"_MQN", m_Qn);
                                        editor.putInt(uniqueID+"_MQR", m_Qr);
                                        editor.commit();

                                        btnTag.setTextColor(0xAAFFFFFF);
                                        btnTag.setBackgroundColor(0xAAA6A6A6);
                                        activity_layout.addView(btnTag);
                                        btnTag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                btnTag.setEnabled(false);
                                                updateQuestion();
                                                Log.d("TFC_updateQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
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
                                }
                                private void updateQuestion() {
                                    Log.d("MCQC_funUQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                    if(m_Index==0){
                                        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        LinearLayout empty_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                        LinearLayout empty_layout_1 =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                        //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                        LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bott);


                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        parent_layout.removeAllViewsInLayout();

                                        TextView greet = new TextView(getApplicationContext());
                                        greet.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        greet.setPadding(60,40,35,0);
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
//                            btnshare.setBackground(null);

                                        TextView teScore = new TextView(getApplicationContext());
                                        teScore.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        teScore.setPadding(60,60,30,60);
                                        teScore.setTextSize(20);
                                        teScore.setTypeface(face);
                                        teScore.setBackgroundColor(0xAAFFFFFF);
                                        teScore.setText("You got "+ m_Score+" questions correct out of " + myQuestion_Data.length+" questions");

                                        parent_layout.addView(greet);
                                        parent_layout.addView(greet_2);
                                        //parent_layout.addView(empty_layout_1,cp);
                                        parent_layout.addView(teScore);
                                        parent_layout.addView(btnshare);


                                        //progress_layout.addView(empty_layout,cp);
                                        Button btnfinish = new Button(getApplicationContext());
                                        btnfinish.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        btnfinish.setText("Next Quiz >");
                                        btnfinish.setTextColor(0xAAFFFFFF);
                                        btnfinish.setBackgroundColor(0xAAA6A6A6);
                                        progress_layout.addView(btnfinish);
                                        btnfinish.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                SharedPreferences.Editor editor = sp.edit();
                                                editor.clear();
                                                editor.commit();
                                                finish();
                                            }
                                        });
                                        btnshare.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                                                share.setType("text/plain");

                                                share.putExtra(Intent.EXTRA_SUBJECT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                        " questions\n Install GK App to keep your General Knowledge up to date");
                                                share.putExtra(Intent.EXTRA_TEXT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                        " questions\n Install GK App to keep your General Knowledge up to date "+"https://catking.in/gk-app");

                                                startActivity(Intent.createChooser(share, "Share your result with friends using"));
                                            }
                                        });
                                    }
                                    m_Question = myQuestion_Data[m_Index];
                                    mQuestionText_View.setText(m_Question);
                                    mQuestion_Number.setText("Question No: "+m_Qn);
                                    mQuestion_Number.setTextColor(0xAA000000);
                                    mQuestion_Number.setBackground(null);
                                    mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                                    mOption_A.setBackgroundColor(0xFFf2f2f2);
                                    mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                                    mOption_B.setBackgroundColor(0xFFf2f2f2);
                                    mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                                    mOption_C.setBackgroundColor(0xFFf2f2f2);
                                    mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                                    mOption_D.setBackgroundColor(0xFFf2f2f2);
                                    mOption_A.setEnabled(true);
                                    mOption_B.setEnabled(true);
                                    mOption_C.setEnabled(true);
                                    mOption_D.setEnabled(true);
                                    //mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100

                                    LinearLayout ll = findViewById(R.id.p_layout);
                                    LinearLayout mm = findViewById(R.id.quiz_layout);
                                    final LinearLayout child = (LinearLayout) ll.findViewById(R.id.button_layout);
                                    ll.removeView(child);
                                    final LinearLayout child2 = (LinearLayout) mm.findViewById(R.id.des_layout);
                                    mm.removeView(child2);
                                    final LinearLayout child3 = (LinearLayout) mm.findViewById(R.id.divider_layout);
                                    mm.removeView(child3);
                                    final LinearLayout child4 = (LinearLayout) ll.findViewById(R.id.emptyText_layout);
                                    ll.removeView(child4);

                                }

                                private void checkAnswer(String userSelection) {
                                    String correctAnswer = myA_Data[m_Index];
                                    boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                                    if(Aa== true){
                                        m_Score = m_Score+1;
                                        m_Count = (m_Count+1);
                                        Log.d("TFC_mcount", String.valueOf(m_Count));
                                        m_Qr = myQuestion_Data.length-m_Count;
                                        Log.d("TFC_mqr", String.valueOf(m_Qr));

                                        if(m_Qr==0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else if(m_Qr<0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else{
                                            mScoreText_View.setText(m_Qr+" more question to go.");
                                        }
                                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);


                                        mOption_C.setBackgroundColor(0xAA81c784);
                                        //mOption_A.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                                        mQuestion_Number.setText("Hurray!\n" + "You got it right");
                                        mQuestion_Number.setTextColor(0xAA385723);
                                        mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));


                                        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                                        LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                                        LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                                        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                                        LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        String des = myDescription_data[m_Index];
                                        String check ="";
                                        boolean de = check.equalsIgnoreCase(des);
                                        if(de == false){
                                            //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                            //parent_layout.addView(divider_layout,cp);

                                            quiz_layout.addView(description_layout,cp);
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
                                            teTag.setText(myDescription_data[m_Index]);
                                            description_layout.addView(teTag);
                                        }

                                        parent_layout.addView(activity_layout,cp);
                                        parent_layout.addView(emptyTextview_layout,cp);
                                        Button btnTag = new Button(getApplicationContext());
                                        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        if(m_Qr==0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else if(m_Qr<0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else{
                                            btnTag.setText("Next Question >");
                                        }

                                        SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                        m_Index = (m_Index + 1) % myQuestion_Data.length;
                                        m_Qn = m_Qn+1;
                                        editor.putInt(uniqueID+"_MINDEX", m_Index);
                                        editor.putInt(uniqueID+"_MQN", m_Qn);
                                        editor.putInt(uniqueID+"_MQR", m_Qr);
                                        editor.putInt(uniqueID+"_MSCORE", m_Score);
                                        editor.commit();

                                        btnTag.setTextColor(0xAAFFFFFF);
                                        btnTag.setBackgroundColor(0xAAA6A6A6);
                                        activity_layout.addView(btnTag);
                                        btnTag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                updateQuestion();
                                                Log.d("MCQC_updateq","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                            }
                                        });
                                    }else{
                                        m_Count = (m_Count+1);
                                        Log.d("TFC_mcount", String.valueOf(m_Count));
                                        m_Qr = myQuestion_Data.length-m_Count;
                                        Log.d("TFC_mqr", String.valueOf(m_Qr));

                                        if(m_Qr==0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else if(m_Qr<0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else{
                                            mScoreText_View.setText(m_Qr+" more question to go.");
                                        }
                                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);


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
                                        LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);

                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        String des = myDescription_data[m_Index];
                                        String check ="";
                                        boolean de = check.equalsIgnoreCase(des);
                                        if(de == false){
                                            //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                            //parent_layout.addView(divider_layout,cp);
//                                ImageView divider = new ImageView(getApplicationContext());
//                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                                lp.setMargins(0, 0, 0, 0);
//                                divider.setLayoutParams(lp);
//                                divider.setBackgroundColor(0xAAAAAAAA);
                                            quiz_layout.addView(description_layout,cp);
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
                                            teTag.setText(myDescription_data[m_Index]);
                                            description_layout.addView(teTag);
                                        }


                                        parent_layout.addView(activity_layout,cp);
                                        parent_layout.addView(emptyTextview_layout,cp);
                                        final Button btnTag = new Button(getApplicationContext());
                                        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        if(m_Qr==0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else if(m_Qr<0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else{
                                            btnTag.setText("Next Question >");
                                        }
                                        SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                        m_Index = (m_Index + 1) % myQuestion_Data.length;
                                        m_Qn = m_Qn+1;
                                        editor.putInt(uniqueID+"_MINDEX", m_Index);
                                        editor.putInt(uniqueID+"_MQN", m_Qn);
                                        editor.putInt(uniqueID+"_MQR", m_Qr);
                                        editor.commit();

                                        btnTag.setTextColor(0xAAFFFFFF);
                                        btnTag.setBackgroundColor(0xAAA6A6A6);
                                        activity_layout.addView(btnTag);
                                        btnTag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                btnTag.setEnabled(false);
                                                updateQuestion();
                                                Log.d("MCQC_updateQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
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
                                }
                                private void updateQuestion() {
                                    Log.d("MCQC_funUQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                    if(m_Index==0){
                                        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        LinearLayout empty_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                        LinearLayout empty_layout_1 =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);
                                        //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                        LinearLayout progress_layout = (LinearLayout) findViewById(R.id.bott);


                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        parent_layout.removeAllViewsInLayout();

                                        TextView greet = new TextView(getApplicationContext());
                                        greet.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        greet.setPadding(60,40,35,0);
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
//                            btnshare.setBackground(null);

                                        TextView teScore = new TextView(getApplicationContext());
                                        teScore.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        teScore.setPadding(60,60,30,60);
                                        teScore.setTextSize(20);
                                        teScore.setTypeface(face);
                                        teScore.setBackgroundColor(0xAAFFFFFF);
                                        teScore.setText("You got "+ m_Score+" questions correct out of " + myQuestion_Data.length+" questions");

                                        parent_layout.addView(greet);
                                        parent_layout.addView(greet_2);
                                        //parent_layout.addView(empty_layout_1,cp);
                                        parent_layout.addView(teScore);
                                        parent_layout.addView(btnshare);


                                        //progress_layout.addView(empty_layout,cp);
                                        Button btnfinish = new Button(getApplicationContext());
                                        btnfinish.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        btnfinish.setText("Next Quiz >");
                                        btnfinish.setTextColor(0xAAFFFFFF);
                                        btnfinish.setBackgroundColor(0xAAA6A6A6);
                                        progress_layout.addView(btnfinish);
                                        btnfinish.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                SharedPreferences.Editor editor = sp.edit();
                                                editor.clear();
                                                editor.commit();
                                                finish();
                                            }
                                        });
                                        btnshare.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                                                share.setType("text/plain");

                                                share.putExtra(Intent.EXTRA_SUBJECT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                        " questions\n Install GK App to keep your General Knowledge up to date");
                                                share.putExtra(Intent.EXTRA_TEXT, "I got "+m_Score+" questions correct out of "+myQuestion_Data.length+
                                                        " questions\n Install GK App to keep your General Knowledge up to date "+"https://catking.in/gk-app");

                                                startActivity(Intent.createChooser(share, "Share your result with friends using"));
                                            }
                                        });
                                    }
                                    m_Question = myQuestion_Data[m_Index];
                                    mQuestionText_View.setText(m_Question);
                                    mQuestion_Number.setText("Question No: "+m_Qn);
                                    mQuestion_Number.setTextColor(0xAA000000);
                                    mQuestion_Number.setBackground(null);
                                    mOption_A.setText("A. "+myOptina_A_data[m_Index]);
                                    mOption_A.setBackgroundColor(0xFFf2f2f2);
                                    mOption_B.setText("B. "+myOptina_B_data[m_Index]);
                                    mOption_B.setBackgroundColor(0xFFf2f2f2);
                                    mOption_C.setText("C. "+myOptina_C_data[m_Index]);
                                    mOption_C.setBackgroundColor(0xFFf2f2f2);
                                    mOption_D.setText("D. "+myOptina_D_data[m_Index]);
                                    mOption_D.setBackgroundColor(0xFFf2f2f2);
                                    mOption_A.setEnabled(true);
                                    mOption_B.setEnabled(true);
                                    mOption_C.setEnabled(true);
                                    mOption_D.setEnabled(true);
                                    //mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100

                                    LinearLayout ll = findViewById(R.id.p_layout);
                                    LinearLayout mm = findViewById(R.id.quiz_layout);
                                    final LinearLayout child = (LinearLayout) ll.findViewById(R.id.button_layout);
                                    ll.removeView(child);
                                    final LinearLayout child2 = (LinearLayout) mm.findViewById(R.id.des_layout);
                                    mm.removeView(child2);
                                    final LinearLayout child3 = (LinearLayout) mm.findViewById(R.id.divider_layout);
                                    mm.removeView(child3);
                                    final LinearLayout child4 = (LinearLayout) ll.findViewById(R.id.emptyText_layout);
                                    ll.removeView(child4);
                                }

                                private void checkAnswer(String userSelection) {
                                    String correctAnswer = myA_Data[m_Index];
                                    boolean Aa = correctAnswer.equalsIgnoreCase(userSelection);
                                    if(Aa== true){
                                        m_Score = m_Score+1;
                                        m_Count = (m_Count+1);
                                        Log.d("TFC_mcount", String.valueOf(m_Count));
                                        m_Qr = myQuestion_Data.length-m_Count;
                                        Log.d("TFC_mqr", String.valueOf(m_Qr));

                                        if(m_Qr==0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else if(m_Qr<0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else{
                                            mScoreText_View.setText(m_Qr+" more question to go.");
                                        }
                                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);


                                        mOption_D.setBackgroundColor(0xAA81c784);
                                        //mOption_D.setBackground(getResources().getDrawable(R.drawable.text_container_true));
                                        mQuestion_Number.setText("Hurray!\n" + "You got it right");
                                        mQuestion_Number.setTextColor(0xAA385723);
                                        mQuestion_Number.setBackground(getResources().getDrawable(R.drawable.text_container_true));


                                        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        LinearLayout activity_layout = (LinearLayout) inflater.inflate(R.layout.add_extra_layout, null);
                                        LinearLayout description_layout = (LinearLayout) inflater.inflate(R.layout.add_des_layout, null);
                                        LinearLayout emptyTextview_layout =(LinearLayout) inflater.inflate(R.layout.add_empty_textview, null);

                                        LinearLayout parent_layout = (LinearLayout) findViewById(R.id.p_layout);
                                        LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);
                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                        String des = myDescription_data[m_Index];
                                        String check ="";
                                        boolean de = check.equalsIgnoreCase(des);
                                        if(de == false){
                                            //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                            //parent_layout.addView(divider_layout,cp);

                                            quiz_layout.addView(description_layout,cp);
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
                                            teTag.setText(myDescription_data[m_Index]);
                                            description_layout.addView(teTag);
                                        }

                                        parent_layout.addView(activity_layout,cp);
                                        parent_layout.addView(emptyTextview_layout,cp);
                                        Button btnTag = new Button(getApplicationContext());
                                        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        if(m_Qr==0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else if(m_Qr<0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else{
                                            btnTag.setText("Next Question >");
                                        }

                                        SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                        m_Index = (m_Index + 1) % myQuestion_Data.length;
                                        m_Qn = m_Qn+1;
                                        editor.putInt(uniqueID+"_MINDEX", m_Index);
                                        editor.putInt(uniqueID+"_MQN", m_Qn);
                                        editor.putInt(uniqueID+"_MQR", m_Qr);
                                        editor.putInt(uniqueID+"_MSCORE", m_Score);
                                        editor.commit();

                                        btnTag.setTextColor(0xAAFFFFFF);
                                        btnTag.setBackgroundColor(0xAAA6A6A6);
                                        activity_layout.addView(btnTag);
                                        btnTag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Log.d("MCQC_updateq","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
                                                updateQuestion();
                                            }
                                        });
                                    }else{
                                        m_Count = (m_Count+1);
                                        Log.d("TFC_mcount", String.valueOf(m_Count));
                                        m_Qr = myQuestion_Data.length-m_Count;
                                        Log.d("TFC_mqr", String.valueOf(m_Qr));

                                        if(m_Qr==0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else if(m_Qr<0){
                                            mScoreText_View.setText("Hurray 100% progress. You are done!");
                                        }else{
                                            mScoreText_View.setText(m_Qr+" more question to go.");
                                        }
                                        mProgress_Bar.incrementProgressBy(PROGRESS_BAR_INCREMENT);


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
                                        LinearLayout quiz_layout = (LinearLayout) findViewById(R.id.quiz_layout);

                                        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        String des = myDescription_data[m_Index];
                                        String check ="";
                                        boolean de = check.equalsIgnoreCase(des);
                                        if(de == false){
                                            //LinearLayout divider_layout = (LinearLayout)inflater.inflate(R.layout.add_divider_layout,null);
                                            //parent_layout.addView(divider_layout,cp);
//                                ImageView divider = new ImageView(getApplicationContext());
//                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                                lp.setMargins(0, 0, 0, 0);
//                                divider.setLayoutParams(lp);
//                                divider.setBackgroundColor(0xAAAAAAAA);
                                            quiz_layout.addView(description_layout,cp);
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
                                            teTag.setText(myDescription_data[m_Index]);
                                            description_layout.addView(teTag);
                                        }


                                        parent_layout.addView(activity_layout,cp);
                                        parent_layout.addView(emptyTextview_layout,cp);
                                        final Button btnTag = new Button(getApplicationContext());
                                        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        if(m_Qr==0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else if(m_Qr<0){
                                            btnTag.setText("Finish Quiz >");
                                            SharedPreferences dd = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                            int m_I  = dd.getInt(uniqueID+"_MINDEX", 0);
                                            int m_S = dd.getInt(uniqueID+"_MSCORE",0);
                                            int m_Q = dd.getInt(uniqueID+"_MQN",1);
                                            int m_QRRR = dd.getInt(uniqueID+"_MQR",0);
                                            int m_C = dd.getInt(uniqueID+"_MCOUNT",0);
                                            Log.d("MCQC_cleared","cleared shared prefs to mIndex:"+m_I+" mScore:"+m_S+" mQn:"+m_Q+" mQr:"+m_QRRR+" mCount:"+m_C);
                                        }else{
                                            btnTag.setText("Next Question >");
                                        }

                                        SharedPreferences sp = getSharedPreferences("MCQquizProgress", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putInt(uniqueID+"_MCOUNT",m_Count);
                                        m_Index = (m_Index + 1) % myQuestion_Data.length;
                                        m_Qn = m_Qn+1;
                                        editor.putInt(uniqueID+"_MINDEX", m_Index);
                                        editor.putInt(uniqueID+"_MQN", m_Qn);
                                        editor.putInt(uniqueID+"_MQR", m_Qr);
                                        editor.commit();

                                        btnTag.setTextColor(0xAAFFFFFF);
                                        btnTag.setBackgroundColor(0xAAA6A6A6);
                                        activity_layout.addView(btnTag);
                                        btnTag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                btnTag.setEnabled(false);
                                                updateQuestion();
                                                Log.d("MCQC_updateQ","mIndex:"+m_Index+" mScore:"+m_Score+" mQr:"+m_Qr+" mQn:"+m_Qn+" mCount:"+m_Count);
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
                    });


                }
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

        View navFooter1 = findViewById(R.id.imageButton_f);
        navFooter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo("com.facebook.katana", 0);
                    if (applicationInfo.enabled) {
                        // http://stackoverflow.com/a/24547437/1048340
                        Uri uri = Uri.parse("fb://facewebmodal/f?href=" + "https://www.facebook.com/rahulcatking/");
                        Intent i = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(i);
                    }
                } catch (PackageManager.NameNotFoundException ignored) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/rahulcatking/")));
                }
                //newFacebookIntent(getApplicationContext().getPackageManager(),"https://www.facebook.com/rahulcatking/");
            }
        });

        View navFooter2 = findViewById(R.id.imageButton_i);
        navFooter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/_u/rahul_catking");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/rahul_catking")));
                }
            }
        });


        View navFooter3 = findViewById(R.id.imageButton_y);
        navFooter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + "k5PwQ1n2x4U"));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/channel/UC4eEFUtZeW6iOqH8e9e0CyQ"));
                try {
                    startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
            }
        });

        View navFooter4 = findViewById(R.id.imageButton_q);
        navFooter4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.quora.com/profile/Rahul-Singh-6180"));
                startActivity(webIntent);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        ImageView nav_Head = (ImageView) headerview.findViewById(R.id.nav_head_image);
        nav_Head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class );
                startActivity(intent);
            }
        });


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
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ScoreKey", m_Score);
        outState.putInt("IndexKey",m_Index);
        outState.putInt("Question_Number",m_Qn);
    }

    private void prepareMenuData() {

        MenuModel menuModel = new MenuModel("MBA GK", true, true, new activity_coming_soon());
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("SNAP", false, false, new snap_sa());
        childModelsList.add(childModel);

        childModel = new MenuModel("XAT", false, false,new xat_sa());
        childModelsList.add(childModel);

        childModel = new MenuModel("MICAT", false, false,new miCat_sa());
        childModelsList.add(childModel);

        childModel = new MenuModel("IIFT", false, false, new iift_sa());
        childModelsList.add(childModel);

        childModel = new MenuModel("CMAT", false, false, new cmat_sa());
        childModelsList.add(childModel);

        childModel = new MenuModel("MAT", false, false, new mat_sa());
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }


        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("RRB GK", true, true, new activity_coming_soon());
        headerList.add(menuModel);
        childModel = new MenuModel("RRB Officer Scale", false, false, new rrb_os_sa());
        childModelsList.add(childModel);

        childModel = new MenuModel("RRB Office Assistant", false, false, new rrb_oa_sa());
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("IBPS GK", true, true, new activity_coming_soon());
        headerList.add(menuModel);
        childModel = new MenuModel("IBPS PO", false, false, new ibps_po_sa());
        childModelsList.add(childModel);

        childModel = new MenuModel("IBPS Clerk", false, false, new ibps_clerk_sa());
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("RBI GK", true, true, new activity_coming_soon());
        headerList.add(menuModel);
        childModel = new MenuModel("RBI Grade B Officer", false, false, new rbi_gbo_sa());
        childModelsList.add(childModel);

        childModel = new MenuModel("RBI Office Assistant", false, false, new rbi_oa_sa());
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("SBI GK", true, true, new activity_coming_soon());
        headerList.add(menuModel);
        childModel = new MenuModel("SBI PO", false, false, new sbi_po_sa());
        childModelsList.add(childModel);

        childModel = new MenuModel("SBI Clerk", false, false, new sbi_clerk_sa());
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }


        menuModel = new MenuModel("Statick GK", true, false, new staticGK_sa()); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Current Affairs", true, false, new activity_coming_soon()); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Buy GK Course", true, false, new buy_gk_course()); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                        Intent intentC = new Intent(getApplicationContext(),headerList.get(groupPosition).activity.getClass());
                        startActivity(intentC);
                        onBackPressed();
                    }
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    Intent intentC = new Intent(getApplicationContext(),model.activity.getClass());
                    startActivity(intentC);
                    //webView.loadUrl(model.url);
                    onBackPressed();
                }

                return false;
            }
        });
    }
}