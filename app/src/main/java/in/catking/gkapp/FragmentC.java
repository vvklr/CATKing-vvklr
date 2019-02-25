package in.catking.gkapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.catking.gkapp.quiz.ListArchiveAdapter;
import in.catking.gkapp.quiz.ListQuizAdapter;
import in.catking.gkapp.quiz.month_quiz;
import in.catking.gkapp.quiz.newMCQ;


public class FragmentC extends Fragment {
    String YEAR_SOURCE ="https://script.google.com/macros/s/AKfycbxmzSNVg4t9erdTg91xkms5q9UUkDnG0ZRo3x-FGbTpUwojGI0/exec?M4G1WU0BFDiTti-AejxMWgLAIaA47-59-";
    String MONTH_SOURCE = "https://script.google.com/macros/s/AKfycbxeh2q1lesRvM8HJWArjEikSVXxMtIpzaojuyIqLAW4w2Kim50Y/exec?MJ7kX2bt0vSBnySUntAL6VzSAfNTxgSUZ";
    String QUIZ_SOURCE = "https://script.google.com/macros/s/AKfycbwq2-UV7olkZ-lD9uOLOiFIsL0DgsKbPq2war_IIl2JTwhIs2Q/exec?MxDQFLFiPj2cyRTpxua1LJLAIaA47-59-";
    String TODAY_SOURCE ="https://script.google.com/macros/s/AKfycby2ywWfn3M-24z6D-XhPmkOTGRsJXVlaDz_KPq5bzwB_ClpJQ/exec?MEPrXrfqp4_X366DIhIAJDDY1r34qOMV5";

    private ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String, String>> monthList = new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String, String>> todayList = new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String, String>> yearList = new ArrayList<HashMap<String, String>>();
    static final String KEY_NO = "no";
    static final String KEY_QUIZNAME = "quiz_name";
    static final String KEY_QUIZAPI = "quiz_api";
    public ListView listQuiz;
    public ListView listMonth;
    public ListView listToday;
    public ListView listYear;
    RelativeLayout emptyView;

