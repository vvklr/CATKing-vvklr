package in.catking.catking;

import org.json.JSONException;
import org.json.JSONObject;

public class tf_QuestionData {
    //TODO: public members
    private String mQuestion;
    private String mCorrectAnswer;
    // TODO: Create a question data from a JSON:
    public static String[] fromJsonQ(JSONObject jsonObject) {
        try {
            int QbLength = jsonObject.getJSONArray("questions").length();
            String[] myQuestionData = new String[QbLength];
            for (int i = 0; i < QbLength; i++) {
                tf_QuestionData questionData = new tf_QuestionData();
                //questionData.mQuestionNumber = jsonObject.getJSONArray("questions").getJSONObject(i).getInt("questionNumber");
                questionData.mQuestion = jsonObject.getJSONArray("questions").getJSONObject(i).getString("question");
                //questionData.mCorrectAnswer = jsonObject.getJSONArray("questions").getJSONObject(i).getString("correctAns");
                myQuestionData[i] = questionData.getmQuestion();
                //return  myQuestionData;
            }
//            boolean[] myAnswerData = new boolean[QbLength];
//            for (int i = 0; i < QbLength; i++) {
//                tf_QuestionData questionData = new tf_QuestionData();
//                //questionData.mQuestionNumber = jsonObject.getJSONArray("questions").getJSONObject(i).getInt("questionNumber");
//                //questionData.mQuestion = jsonObject.getJSONArray("questions").getJSONObject(i).getString("question");
//                questionData.mCorrectAnswer = jsonObject.getJSONArray("questions").getJSONObject(i).getBoolean("correctAns");
//                myAnswerData[i] = questionData.ismCorrectAnswer();
//                //return  myQuestionData;
//            }
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


            // TODO: Create a question data from a JSON:
//    public static tf_QuestionData fromJson(JSONObject jsonObject) {
//        try {
//            tf_QuestionData questionData = new tf_QuestionData();
//            questionData.mQuestionNumber = jsonObject.getJSONArray("questions").getJSONObject(0).getInt("questionNumber");
//            questionData.mQuestion= jsonObject.getJSONArray("questions").getJSONObject(0).getString("question");
//            questionData.mCorrectAnswer= jsonObject.getJSONArray("questions").getJSONObject(0).getBoolean("correctAns");
//            return questionData;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    public int getmQuestionNumber() {
//        return mQuestionNumber;
//    }

    public String getmQuestion() {
        return mQuestion;
    }
    public String ismCorrectAnswer() {
        return mCorrectAnswer;
    }

}
