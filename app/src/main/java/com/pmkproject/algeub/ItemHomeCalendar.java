package com.pmkproject.algeub;

import com.applandeo.materialcalendarview.EventDay;

import java.util.Calendar;

public class ItemHomeCalendar {
    Calendar calendar;
    int position;
    EventDay eventDay;

    public ItemHomeCalendar(Calendar calendar,int position,EventDay eventDay) {
        this.calendar = calendar;
        this.position = position;
        this.eventDay = eventDay;
    }

    public EventDay getEventDay() {
        return eventDay;
    }

    public void setEventDay(EventDay eventDay) {
        this.eventDay = eventDay;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
