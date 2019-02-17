package in.catking.gkapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
//
//import static in.gkapp.gkapp.test2.KEY_AUTHOR;
//import static in.gkapp.gkapp.test2.KEY_DESCRIPTION;
//import static in.gkapp.gkapp.test2.KEY_PUBLISHEDAT;
//import static in.gkapp.gkapp.test2.KEY_TITLE;
//import static in.gkapp.gkapp.test2.KEY_URL;
//import static in.gkapp.gkapp.test2.KEY_URLTOIMAGE;

public class FragmentA extends Fragment {
    String API_KEY = "9f322babc4154fdeb653ffbf0f93ff12"; // API owner Vishal Raut
    String NEWS_SOURCE = "in";

    private ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    static final String KEY_AUTHOR = "author";
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_URL = "url";
    static final String KEY_URLTOIMAGE = "urlToImage";
    static final String KEY_PUBLISHEDAT = "publishedAt";
    public ListView listNews;
    ProgressBar loader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment, container, false);

        listNews = rootView.findViewById(R.id.listNews);
        loader = rootView.findViewById(R.id.loader);
        listNews.setEmptyView(loader);
        //this section will contain cards type view for list


//        listNews.setClipToPadding(false);
//        listNews.setDivider(null);
//        Resources r = getResources();
//        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                8, r.getDisplayMetrics());
//        listNews.setDividerHeight(px);
//        listNews.setFadingEdgeLength(0);
//        listNews.setFitsSystemWindows(true);
//        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
//                r.getDisplayMetrics());
//        listNews.setPadding(px, px, px, px);
//        listNews.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);


        //this section contains card type view for list

        if(Function.isNetworkAvailable(getContext()))
        {
            FragmentA.DownloadNews newsTask = new DownloadNews();
            newsTask.execute();
        }else{
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        String[] items = getResources().getStringArray(R.array.tab_A);
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(items);
//        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
//    }
    class DownloadNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        protected String doInBackground(String... args) {
            String xml = "";

            String urlParameters = "";//xml = Function.excuteGet("https://newsapi.org/v2/top-headlines?country="+NEWS_SOURCE+"&apiKey="+API_KEY, urlParameters);
            xml = Function.excuteGet("https://newsapi.org/v2/top-headlines?country="+NEWS_SOURCE+"&apiKey="+API_KEY, urlParameters);
            return  xml;
        }
        @Override
        protected void onPostExecute(String xml) {

            if(xml.length()>10){ // Just checking if not empty

                try {
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        String aa =jsonObject.optString(KEY_AUTHOR);
                        String nu = "null";
                        boolean cc = aa.equalsIgnoreCase(nu);
                        if(cc == true){
                            map.put(KEY_AUTHOR, "");
                        }else{
                            map.put(KEY_AUTHOR, jsonObject.optString(KEY_AUTHOR).toString());
                        }
                        map.put(KEY_TITLE, jsonObject.optString(KEY_TITLE).toString());
                        map.put(KEY_DESCRIPTION, jsonObject.optString(KEY_DESCRIPTION).toString());
                        map.put(KEY_URL, jsonObject.optString(KEY_URL).toString());
                        map.put(KEY_URLTOIMAGE, jsonObject.optString(KEY_URLTOIMAGE).toString());
                        map.put(KEY_PUBLISHEDAT, jsonObject.optString(KEY_PUBLISHEDAT).toString());
                        dataList.add(map);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }

                ListNewsAdapter adapter = new ListNewsAdapter(getContext(), dataList);
                listNews.setAdapter(adapter);

                listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent i = new Intent(getContext(), DetailsActivity.class);
                        i.putExtra("url", dataList.get(+position).get(KEY_URL));
                        startActivity(i);
                    }
                });

            }else{
                Toast.makeText(getContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }



    }
}
