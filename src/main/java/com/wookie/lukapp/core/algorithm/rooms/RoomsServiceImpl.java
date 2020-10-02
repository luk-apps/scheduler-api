package com.wookie.lukapp.core.algorithm.rooms;

import com.wookie.lukapp.core.algorithm.overlap.OverlapService;
import com.wookie.lukapp.core.services.HelperService;
import com.wookie.lukapp.core.valueObjects.EventTimeFrameComparator;
import com.wookie.lukapp.core.valueObjects.EventTimeFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RoomsServiceImpl implements RoomsService {

    @Autowired
    OverlapService overlapService;

    @Override
    public boolean doEventsFitNRooms(List<EventTimeFrame> list, int n) {
        Collections.sort(list, new EventTimeFrameComparator());

        // liczba pomieszczen nie moze być większa niż liczba eventów
        if(n > list.size())
            n = list.size();

        // sprawdzamy czy jakieś eventy pokrywają się więcej razy niż wynosi liczba sal. Jeśli tak to odpada
        for(int i = 0; i < list.size() - n; i++) {
            if (list.get(i).getEnd() > list.get(i + n).getStart()) {
                // odpada bo pokrywa się więcej niż n razy
                return false;
            }
        }

        // sprawdzamy każdą możliwą parę czy się overlapuje, a jeśli tak to czy są tam te same osoby
        // Jeśli znajdzie taki przypadek to odpada
        List<List<Integer>> indexes = HelperService.findKElementCombinationsOfNElementSet(2, list.size());
        for(List<Integer> i : indexes) {
            List<EventTimeFrame> pair = new ArrayList<>();
            pair.add(list.get(i.get(0)));
            pair.add(list.get(i.get(1)));
            if(overlapService.doEventsOverlap(pair)) {
                List<List<?>> eventParticipants = new ArrayList();
                for (EventTimeFrame e : pair) {
                    eventParticipants.add(new ArrayList(e.getEventData().getParticipants()));
                }

                if (!HelperService.checkIfContainOnlyUniqueLists(eventParticipants))
                    return false;
            }
        }

        return true;
    }
}
