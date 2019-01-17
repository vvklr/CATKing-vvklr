package in.catking.catking.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import in.catking.catking.FragmentA;
import in.catking.catking.R;

public class ListQuizAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, String>> data;

    public ListQuizAdapter(Context a, ArrayList<HashMap<String, String>> d) {
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
        ListQuizViewHolder holder = null;
        if (convertView == null) {
            holder = new ListQuizViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.quiz_item_newitem, parent, false);
            holder.quiz = (TextView) convertView.findViewById(R.id.quiz);

            convertView.setTag(holder);
        } else {
            holder = (ListQuizViewHolder) convertView.getTag();
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
class ListQuizViewHolder {
    TextView quiz;
}
