package com.wookie.lukapp.core.algorithm.possibleOccurences;

import com.wookie.lukapp.core.util.TimeInterval;

import java.util.ArrayList;
import java.util.List;

public interface PossibleOccurencesService {

    public ArrayList<TimeInterval> findPossibleOccurences(List<TimeInterval> possibleTimeIntervals, List<TimeInterval> eventPossibleTimeIntervals, int durationInMinutes, int timeStep);

}
