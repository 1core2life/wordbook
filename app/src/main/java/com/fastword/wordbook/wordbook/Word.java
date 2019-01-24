package com.fastword.wordbook.wordbook;

public class Word {

    private String wordTarget ;
    private String wordMeaning ;

    public Word(String s1, String s2){
        wordTarget = s1;
        wordMeaning = s2;
    }

    public Word(){
        wordTarget = "NULL";
        wordMeaning = "NULL";
    }

    public void setWordTarget(String title) {
        wordTarget = title ;
    }
    public void setWordMeaning(String desc) {
        wordMeaning = desc ;
    }

    public String getWordTarget() {
        return this.wordTarget ;
    }
    public String getWordMeaning() {
        return this.wordMeaning ;
    }


}
