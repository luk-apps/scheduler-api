package com.wookie.lukapp.core.algorithm.overlap;

import com.wookie.lukapp.api.DTO.TimeFrame;
import com.wookie.lukapp.core.valueObjects.EventTimeFrame;

import java.util.List;

public interface OverlapService {

    boolean doEventsOverlap(List<EventTimeFrame> list);
}
