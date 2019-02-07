package in.catking.gkapp.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import in.catking.gkapp.R;

public class TF_ListQuizAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, String>> data;

    public TF_ListQuizAdapter(Context a, ArrayList<HashMap<String, String>> d) {
        context = a;
        data=d;
    }


    public int getCount() {
        return data.size();
    }


    public Object getItem(int position) {
        return position;
    }


    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        TF_ListQuizViewHolder holder = null;
        if (convertView == null) {
            holder = new TF_ListQuizViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.quiz_item_newitem, parent, false);
            holder.quiz = (TextView) convertView.findViewById(R.id.quiz);

            convertView.setTag(holder);
        } else {
            holder = (TF_ListQuizViewHolder) convertView.getTag();
        }
        holder.quiz.setId(position);
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        try{
            holder.quiz.setText(song.get(QuizList.KEY_QUIZNAME));

        }catch(Exception e) {}
        return convertView;

    }
}
class TF_ListQuizViewHolder {
    TextView quiz;
}
