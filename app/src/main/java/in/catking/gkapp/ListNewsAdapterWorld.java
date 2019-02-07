package in.catking.gkapp;


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

/**
 * vvklr
 */

class ListNewsAdapterWorld extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, String>> data;

    public ListNewsAdapterWorld(Context a, ArrayList<HashMap<String, String>> d) {
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
        ListNewsViewHolder holder = null;
        if (convertView == null) {
            holder = new ListNewsViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.list_row, parent, false);
            holder.galleryImage = (ImageView) convertView.findViewById(R.id.galleryImage);
            holder.author = (TextView) convertView.findViewById(R.id.author);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.sdetails = (TextView) convertView.findViewById(R.id.sdetails);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (ListNewsViewHolder) convertView.getTag();
        }
        holder.galleryImage.setId(position);
        holder.author.setId(position);
        holder.title.setId(position);
        holder.sdetails.setId(position);
        holder.time.setId(position);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        try{
            holder.author.setText(song.get(FragmentB.KEY_AUTHOR));
            holder.title.setText(song.get(FragmentB.KEY_TITLE));
            holder.time.setText(song.get(FragmentB.KEY_PUBLISHEDAT));
            holder.sdetails.setText(song.get(FragmentB.KEY_DESCRIPTION));

            if(song.get(FragmentB.KEY_URLTOIMAGE).toString().length() < 5)
            {
                holder.galleryImage.setVisibility(View.GONE);
            }else{
                Picasso.with(context)
                        .load(song.get(FragmentB.KEY_URLTOIMAGE).toString())
                        .resize(300, 150)
                        .into(holder.galleryImage);
            }
        }catch(Exception e) {}
        return convertView;
    }
}

class ListNewsViewHolderWorld {
    ImageView galleryImage;
    TextView author, title, sdetails, time;
}