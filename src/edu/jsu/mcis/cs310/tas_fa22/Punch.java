package edu.jsu.mcis.cs310.tas_fa22;

import java.time.LocalDateTime;

public class Punch {

    Integer id;
    Integer terminalid;
    Badge badge;
    EventType punchtype;
    LocalDateTime originaltimestamp;
    PunchAdjustmentType adjustedtimestamp; // for future feature

    // for new punches
    public void Punch(int terminalid, Badge badge, EventType punchtype) {
        this.id = null;
        this.adjustedtimestamp = null;
        this.terminalid = terminalid;
        this.badge = badge;
        this.punchtype = punchtype;
    }

    // for existing punches
    public void Punch(int id,
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

}
