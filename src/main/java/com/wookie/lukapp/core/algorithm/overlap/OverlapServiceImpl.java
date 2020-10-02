package com.wookie.lukapp.core.algorithm.overlap;

import com.wookie.lukapp.core.valueObjects.EventTimeFrameComparator;
import com.wookie.lukapp.core.valueObjects.EventTimeFrame;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class OverlapServiceImpl implements  OverlapService {

    @Override
    public boolean doEventsOverlap(List<EventTimeFrame> list) {
        Collections.sort(list, new EventTimeFrameComparator());
        for(int i = 0; i < list.size() - 1; i++) {
            if(list.get(i).getEnd() > list.get(i + 1).getStart()) {
                return true;
            }
        }
        return false;
    }
}
