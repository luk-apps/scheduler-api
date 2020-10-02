package com.wookie.lukapp.api.DTO;

import java.util.ArrayList;

public class ScheduleParameters {

    private int timeStep;
    private ArrayList<TimeFrame> timeFrames;
    private int numberOfRooms;
    private boolean useLimitedTimeFrames;
    private int maxNumberOfResults;

    public ScheduleParameters() {}

    public ScheduleParameters(int timeStep, ArrayList<TimeFrame> timeFrames) {
        this.timeStep = timeStep;
        this.timeFrames = timeFrames;
    }

    public int getTimeStep() {
        return timeStep;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public boolean getUseLimitedTimeFrames() { return useLimitedTimeFrames; }

    public ArrayList<TimeFrame> getTimeFrames() {
        return timeFrames;
    }

    public int getMaxNumberOfResults() {
        return maxNumberOfResults;
    }
}
