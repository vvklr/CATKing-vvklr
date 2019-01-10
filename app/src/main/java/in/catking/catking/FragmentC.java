package in.catking.catking;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentC extends Fragment {

    public FragmentC(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_c, container, false);
        Button TFQuiz = (Button) rootView.findViewById(R.id.buttonTFQuiz);
        Button MCQuiz = (Button) rootView.findViewById(R.id.buttonMultiQuiz);

        TFQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),test1.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

}