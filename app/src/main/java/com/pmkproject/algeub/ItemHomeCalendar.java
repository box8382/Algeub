package com.pmkproject.algeub;

import com.applandeo.materialcalendarview.EventDay;

import java.util.Calendar;

public class ItemHomeCalendar {
    Long calendar;
    int position;
    int cnt;

    public ItemHomeCalendar(Long calendar,int position,int cnt) {
        this.calendar = calendar;
        this.position = position;
        this.cnt=cnt;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public Long getCalendar() {
        return calendar;
    }

    public void setCalendar(Long calendar) {
        this.calendar = calendar;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
