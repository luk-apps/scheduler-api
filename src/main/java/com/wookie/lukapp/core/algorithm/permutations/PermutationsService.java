package com.wookie.lukapp.core.algorithm.permutations;

import com.wookie.lukapp.core.aggregates.EventData;
import com.wookie.lukapp.core.valueObjects.EventTimeFrame;

import java.util.List;

public interface PermutationsService {

    // return type is void because it is a recursive method
    void generatePermutations(List<EventData> lists, List<List<EventTimeFrame>> result, int depth, List<EventTimeFrame> current, int numberOfRooms, int maxResults);

}
