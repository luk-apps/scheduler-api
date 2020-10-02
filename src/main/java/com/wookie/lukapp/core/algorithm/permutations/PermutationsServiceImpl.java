package com.wookie.lukapp.core.algorithm.permutations;

import com.wookie.lukapp.core.aggregates.EventData;
import com.wookie.lukapp.core.algorithm.rooms.RoomsService;
import com.wookie.lukapp.core.valueObjects.EventTimeFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermutationsServiceImpl implements PermutationsService {

//    @Autowired
    private final RoomsService roomsService;

    @Autowired
    public PermutationsServiceImpl(RoomsService roomsService) {
        this.roomsService = roomsService;
    }

    @Override
    public void generatePermutations(List<EventData> lists, List<List<EventTimeFrame>> result, int depth, List<EventTimeFrame> current, int numberOfRooms, int maxResults) {
        if (depth == lists.size()) {
            if(roomsService.doEventsFitNRooms(current, numberOfRooms))
                result.add(current);
            return;
        }

        for (int i = 0; i < lists.get(depth).getPossibleOccurrences().size(); i++) {
            List<EventTimeFrame> list = new ArrayList<>(current);
            list.add(new EventTimeFrame(lists.get(depth), lists.get(depth).getPossibleOccurrences().get(i).start,
                    lists.get(depth).getPossibleOccurrences().get(i).end));
            if(result.size() < maxResults)
                generatePermutations(lists, result, depth + 1, list, numberOfRooms, maxResults);
        }
    }


}
