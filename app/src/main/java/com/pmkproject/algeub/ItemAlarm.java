package com.pmkproject.algeub;

public class ItemAlarm {
    int num;
    String name;
    String week;
    String ampm;
    String time;
    boolean onOff;
    String sound;

    public ItemAlarm(int num, String name, String week,String ampm, String time, boolean onOff,String sound) {
        this.num=num;
        this.name = name;
        this.week = week;
        this.ampm = ampm;
        this.time = time;
        this.onOff = onOff;
        this.sound=sound;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getAmpm() {
        return ampm;
    }

    public void setAmpm(String ampm) {
        this.ampm = ampm;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isOnOff() {
        return onOff;
    }

    public void setOnOff(boolean onOff) {
        this.onOff = onOff;
    }
}
