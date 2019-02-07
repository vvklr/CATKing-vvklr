package in.catking.gkapp;

import org.json.JSONException;
import org.json.JSONObject;

public class mcq_QuestionData {
    //TODO: public members
    private String mQuestion;
    private String mOptionA;
    private String mOptionB;
    private String mOptionC;
    private String mOptionD;
    private String mCorrectAnswer;
    private String mDescription;


    // TODO: Create a question data from a JSON:
    public static String[] fromJsonQ(JSONObject jsonObject) {
        try {
            int QbLength = jsonObject.getJSONArray("questions").length();
            String[] myQuestionData = new String[QbLength];
            for (int i = 0; i < QbLength; i++) {
                mcq_QuestionData questionData = new mcq_QuestionData();
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
            String[] myOptionA_Data = new String[QbLength];
            for (int i = 0; i < QbLength; i++) {
                mcq_QuestionData questionData = new mcq_QuestionData();
                questionData.mOptionA = jsonObject.getJSONArray("questions").getJSONObject(i).getString("a");
                myOptionA_Data[i]=questionData.getmOptionA();
            }
            return myOptionA_Data;//was not able to define as much parameters as tf_question_data as
        }catch(JSONException e){//questionData is defined in both classes.
            e.printStackTrace();
            return null;
        }
    }

    public static String[] fromJsonB(JSONObject jsonObject) {
        try {
            int QbLength = jsonObject.getJSONArray("questions").length();
            String[] myOptionB_Data = new String[QbLength];
            for (int i = 0; i < QbLength; i++) {
                mcq_QuestionData questionData = new mcq_QuestionData();
                questionData.mOptionB = jsonObject.getJSONArray("questions").getJSONObject(i).getString("b");
                myOptionB_Data[i]=questionData.getmOptionB();
            }
            return myOptionB_Data;//was not able to define as much parameters as tf_question_data as
        }catch(JSONException e){//questionData is defined in both classes.
            e.printStackTrace();
            return null;
        }
    }


    public static String[] fromJsonC(JSONObject jsonObject) {
        try {
            int QbLength = jsonObject.getJSONArray("questions").length();
            String[] myOptionC_Data = new String[QbLength];
            for (int i = 0; i < QbLength; i++) {
                mcq_QuestionData questionData = new mcq_QuestionData();
                questionData.mOptionC = jsonObject.getJSONArray("questions").getJSONObject(i).getString("c");
                myOptionC_Data[i]=questionData.getmOptionC();
            }
            return myOptionC_Data;//was not able to define as much parameters as tf_question_data as
        }catch(JSONException e){//questionData is defined in both classes.
            e.printStackTrace();
            return null;
        }
    }


    public static String[] fromJsonD(JSONObject jsonObject) {
        try {
            int QbLength = jsonObject.getJSONArray("questions").length();
            String[] myOptionD_Data = new String[QbLength];
            for (int i = 0; i < QbLength; i++) {
                mcq_QuestionData questionData = new mcq_QuestionData();
                questionData.mOptionD = jsonObject.getJSONArray("questions").getJSONObject(i).getString("d");
                myOptionD_Data[i]=questionData.getmOptionD();
            }
            return myOptionD_Data;//was not able to define as much parameters as tf_question_data as
        }catch(JSONException e){//questionData is defined in both classes.
            e.printStackTrace();
            return null;
        }
    }


    public static String[] fromJsonCA(JSONObject jsonObject) {
        try {
            int QbLength = jsonObject.getJSONArray("questions").length();
            String[] myAns_Data = new String[QbLength];
            for (int i = 0; i < QbLength; i++) {
                mcq_QuestionData aData = new mcq_QuestionData();
                aData.mCorrectAnswer = jsonObject.getJSONArray("questions").getJSONObject(i).getString("correctAns");
                myAns_Data[i]=aData.getmCorrectAnswer();
            }
            return myAns_Data;//was not able to define as much parameters as tf_question_data as
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
                mcq_QuestionData aData = new mcq_QuestionData();
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

    public String getmOptionA() {
        return mOptionA;
    }

    public String getmOptionB() {
        return mOptionB;
    }

    public String getmOptionC() {
        return mOptionC;
    }

    public String getmOptionD() {
        return mOptionD;
    }

    public String getmCorrectAnswer() {
        return mCorrectAnswer;
    }

    public String getmDescription() {
        return mDescription;
    }
}
