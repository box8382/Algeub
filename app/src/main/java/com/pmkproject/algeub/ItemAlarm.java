package com.pmkproject.algeub;

public class ItemAlarm {
    String name;
    long time;
    boolean onOff;

    public ItemAlarm(String name, long time, boolean onOff) {
        this.name = name;
        this.time = time;
        this.onOff = onOff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isOnOff() {
        return onOff;
    }

    public void setOnOff(boolean onOff) {
        this.onOff = onOff;
    }
}
