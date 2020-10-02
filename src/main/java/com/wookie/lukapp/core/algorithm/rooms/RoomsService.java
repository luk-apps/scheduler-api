package com.wookie.lukapp.core.algorithm.rooms;

import com.wookie.lukapp.core.valueObjects.EventTimeFrame;

import java.util.List;

public interface RoomsService {

    public boolean doEventsFitNRooms(List<EventTimeFrame> list, int n);
}
