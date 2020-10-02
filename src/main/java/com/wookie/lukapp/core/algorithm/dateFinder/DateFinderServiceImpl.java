package com.wookie.lukapp.core.algorithm.dateFinder;

import com.wookie.lukapp.api.DTO.TimeFrame;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DateFinderServiceImpl implements DateFinderService {

    public DateTime findEarliestDate(List<TimeFrame> list) {
        DateTime earliest = list.get(0).getStart();
        for(TimeFrame pair : list) {
            if(pair.getStart().isBefore(earliest))
                earliest = pair.getStart();
        }
        return earliest;
    }

    public DateTime findLatesDate(List<TimeFrame> list) {
        DateTime latest = list.get(0).getEnd();
        for(TimeFrame pair : list) {
            if(pair.getEnd().isAfter(latest))
                latest = pair.getEnd();
        }
        return latest;
    }
}
