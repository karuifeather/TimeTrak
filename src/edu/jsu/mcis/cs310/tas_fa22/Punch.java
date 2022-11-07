package edu.jsu.mcis.cs310.tas_fa22;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

public class Punch {

    private Integer id;
    private Integer terminalid;
    private Badge badge;
    private EventType punchtype;
    private LocalDateTime originaltimestamp;
    private LocalDateTime adjustedtimestamp;
    private PunchAdjustmentType adjustmenttype;

    // for new punches
    public Punch(int terminalid, Badge badge, EventType punchtype) {
        this.id = null;
        this.terminalid = terminalid;
        this.badge = badge;
        this.punchtype = punchtype;
        this.originaltimestamp = LocalDateTime.now();
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

    public PunchAdjustmentType getAdjustmenttype() {
        return adjustmenttype;
    }

    public LocalDateTime getOriginaltimestamp() {
        return originaltimestamp;
    }

    public LocalDateTime getAdjustedtimestamp() {
        return adjustedtimestamp;
    }

    public void adjust(Shift s) {
        LocalDateTime ot = originaltimestamp;

        adjustmenttype = null;
        adjustedtimestamp = null;

        Boolean isWeekend = false;
        DayOfWeek day = ot.getDayOfWeek();

        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            isWeekend = true;
        }

        Integer interval = s.getRoundInterval();
        Integer grace = s.getGracePeriod();
        Integer dock = s.getDockPenalty();

        LocalTime sStart = s.getStartTime();
        LocalTime sStop = s.getStopTime();
        LocalTime lStart = s.getLunchStart();
        LocalTime lStop = s.getLunchStop();

        LocalDateTime shiftStart = ot.withHour(sStart.getHour()).withMinute(sStart.getMinute());
        shiftStart = shiftStart.withSecond(0).withNano(0);

        LocalDateTime shiftStop = ot.withHour(sStop.getHour()).withMinute(sStop.getMinute());
        shiftStop = shiftStop.withSecond(0).withNano(0);

        LocalDateTime lunchStart = ot.withHour(lStart.getHour()).withMinute(lStart.getMinute());
        lunchStart = lunchStart.withSecond(0).withNano(0);

        LocalDateTime lunchStop = ot.withHour(lStop.getHour()).withMinute(lStop.getMinute());
        lunchStop = lunchStop.withSecond(0).withNano(0);

        LocalDateTime shiftStartInterval = shiftStart.minusMinutes(interval);
        LocalDateTime shiftStartGrace = shiftStart.plusMinutes(grace);
        LocalDateTime shiftStartDock = shiftStart.plusMinutes(dock);

        LocalDateTime shiftStopInterval = shiftStop.plusMinutes(interval);
        LocalDateTime shiftStopGrace = shiftStop.minusMinutes(grace);
        LocalDateTime shiftStopDock = shiftStop.minusMinutes(dock);

        switch (punchtype) {
            // if the punch is clock in
            case CLOCK_IN:
                // early and within the interval
                if (ot.isAfter(shiftStartInterval.minusSeconds(1)) && ot.isBefore(shiftStart)) {
                    adjustedtimestamp = shiftStart;
                    adjustmenttype = PunchAdjustmentType.SHIFT_START;
                } // late and within grace
                else if (ot.isAfter(shiftStart) && ot.isBefore(shiftStartGrace)) {
                    adjustedtimestamp = ot.with(shiftStart);
                    adjustmenttype = PunchAdjustmentType.SHIFT_START;
                } // late: not in grace but within dock
                else if (ot.isAfter(shiftStartGrace) && ot.isBefore(shiftStartDock.plusSeconds(1))) {
                    adjustedtimestamp = ot.with(shiftStartDock);
                    adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
                } // check if it within lunch
                else if (!isWeekend && ot.isAfter(lunchStart) && ot.isBefore(lunchStop)) {
                    adjustedtimestamp = ot.with(lunchStop);
                    adjustmenttype = PunchAdjustmentType.LUNCH_STOP;
                }

                break;

            // if the punch is clock out
            case CLOCK_OUT:
                // late but within the interval
                if (ot.isAfter(shiftStop) && ot.isBefore(shiftStopInterval.plusSeconds(1))) {
                    adjustedtimestamp = ot.with(shiftStop);
                    adjustmenttype = PunchAdjustmentType.SHIFT_STOP;
                } // early but within grace
                else if (ot.isBefore(shiftStop) && ot.isAfter(shiftStopGrace)) {
                    adjustedtimestamp = ot.with(shiftStop);
                    adjustmenttype = PunchAdjustmentType.SHIFT_STOP;
                } // early and outside of grace and within dockinterval; dock him
                else if (ot.isBefore(shiftStopGrace) && ot.isAfter(shiftStopDock.minusSeconds(1))) {
                    adjustedtimestamp = ot.with(shiftStopDock);
                    adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
                } // check if it within lunch
                else if (!isWeekend && ot.isAfter(lunchStart) && ot.isBefore(lunchStop)) {
                    adjustedtimestamp = ot.with(lunchStart);
                    adjustmenttype = PunchAdjustmentType.LUNCH_START;
                }

                break;
        }

        if (adjustedtimestamp != null && adjustmenttype != null) {
            return;
        }

        if ((ot.getMinute() % 15) != 0) {
            // rounding to the nearest increment of interval
            int adjustedminute;
            int minute = ot.getMinute();

            // round down
            if ((minute % interval) < (interval) / 2) {
                adjustedminute = (Math.round(minute / interval) * interval);
            } // round up
            else {
                adjustedminute = (Math.round(minute / interval) * interval) + interval;
            }

            // if ajustedminute was 60 mintues, round up the hour too
            if ((adjustedminute / 60) == 1) {
                this.adjustedtimestamp = ot
                        .withHour(ot.getHour())
                        .plusHours(1)
                        .withMinute(0)
                        .withSecond(0)
                        .withNano(0);

            } else {
                this.adjustedtimestamp = ot
                        .withMinute(adjustedminute)
                        .withSecond(0)
                        .withNano(0);
            }

            this.adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
        }

        // timestamp wasn't adjusted at all
        if (adjustedtimestamp == null) {
            this.adjustedtimestamp = ot.withSecond(0).withNano(0);
            this.adjustmenttype = PunchAdjustmentType.NONE;
        }
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

    public String printAdjusted() {
        StringBuilder s = new StringBuilder();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");

        // #28DC3FB8 CLOCK IN: FRI 09/07/2018 07:00:00 (Shift Start)
        s.append("#");
        s.append(badge.getId()).append(" ");
        s.append(punchtype).append(": ");
        s.append(adjustedtimestamp.format(format)).append(" ");

        s = new StringBuilder(s.toString().toUpperCase());
        s.append("(").append(adjustmenttype).append(")");

        return s.toString();
    }

    @Override
    public String toString() {
        return printOriginal();
    }

}
