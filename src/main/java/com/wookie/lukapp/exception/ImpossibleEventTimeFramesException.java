package com.wookie.lukapp.exception;

import java.util.List;


public class ImpossibleEventTimeFramesException extends Exception {

    private List<String> failedEventsNames;

    public ImpossibleEventTimeFramesException(List<String> failedEventsNames) {
        super(ExceptionCodes.IMPOSSIBLE_EVENT_TIME_FRAMES.name());
        this.failedEventsNames = failedEventsNames;
    }

    public String getFailedEventsNames() {
        return String.join(", ", failedEventsNames);
    }
}
