package in.catking.catking;

//Vishal Raut
//Email me on vr.iitb@gmail.com if you come across any problem


public class TrueFalse {
    private int mQuestionID;
    private boolean mAnswer;
    public TrueFalse(int questionResourceID,boolean trueOrFalse){
        mQuestionID = questionResourceID;
        mAnswer = trueOrFalse;
    }

    public int getmQuestionID() {
        return mQuestionID;
    }

    public void setmQuestionID(int mQuestionID) {
        this.mQuestionID = mQuestionID;
    }

    public boolean ismAnswer() {
        return mAnswer;
    }

    public void setmAnswer(boolean mAnswer) {
        this.mAnswer = mAnswer;
    }
}
