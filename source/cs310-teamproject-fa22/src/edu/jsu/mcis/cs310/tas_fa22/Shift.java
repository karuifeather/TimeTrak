package edu.jsu.mcis.cs310.tas_fa22;

import java.util.*;
import java.time.*;

public class Shift {
    private LocalTime startTime, stopTime;
    private int lunchDuration, shiftDuration;

    public Shift(HashMap map) {
        this.startTime = (LocalTime)map.get("startTime");
        this.stopTime = (LocalTime)map.get("stopTime");
        this.lunchDuration = (int)map.get("lunchDuration");
        this.shiftDuration = (int)map.get("shiftDuration");
    }
}
