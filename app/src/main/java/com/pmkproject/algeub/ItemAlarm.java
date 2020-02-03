package com.pmkproject.algeub;

public class ItemAlarm {
    int num;
    String name;
    String time;
    boolean onOff;

    public ItemAlarm(int num,String name, String time, boolean onOff) {
        this.num=num;
        this.name = name;
        this.time = time;
        this.onOff = onOff;
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
