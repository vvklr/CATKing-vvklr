package in.catking.catking;

import org.json.JSONException;
import org.json.JSONObject;

public class tf_QuestionData {
    //TODO: public members
    private String mQuestion;
    private String mCorrectAnswer;
    private String mDescription;
    // TODO: Create a question data from a JSON:
    public static String[] fromJsonQ(JSONObject jsonObject) {
        try {
            int QbLength = jsonObject.getJSONArray("questions").length();
            String[] myQuestionData = new String[QbLength];
            for (int i = 0; i < QbLength; i++) {
                tf_QuestionData questionData = new tf_QuestionData();
                questionData.mQuestion = jsonObject.getJSONArray("questions").getJSONObject(i).getString("question");
                myQuestionData[i] = questionData.getmQuestion();
            }
            return myQuestionData;
            }catch(JSONException e){
                e.printStackTrace();
                return null;
            }
        }
    public static String[] fromJsonA(JSONObject jsonObject) {
        try {
            int QbLength = jsonObject.getJSONArray("questions").length();
            String[] myAnswerData = new String[QbLength];
            for (int i = 0; i < QbLength; i++) {
                tf_QuestionData questionData = new tf_QuestionData();
                questionData.mCorrectAnswer = jsonObject.getJSONArray("questions").getJSONObject(i).getString("correctAns");
                myAnswerData[i]=questionData.ismCorrectAnswer();
            }
            return myAnswerData;//was not able to define as much parameters as tf_question_data as
        }catch(JSONException e){//questionData is defined in both classes.
            e.printStackTrace();
            return null;
        }
    }
    public static String[] fromJsonDes(JSONObject jsonObject) {
        try {
            int QbLength = jsonObject.getJSONArray("questions").length();
            String[] myAns_Data = new String[QbLength];
            for (int i = 0; i < QbLength; i++) {
                tf_QuestionData aData = new tf_QuestionData();
                aData.mDescription = jsonObject.getJSONArray("questions").getJSONObject(i).getString("description");
                myAns_Data[i]=aData.getmDescription();
            }
            return myAns_Data;//was not able to define as much parameters as tf_question_data as
        }catch(JSONException e){//questionData is defined in both classes.
            e.printStackTrace();
            return null;
        }
    }

    public String getmQuestion() {
        return mQuestion;
    }
    public String ismCorrectAnswer() {
        return mCorrectAnswer;
    }
    public String getmDescription() {
        return mDescription;
    }

}
