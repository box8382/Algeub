package com.pmkproject.algeub;

public class GItemPay {
    private int num;
    private String title;

    private int startClock;
    private int lastClock;

    private int pay;
    private String text;

    private int delivery;
    private boolean nightPay;
    private int free;

    public GItemPay() {
    }

    public GItemPay(int num, String title, int startClock, int lastClock, int pay, String text, int delivery, boolean nightPay, int free) {
        this.num = num;
        this.title = title;
        this.startClock = startClock;
        this.lastClock = lastClock;
        this.pay = pay;
        this.text = text;
        this.delivery = delivery;
        this.nightPay = nightPay;
        this.free = free;
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

    public int getStartClock() {
        return startClock;
    }

    public void setStartClock(int startClock) {
        this.startClock = startClock;
    }

    public int getLastClock() {
        return lastClock;
    }

    public void setLastClock(int lastClock) {
        this.lastClock = lastClock;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDelivery() {
        return delivery;
    }

    public void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    public boolean getNightPay() {
        return nightPay;
    }

    public void setNightPay(boolean nightPay) {
        this.nightPay = nightPay;
    }

    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }
}
