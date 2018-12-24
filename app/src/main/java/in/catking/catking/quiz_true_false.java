package in.catking.catking;

//Vishal Raut
//Email me on vr.iitb@gmail.com if you come across any problem
import android.util.Log;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
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

public class quiz_true_false extends Activity {

    // TODO: Declare member variables here:
    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestionTextView;
    TextView mScoreTextView;
    ProgressBar mProgressBar;
    int mIndex; //will increase after answer selection
    int mQuestion;
    int mScore;  //will increase with correct answer

    // TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[] {

    };
//            new TrueFalse(R.string.question_1, true),
//            new TrueFalse(R.string.question_2, true),
//            new TrueFalse(R.string.question_3, true),
//            new TrueFalse(R.string.question_4, true),
//            new TrueFalse(R.string.question_5, true),
//            new TrueFalse(R.string.question_6, false),
//            new TrueFalse(R.string.question_7, true),
//            new TrueFalse(R.string.question_8, false),
//            new TrueFalse(R.string.question_9, true),
//            new TrueFalse(R.string.question_10, true),
//            new TrueFalse(R.string.question_11, false),
//            new TrueFalse(R.string.question_12, false),
//            new TrueFalse(R.string.question_13,true)
//    };

    // TODO: Declare constants here
    final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0/mQuestionBank.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_true_false);
        if(savedInstanceState != null){
            mScore =savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");

        }else {
            mScore = 0;
            mIndex = 0;

        }
        //new LetsCollectJSON("https://script.google.com/macros/s/AKfycbxlRWQK1ypdlapRhFWoI1WY3B5ccQsYduZp7EVCIiBGz1vrcNI/exec?MAT477-BuRhAq-xS2vtUhjkwhP7cC3CUJ");

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mQuestionTextView = findViewById(R.id.question_text_view);
        mScoreTextView = findViewById(R.id.score);
        mProgressBar = (ProgressBar)findViewById(R.id.progress_bar);

        mQuestion = mQuestionBank[mIndex].getmQuestionID();
        mQuestionTextView.setText(mQuestion);
        mScoreTextView.setText("Score" + mScore+ "/"+ mQuestionBank.length);
//
//        View.OnClickListener myListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("Quizzler", " True Button got pressed");
//                Toast.makeText(getApplicationContext(), "True Button got pressed", Toast.LENGTH_LONG).show();
//            }
//        };
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                updateQuestion();
//                Log.d("Quizzler", " True Button got pressed");
//                Toast.makeText(getApplicationContext(), "True Button got pressed", Toast.LENGTH_LONG).show();
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                updateQuestion();
//                Log.d("Quizzler", "False Button got pressed");
//                Toast myToast = Toast.makeText(getApplicationContext(), "False Button got pressed", Toast.LENGTH_LONG);
//                myToast.show();
            }
        });

    }
    private void updateQuestion(){
        mIndex = (mIndex+1)% mQuestionBank.length;
        if(mIndex==0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Quiz is done");
            alert.setCancelable(false);
            alert.setMessage("You scored " +mScore+ " points out of "+mQuestionBank.length);
            alert.setPositiveButton("Close Quiz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        }
        mQuestion = mQuestionBank[mIndex].getmQuestionID();
        mQuestionTextView.setText(mQuestion);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT); //progress bar will fill 8 out of 100
        mScoreTextView.setText("Score" + mScore+ "/"+ mQuestionBank.length);
    }
    private void checkAnswer(boolean userSelection){
        boolean correctAnswer = mQuestionBank[mIndex].ismAnswer();
        if(userSelection==correctAnswer){
            Toast.makeText(getApplicationContext(),R.string.correct_toast, Toast.LENGTH_SHORT).show();
            mScore = mScore+1;

        }else{
            Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey",mIndex);
    }
    // TODO: Add updateUI() here:
    private void updateQuestions(tf_QuestionData questions){
        mQuestionTextView.setText(questions.getmQuestion());

      }
    private void updateAnswer(tf_QuestionData questions) {

        mQuestionTextView.setText(questions.getmQuestion());
    }

}
