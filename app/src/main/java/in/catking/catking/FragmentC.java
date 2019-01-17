package in.catking.catking;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import in.catking.catking.quiz.QuizList;
import in.catking.catking.quiz.TF_QuizList;

public class FragmentC extends Fragment{

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
                Intent intent = new Intent(getContext(),TF_QuizList.class);
                startActivity(intent);
            }
        });

        MCQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),QuizList.class);
                startActivity(intent);
            }
        });

//        CardQuiz.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(),test3.class);
//                startActivity(intent);
//            }
//        });
        return rootView;
    }
}