package com.swufe.page;

public class WordItem {
    private int id;
    private String curWord;
    private String curMean;

    public WordItem() {
        this.curWord = "";
        this.curMean = "";
    }

    public WordItem(String curWord, String curMean) {
        this.curWord = curWord;
        this.curMean = curMean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurWord() {
        return curWord ;
    }

    public void setCurWord(String curWord) {
        this.curWord = curWord;
    }

    public String getCurMean() {
        return curMean;
    }

    public void setCurMean(String curMean) {
        this.curMean = curMean;
    }
}

