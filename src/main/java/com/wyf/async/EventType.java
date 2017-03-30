package com.wyf.async;

/**
 * Created by w7397 on 2017/3/30.
 */
public enum EventType {
    LOGIN(1),
    MAIL(2);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    }
