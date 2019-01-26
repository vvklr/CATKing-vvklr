package in.catking.catking;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class pdf_sheet_name {
    private static String Science;
    private static String Geography;
    private static String Books_and_Authors;
    private static String Olympics;
    private static String Sports_and_Achievements;
    private static String Art_and_Culture;
    private static String History;
    private static String Politics;
    private static String Constitution_of_India;
    private static String Miscellaneous;
    private static String Funfacts;
    private static String DynamicGK;
    private static String Economics;
    public void pdf_sheet_name(){
        String url = "https://script.google.com/macros/s/AKfycbwfvXAADSw7PCH36Rjiut9cqOOzOCjGXp2qg0S8jTMMa7eAaGU/exec?MQK1hyOY2ysqc29O-nnehdEwhP7cC3CUJ";
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestHandle requestHandle = client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Log.d("CATKing", "Successful JSON data collection " + response.toString());
                final String[] mySheetID_Data = pdf_SheetData.fromJson_pdfID(response);
                final String Science_2 = mySheetID_Data[0];
                setScience(Science_2);
                final String Geography_2 = mySheetID_Data[1];
                setGeography(Geography_2);
                final String Books_and_Authors_2 = mySheetID_Data[2];
                setBooks_and_Authors(Books_and_Authors_2);
                final String Olympics_2 = mySheetID_Data[3];
                setOlympics(Olympics_2);
                final String Sports_and_Achievements_2 = mySheetID_Data[4];
                setSports_and_Achievements(Sports_and_Achievements_2);
                final String Art_and_Culture_2 = mySheetID_Data[5];
                setArt_and_Culture(Art_and_Culture_2);
                final String History_2 = mySheetID_Data[6];
                setHistory(History_2);
                final String Politics_2 = mySheetID_Data[7];
                setPolitics(Politics_2);
                final String Constitution_of_India_2 = mySheetID_Data[8];
                setConstitution_of_India(Constitution_of_India_2);
                final String Miscellaneous_2 = mySheetID_Data[9];
                setMiscellaneous(Miscellaneous_2);
                final String Funfacts_2 = mySheetID_Data[10];
                setFunfacts(Funfacts_2);
                final String DynamicGK_2 = mySheetID_Data[11];
                setDynamicGK(DynamicGK_2);
                final String Economics_2 = mySheetID_Data[12];
                setEconomics(Economics_2);
                Log.d("CAT_PDF","This is for check: "+DynamicGK_2);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject responce) {
                Log.e("CATKing", "Fail JSON" + e.toString());
                Log.d("CATKING", " Fail Status Code" + statusCode);
            }

        });

    }

//    String url = "https://script.google.com/macros/s/AKfycbxlRWQK1ypdlapRhFWoI1WY3B5ccQsYduZp7EVCIiBGz1vrcNI/exec?MAT477-BuRhAq-xS2vtUhjkwhP7cC3CUJ";
//    AsyncHttpClient client = new AsyncHttpClient();
//    final RequestHandle requestHandle = client.get(url, new JsonHttpResponseHandler() {
//        @Override
//        public void onSuccess(int statusCode, Header[] headers, JSONObject response){
//            Log.d("CATKing", "Successful JSON data collection " + response.toString());
//            final String[] mySheet_Data = pdf_SheetData.fromJson_pdf(response);
//            final String[] mySheetID_Data = pdf_SheetData.fromJson_pdfID(response);
//            Science = mySheetID_Data[0];
//            Geography = mySheetID_Data[1];
//            Books_and_Authors = mySheetID_Data[2];
//            Olympics = mySheetID_Data[3];
//            Sports_and_Achievements = mySheetID_Data[4];
//            Art_and_Culture = mySheetID_Data[5];
//            History = mySheetID_Data[6];
//            Politics = mySheetID_Data[7];
//            Constitution_of_India = mySheetID_Data[8];
//            Miscellaneous = mySheetID_Data[9];
//            Funfacts = mySheetID_Data[10];
//            DynamicGK = mySheetID_Data[11];
//            Economics = mySheetID_Data[12];
//
//
//        }
//
//
//        @Override
//        public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject responce) {
//            Log.e("CATKing", "Fail JSON" + e.toString());
//            Log.d("CATKING", " Fail Status Code" + statusCode);
//        }
//
//    });
    public String getScience() {
        return this.Science;
    }

    public void setScience(String science) {
        Science = science;
    }

    public static String getGeography() {
        return Geography;
    }

    public void setGeography(String geography) {
        Geography = geography;
    }

    public static String getBooks_and_Authors() {
        return Books_and_Authors;
    }

    public void setBooks_and_Authors(String books_and_Authors) {
        Books_and_Authors = books_and_Authors;
    }

    public static String getOlympics() {
        return Olympics;
    }

    public void setOlympics(String olympics) {
        Olympics = olympics;
    }

    public static String getSports_and_Achievements() {
        return Sports_and_Achievements;
    }

    public void setSports_and_Achievements(String sports_and_Achievements) {
        Sports_and_Achievements = sports_and_Achievements;
    }

    public static String getArt_and_Culture() {
        return Art_and_Culture;
    }

    public void setArt_and_Culture(String art_and_Culture) {
        Art_and_Culture = art_and_Culture;
    }

    public static String getHistory() {
        return History;
    }

    public void setHistory(String history) {
        History = history;
    }

    public static String getPolitics() {
        return Politics;
    }

    public void setPolitics(String politics) {
        Politics = politics;
    }

    public static String getConstitution_of_India() {
        return Constitution_of_India;
    }

    public void setConstitution_of_India(String constitution_of_India) {
        Constitution_of_India = constitution_of_India;
    }

    public static String getMiscellaneous() {
        return Miscellaneous;
    }

    public void setMiscellaneous(String miscellaneous) {
        Miscellaneous = miscellaneous;
    }

    public static String getFunfacts() {
        return Funfacts;
    }

    public void setFunfacts(String funfacts) {
        Funfacts = funfacts;
    }

    public static String getDynamicGK() {
        return DynamicGK;
    }

    public void setDynamicGK(String dynamicGK) {
        DynamicGK = dynamicGK;
    }

    public static String getEconomics() {
        return Economics;
    }

    public void setEconomics(String economics) {
        Economics = economics;
    }
}
