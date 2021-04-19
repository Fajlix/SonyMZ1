package com.example.sonymz1;

public class ChallengeItem {
    private int mImageResource1;
    private int mImageResource2;
    private int mImageResource3;
    private String mText1;
    private String mText2;

    public ChallengeItem(int mImageResource1, int mImageResource2, int mImageResource3, String mText1, String mText2) {
        this.mImageResource1 = mImageResource1;
        this.mImageResource2 = mImageResource2;
        this.mImageResource3 = mImageResource3;
        this.mText1 = mText1;
        this.mText2 = mText2;
    }
    public void changeText(String text){
        mText1 = text;
    }

    public int getmImageResource1() {
        return mImageResource1;
    }

    public int getmImageResource2() {
        return mImageResource2;
    }

    public int getmImageResource3() {
        return mImageResource3;
    }

    public String getmText1() {
        return mText1;
    }

    public String getmText2() {
        return mText2;
    }
}
