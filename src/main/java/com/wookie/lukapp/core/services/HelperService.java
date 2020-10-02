package com.wookie.lukapp.core.services;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class contains bunch of helper methods that do some magic. Mostly taken from stackoverflow ;)
 */
public class HelperService {

    /**
     * Returns list of indexes for every combination. Don't know how it works but it is awesome :D
     * taken from stack overflow
     * @param k
     * @param N
     */
    public static List<List<Integer>> findKElementCombinationsOfNElementSet(int k, int N) {
        List<List<Integer>> result = new ArrayList<>();
        int s = (1 << k) - 1;
        while ((s & 1 << N) == 0)
        {
            result.add(bitPositions(s));
            // do stuff with s
            int lo = s & ~(s - 1);       // lowest one bit
            int lz = (s + lo) & ~s;      // lowest zero bit above lo
            s |= lz;                     // add lz to the set
            s &= ~(lz - 1);              // reset bits below lz
            s |= (lz / lo / 2) - 1;      // put back right number of bits at end
        }
        return result;
    }

    /**
     * return list of bit positions of number written as decimal
     * @param number
     * @return
     */
    public static List<Integer> bitPositions(int number) {
        List<Integer> positions = new ArrayList<>();
        int position = 0;
        while (number != 0) {
            if ((number & 1) != 0) {
                positions.add(position);
            }
            position++;
            number = number >>> 1;
        }
        return positions;
    }

    // TODO UNIT TESTY
    public static boolean checkIfContainOnlyUniqueLists(List<List<?>> list) {
        List<?> flat = list.stream()
            .flatMap(List::stream)
            .collect(Collectors.toList());
        Set<?> set = new HashSet<>(flat);

        if(flat.size() == set.size())
            return true;
        else return false;
    }
}
