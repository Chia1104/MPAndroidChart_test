package com.example.mpandroidchart_test;

public class Dcard {
    private String title;
    private String date;
    private String content;
    private String sascore;
    private String saclass;
    private String id;
    private String lv1;
    private String lv2;
    private String lv3;

    public Dcard(String title, String date, String content, String sascore, String saclass, String id, String lv1, String lv2, String lv3) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.sascore = sascore;
        this.saclass = saclass;
        this.id = id;
        this.lv1 = lv1;
        this.lv2 = lv2;
        this.lv3 = lv3;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLv1() {
        return lv1;
    }

    public void setLv1(String lv1) {
        this.lv1 = lv1;
    }

    public String getLv2() {
        return lv2;
    }

    public void setLv2(String lv2) {
        this.lv2 = lv2;
    }

    public String getLv3() {
        return lv3;
    }

    public void setLv3(String lv3) {
        this.lv3 = lv3;
    }
}
