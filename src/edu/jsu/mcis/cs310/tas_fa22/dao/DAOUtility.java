package edu.jsu.mcis.cs310.tas_fa22.dao;

import edu.jsu.mcis.cs310.tas_fa22.*;
import java.text.DecimalFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import org.json.simple.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * Utility class for DAOs. This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 *
 */
public final class DAOUtility {

    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist) {
        ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");

        for (Punch punch : dailypunchlist) {
            HashMap<String, String> punchData = new HashMap<>();

            punchData.put("id", String.valueOf(punch.getId()));
            punchData.put("badgeid", String.valueOf(punch.getBadge().getId()));
            punchData.put("terminalid", String.valueOf(punch.getTerminalid()));
            punchData.put("punchtype", String.valueOf(punch.getPunchtype()));
            punchData.put("adjustmenttype", String.valueOf(punch.getAdjustmenttype()));
            punchData.put("originaltimestamp", punch.getOriginaltimestamp().format(format).toUpperCase());
            punchData.put("adjustedtimestamp", punch.getAdjustedtimestamp().format(format).toUpperCase());

            jsonData.add(punchData);

        }

        String json = JSONValue.toJSONString(jsonData);
        return json;

    }

    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift s) {
        long totalMinutes = 0;
        long shiftDuration;

        LocalDateTime shiftStart = null;
        LocalDateTime shiftStop = null;

        Boolean isRecorded;
        Boolean isTimeout = false;

        for (Punch p : dailypunchlist) {
            isRecorded = false;

            PunchAdjustmentType type = p.getAdjustmenttype();

            if (type == PunchAdjustmentType.LUNCH_START || type == PunchAdjustmentType.LUNCH_STOP) {
                continue;
            }

            if (null != type) {
                switch (type) {
                    case SHIFT_START:
                        shiftStart = p.getAdjustedtimestamp();
                        isRecorded = true;
                        break;
                    case SHIFT_STOP:
                        shiftStop = p.getAdjustedtimestamp();
                        isRecorded = true;
                        break;
                    default:
                        break;
                }
            }

            // is the punch is docked or in interval or no adjustment was made
            if (!isRecorded) {
                switch (p.getPunchtype()) {
                    case CLOCK_IN:
                        shiftStart = p.getAdjustedtimestamp();
                        break;
                    case CLOCK_OUT:
                        shiftStop = p.getAdjustedtimestamp();
                        break;
                    case TIME_OUT:
                        isTimeout = true;
                        break;
                }
            } else {
                continue;
            }

            if (isTimeout) {
                return (int) totalMinutes;
            }
        }

        shiftDuration = ChronoUnit.MINUTES.between(shiftStart, shiftStop);

        if (shiftDuration > s.getLunchThreshold()) {
            totalMinutes = shiftDuration - s.getLunchDuration().toMinutes();
        } else {
            totalMinutes = shiftDuration;
        }

        return (int) totalMinutes;
    }
    
    public static double calculateAbsenteeism(ArrayList<Punch> punchlist, Shift s) {
        
        ArrayList<Punch> dailyPunches = new ArrayList();
        
        Integer ongoingDay = punchlist.get(0).getAdjustedtimestamp().getDayOfMonth();
        int currentDay;
        
        long totalMinutesWorked = 0;
        long totalWorkExpected = 0;
        long shiftDuration = s.getShiftDuration().toMinutes();
        
        for (Punch p: punchlist) {
            currentDay = p.getAdjustedtimestamp().getDayOfMonth();
            
            if (currentDay != ongoingDay) {
                // current punch is from a new day
                
                totalMinutesWorked += calculateTotalMinutes(dailyPunches, s);
                totalWorkExpected += shiftDuration;
                
                dailyPunches.clear();
                dailyPunches.add(p);
                
                ongoingDay = currentDay;
            }

            dailyPunches.add(p);
        }
        
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double percent = Double.valueOf(decimalFormat.format((totalMinutesWorked/totalWorkExpected) * 100)); 

        return percent;
    }       
}
