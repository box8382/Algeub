package com.pmkproject.algeub;

public class ItemPay {
    int num;
    String title;
    String clock;
    String pay;
    String text;

    public ItemPay() {
    }

    public ItemPay(int num, String title, String clock, String pay, String text) {
        this.num = num;
        this.title = title;
        this.clock = clock;
        this.pay = pay;
        this.text = text;
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

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
