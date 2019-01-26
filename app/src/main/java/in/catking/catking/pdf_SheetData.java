package in.catking.catking;

import org.json.JSONException;
import org.json.JSONObject;

public class pdf_SheetData {

    private String mPDF_ID;
    public static String[] fromJson_pdfID(JSONObject jsonObject) {
        try {
            int QbLength = jsonObject.getJSONArray("pdfs").length();
            String[] myPDF_IdData = new String[QbLength];
            for (int i = 0; i < QbLength; i++) {
                pdf_SheetData idData = new pdf_SheetData();
                idData.mPDF_ID = jsonObject.getJSONArray("pdfs").getJSONObject(i).getString("pdf_if");
                myPDF_IdData[i]=idData.getmPDF_ID();
            }
            return myPDF_IdData;//was not able to define as much parameters as tf_question_data as
        }catch(JSONException e){//questionData is defined in both classes.
            e.printStackTrace();
            return null;
        }
    }

    public String getmPDF_ID() {
        return mPDF_ID;
    }

}
