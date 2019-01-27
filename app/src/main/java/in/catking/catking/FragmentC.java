package in.catking.catking;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import in.catking.catking.quiz.QuizList;
import in.catking.catking.quiz.TF_QuizList;

public class FragmentC extends Fragment{
    private static int SPLASH_TIME_SPLASHSCREEN = 0;
    private static int SPLASH_TIME_OTP = 4000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_c, container, false);
//        View.OnClickListener listener = new View.OnClickListener() {
//            public void onClick(View view) {
//
//            }
//        };
        Button TFQuiz = (Button) rootView.findViewById(R.id.buttonTFQuiz);
        Button MCQuiz = (Button) rootView.findViewById(R.id.buttonMultiQuiz);

//        Button CardQuiz = (Button) rootView.findViewById(R.id.buttonCardQuiz);

        TFQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTF();
            }
        });

        MCQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMCQ();
            }
        });
        return rootView;
    }
    public void startTF(){
                Intent intentC = new Intent(getContext(),TF_QuizList.class);
                startActivity(intentC);
    }
    public void startMCQ(){
                Intent intentD = new Intent(getContext(),QuizList.class);
                startActivity(intentD);
    }
}