package com.pmkproject.algeub;

public class ItemTalk {
    private int no;
    private int num;
    private String title;
    private String writer;
    private String date;
    private String content;

    public ItemTalk(int no,int num, String title, String writer, String date, String content) {
        this.no=no;
        this.num = num;
        this.title = title;
        this.writer = writer;
        this.date = date;
        this.content= content;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
