package in.catking.catking;

//Vishal Raut
//Email me on vr.iitb@gmail.com if you come across any problem


public class TrueFalse {
    private String mQuestionID;
    private String mAnswer;
    public TrueFalse(String questionResourceID,String trueOrFalse){
        mQuestionID = questionResourceID;
        mAnswer = trueOrFalse;
    }

    public String getmQuestionID() {
        return mQuestionID;
    }

    public void setmQuestionID(String mQuestionID) {
        this.mQuestionID = mQuestionID;
    }

    public String ismAnswer() {
        return mAnswer;
    }

    public void setmAnswer(String mAnswer) {
        this.mAnswer = mAnswer;
    }
}