    ProgressBar loader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cl, container, false);
        listQuiz = rootView.findViewById(R.id.listQuizArchive);
        listMonth = rootView.findViewById(R.id.listMonth);
        listToday = rootView.findViewById(R.id.listQuiz);
        emptyView = rootView.findViewById(R.id.emptyTodayView);
        loader = rootView.findViewById(R.id.Qloader);
        listQuiz.setEmptyView(loader);
        listToday.setEmptyView(emptyView);

        if(Function.isNetworkAvailable(getContext()))
        {
            FragmentC.DownloadToday todayTask = new FragmentC.DownloadToday();
            FragmentC.DownloadNews quizTask = new FragmentC.DownloadNews();
            FragmentC.DownloadMonth monthTask = new FragmentC.DownloadMonth();
            //FragmentC.DownloadYear yearTask = new FragmentC.DownloadYear();
            todayTask.execute();
            quizTask.execute();
            monthTask.execute();
            //yearTask.execute();
        }else{
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
        return rootView;
    }

    class DownloadToday extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(String... args) {
            String xml = "";
            String urlParameters = "";//xml = Function.excuteGet("https://newsapi.org/v2/top-headlines?country="+NEWS_SOURCE+"&apiKey="+API_KEY, urlParameters);
            xml = Function.excuteGet(TODAY_SOURCE,urlParameters);
            return  xml;
        }
        @Override
        protected void onPostExecute(String xml) {

            if(xml.length()>10){ // Just checking if not empty

                try {
                    todayList.clear();
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("quiz");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_NO, jsonObject.optString(KEY_NO).toString());
                        map.put(KEY_QUIZNAME, jsonObject.optString(KEY_QUIZNAME).toString());
                        map.put(KEY_QUIZAPI, jsonObject.optString(KEY_QUIZAPI).toString());
                        todayList.add(map);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }

                ListQuizAdapter adapter = new ListQuizAdapter(getContext(), todayList);
                listToday.setAdapter(adapter);
                setListViewHeightBasedOnChildren(listToday);


                listToday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent i = new Intent(getContext(), newMCQ.class);
                        i.putExtra("url", todayList.get(+position).get(KEY_QUIZAPI));
                        i.putExtra("UID",todayList.get(+position).get(KEY_NO));
                        startActivity(i);

                    }
                });

            }else{
                Toast.makeText(getContext(), "No quiz found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class DownloadNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        protected String doInBackground(String... args) {
            String xml = "";

            String urlParameters = "";//xml = Function.excuteGet("https://newsapi.org/v2/top-headlines?country="+NEWS_SOURCE+"&apiKey="+API_KEY, urlParameters);
            xml = Function.excuteGet(QUIZ_SOURCE,urlParameters);
            return  xml;
        }
        @Override
        protected void onPostExecute(String xml) {

            if(xml.length()>10){ // Just checking if not empty

                try {
                    dataList.clear();
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("quiz");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_NO, jsonObject.optString(KEY_NO).toString());
                        map.put(KEY_QUIZNAME, jsonObject.optString(KEY_QUIZNAME).toString());
                        map.put(KEY_QUIZAPI, jsonObject.optString(KEY_QUIZAPI).toString());
                        dataList.add(map);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }

                ListArchiveAdapter aadapter = new ListArchiveAdapter(getContext(), dataList);
                listQuiz.setAdapter(aadapter);
                setListViewHeightBasedOnChildren(listQuiz);

                listQuiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent i = new Intent(getContext(), newMCQ.class);
                        i.putExtra("url", dataList.get(+position).get(KEY_QUIZAPI));
                        i.putExtra("UID",dataList.get(+position).get(KEY_NO));
                        startActivity(i);

                    }
                });

            }else{
                Toast.makeText(getContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class DownloadMonth extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(String... args) {
            String xml = "";
            String urlParameters = "";//xml = Function.excuteGet("https://newsapi.org/v2/top-headlines?country="+NEWS_SOURCE+"&apiKey="+API_KEY, urlParameters);
            xml = Function.excuteGet(MONTH_SOURCE,urlParameters);
            return  xml;
        }
        @Override
        protected void onPostExecute(String xml) {

            if(xml.length()>10){ // Just checking if not empty

                try {
                    monthList.clear();
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("quiz");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_NO, jsonObject.optString(KEY_NO).toString());
                        map.put(KEY_QUIZNAME, jsonObject.optString(KEY_QUIZNAME).toString());
                        map.put(KEY_QUIZAPI, jsonObject.optString(KEY_QUIZAPI).toString());
                        monthList.add(map);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }

                ListArchiveAdapter adapter = new ListArchiveAdapter(getContext(), monthList);
                listMonth.setAdapter(adapter);
                setListViewHeightBasedOnChildren(listMonth);


                listMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent i = new Intent(getContext(), month_quiz.class);
                        i.putExtra("month_url", monthList.get(+position).get(KEY_QUIZAPI));
                        startActivity(i);

                    }
                });

            }else{
                Toast.makeText(getContext(), "No quiz found", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    class DownloadYear extends AsyncTask<String, Void, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//        protected String doInBackground(String... args) {
//            String xml = "";
//            String urlParameters = "";//xml = Function.excuteGet("https://newsapi.org/v2/top-headlines?country="+NEWS_SOURCE+"&apiKey="+API_KEY, urlParameters);
//            xml = Function.excuteGet(YEAR_SOURCE,urlParameters);
//            return  xml;
//        }
//        @Override
//        protected void onPostExecute(String xml) {
//
//            if(xml.length()>10){ // Just checking if not empty
//
//                try {
////                    yearList.clear();
//                    JSONObject jsonResponse = new JSONObject(xml);
//                    JSONArray jsonArray = jsonResponse.optJSONArray("quiz");
//
//
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(KEY_NO, jsonObject.optString(KEY_NO).toString());
//                        map.put(KEY_QUIZNAME, jsonObject.optString(KEY_QUIZNAME).toString());
//                        map.put(KEY_QUIZAPI, jsonObject.optString(KEY_QUIZAPI).toString());
//                        yearList.add(map);
//                    }
//                } catch (JSONException e) {
//                    Toast.makeText(getContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
//                }
//
//                try{
//                    ListArchiveAdapter adapter = new ListArchiveAdapter(getContext(), yearList);
//                    listYear.setAdapter(adapter);
//                    setListViewHeightBasedOnChildren(listYear);
//
//
//                    listYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        public void onItemClick(AdapterView<?> parent, View view,
//                                                int position, long id) {
//                            Intent i = new Intent(getContext(), month_quiz.class);
//                            i.putExtra("month_url", yearList.get(+position).get(KEY_QUIZAPI));
//                            startActivity(i);
//
//                        }
//                    });
//
//
//                }catch (LinkageError error){
//                    Toast.makeText(getContext(), "No quiz found", Toast.LENGTH_SHORT).show();
//                }
//            }else{
//                Toast.makeText(getContext(), "No quiz found", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}