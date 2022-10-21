package edu.jsu.mcis.cs310.tas_fa22;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;

public class Punch {

    private Integer id;
    private Integer terminalid;
    private Badge badge;
    private EventType punchtype;
    private LocalDateTime originaltimestamp;
    private PunchAdjustmentType adjustmenttype; // for future feature

    // for new punches
    public Punch(int terminalid, Badge badge, EventType punchtype) {
        this.id = null;
        this.adjustmenttype = null;
        this.terminalid = terminalid;
        this.badge = badge;
        this.punchtype = punchtype;
    }

    // for existing punches
    public Punch(
            int id,
            int terminalid,
            Badge badge,
            LocalDateTime originaltimestamp,
            EventType punchtype) {

        this.id = id;
        this.terminalid = terminalid;
        this.badge = badge;
        this.originaltimestamp = originaltimestamp;
        this.punchtype = punchtype;
    }

    public Integer getId() {
        return id;
    }

    public Integer getTerminalid() {
        return terminalid;
    }

    public Badge getBadge() {
        return badge;
    }

    public EventType getPunchtype() {
        return punchtype;
    }

    public LocalDateTime getOriginaltimestamp() {
        return originaltimestamp;
    }

    public PunchAdjustmentType getAdjustedtimestamp() {
        return adjustmenttype;
    }

    public String printOriginal() {
        StringBuilder s = new StringBuilder();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");

        // "#0FFA272B CLOCK OUT: MON 09/24/2018 17:30:04"
        s.append("#");
        s.append(badge.getId()).append(" ");
        s.append(punchtype).append(": ");
        s.append(originaltimestamp.format(format));

        return s.toString().toUpperCase();
    }

    @Override
    public String toString() {
        return printOriginal();
    }

}
