package com.wookie.lukapp.core.util;

import java.util.Objects;

public class TimeInterval {
    public int start;
    public int end;

    public TimeInterval(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeInterval that = (TimeInterval) o;
        return start == that.start &&
                end == that.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
