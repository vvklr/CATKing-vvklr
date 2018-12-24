package in.catking.catking;

//Vishal Raut
//Email me on vr.iitb@gmail.com if you come across any problem
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.Arrays;

import cz.msebera.android.httpclient.Header;



public class LetsCollectJSON {

    public LetsCollectJSON(String url){

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Log.d("CATKing", "Successful JSON data collection " + response.toString());
                String[] myQuestionData = tf_QuestionData.fromJsonQ(response);
                String[] myAnswerData = tf_QuestionData.fromJsonA(response);
                String[][] mA = {myQuestionData,myAnswerData};
                System.out.println(Arrays.deepToString(mA));
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject responce){
                Log.e("CATKing", "Fail JSON" + e.toString());
                Log.d("CATKING", " Fail Status Code" + statusCode);
            }

        });
    }

}
