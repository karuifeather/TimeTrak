package edu.jsu.mcis.cs310.tas_fa22;

import java.util.*;
import java.time.*;

public class Shift {
    private String description;
    private LocalTime startTime, stopTime, lunchStart, lunchStop;
    private int id;
    private long lunchDuration, shiftDuration;

    public Shift(HashMap map) {
        this.id = Integer.parseInt((String)map.get("id"));
        this.description = map.get("description").toString();
        this.startTime = LocalTime.parse((String)map.get("startTime"));
        this.stopTime = LocalTime.parse((String)map.get("stopTime"));
        this.lunchStart = LocalTime.parse((String)map.get("lunchStart"));
        this.lunchStop = LocalTime.parse((String)map.get("lunchStop"));
        this.lunchDuration = Duration.between(lunchStart, lunchStop).toMinutes();
        this.shiftDuration = Duration.between(startTime, stopTime).toMinutes();
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getStopTime() {
        return stopTime;
    }

    public LocalTime getLunchStart() {
        return lunchStart;
    }

    public LocalTime getLunchStop() {
        return lunchStop;
    }

    public long getLunchDuration() {
        return lunchDuration;
    }

    public long getShiftDuration() {
        return shiftDuration;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append(description).append(": ");
        s.append(startTime).append(" - ");
        s.append(stopTime).append(" (");
        s.append(shiftDuration).append(" minutes); Lunch: ");
        s.append(lunchStart).append(" - ");
        s.append(lunchStop).append(" (");
        s.append(lunchDuration).append(" minutes)");

        return s.toString();

    } 
    
}
