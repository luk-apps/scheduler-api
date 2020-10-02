package com.wookie.lukapp;

import com.wookie.lukapp.core.services.HelperService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class HelperServiceTests {

    @Test
    void bitPositionsTest() {
        List<Integer> result = HelperService.bitPositions(14);
        assertEquals(1, result.get(0));
        assertEquals(2, result.get(1));
        assertEquals(3, result.get(2));
    }

    @Test
    void bitPositionsTest2() {
        List<Integer> result = HelperService.bitPositions(25);
        assertEquals(0, result.get(0));
        assertEquals(3, result.get(1));
        assertEquals(4, result.get(2));
        assertEquals(3, result.size());
    }

    @Test
    void findKElementCombinationsOfNElementSetTest() {
        List<List<Integer>> result = HelperService.findKElementCombinationsOfNElementSet(3, 4);

        assertEquals(4, result.size());

        assertEquals(0, result.get(0).get(0));
        assertEquals(1, result.get(0).get(1));
        assertEquals(2, result.get(0).get(2));

        assertEquals(0, result.get(1).get(0));
        assertEquals(1, result.get(1).get(1));
        assertEquals(3, result.get(1).get(2));

        assertEquals(0, result.get(2).get(0));
        assertEquals(2, result.get(2).get(1));
        assertEquals(3, result.get(2).get(2));

        assertEquals(1, result.get(3).get(0));
        assertEquals(2, result.get(3).get(1));
        assertEquals(3, result.get(3).get(2));
    }
}
