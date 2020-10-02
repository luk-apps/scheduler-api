package com.wookie.lukapp.core.algorithm.dateFinder;

import com.wookie.lukapp.api.DTO.TimeFrame;
import org.joda.time.DateTime;

import java.util.List;

public interface DateFinderService {

    DateTime findEarliestDate(List<TimeFrame> list);
    DateTime findLatesDate(List<TimeFrame> list);
}
