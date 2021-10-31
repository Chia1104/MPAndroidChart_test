package com.example.mpandroidchart_test;

public class Dcard {
    private String title;
    private String date;
    private String content;
    private String sascore;
    private String saclass;

    public Dcard(String title, String date, String content, String sascore, String saclass) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.sascore = sascore;
        this.saclass = saclass;
    }

    public Dcard() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSascore() {
        return sascore;
    }

    public void setSascore(String sascore) {
        this.sascore = sascore;
    }

    public String getSaclass() {
        return saclass;
    }

    public void setSaclass(String saclass) {
        this.saclass = saclass;
    }
}
