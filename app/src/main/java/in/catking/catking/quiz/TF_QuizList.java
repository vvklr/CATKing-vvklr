package in.catking.catking.quiz;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import in.catking.catking.Function;
import in.catking.catking.R;


public class TF_QuizList extends AppCompatActivity {
    String QUIZ_SOURCE = "https://script.google.com/macros/s/AKfycbxaw036z5nEof8HAU_ws5YC54o63tVh603cM9NDceyVkVd9Mts/exec?MnTYt0D2S0YhWZdXx2vSKPEwhP7cC3CUJ";

    private ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    static final String KEY_NO = "no";
    static final String KEY_QUIZNAME = "quiz_name";
    static final String KEY_QUIZAPI = "quiz_api";
    public ListView listQuiz;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ql);

        listQuiz = findViewById(R.id.listQuiz);
        loader = findViewById(R.id.Qloader);
        listQuiz.setEmptyView(loader);

        listQuiz.setClipToPadding(false);
        listQuiz.setDivider(null);
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, r.getDisplayMetrics());
        listQuiz.setDividerHeight(px);
        listQuiz.setFadingEdgeLength(0);
        listQuiz.setFitsSystemWindows(true);
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
                r.getDisplayMetrics());
        listQuiz.setPadding(px, px, px, px);
        listQuiz.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);

        if(Function.isNetworkAvailable(getApplicationContext()))
        {
            TF_QuizList.DownloadNews quizTask = new DownloadNews();
            quizTask.execute();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }

                TF_ListQuizAdapter adapter = new TF_ListQuizAdapter(getApplicationContext(), dataList);
                listQuiz.setAdapter(adapter);

                listQuiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent i = new Intent(getApplicationContext(), new_TF.class);
                        i.putExtra("url", dataList.get(+position).get(KEY_QUIZAPI));
                        startActivity(i);
                    }
                });

            }else{
                Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }



    }
}
