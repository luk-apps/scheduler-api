package com.wookie.lukapp.exception;

public enum ExceptionCodes {

    IMPOSSIBLE_EVENT_TIME_FRAMES("IMPOSSIBLE_EVENT_TIME_FRAMES");

    public final String name;

    private ExceptionCodes(String label) {
        this.name = label;
    }

}
