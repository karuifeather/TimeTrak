package edu.jsu.mcis.cs310.tas_fa22.dao;

import edu.jsu.mcis.cs310.tas_fa22.*;
import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import org.json.simple.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 * 
 */
public final class DAOUtility {
    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist){
        ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();
        
        for(Punch punch : dailypunchlist){
            HashMap<String, String> punchData = new HashMap<>();
            
            punchData.put("id", String.valueOf(punch.getId()));
            punchData.put("badgeid", String.valueOf(punch.getBadge()));
            punchData.put("terminalid", String.valueOf(punch.getTerminalid()));
            punchData.put("punchtype", String.valueOf(punch.getPunchtype()));
            punchData.put("adjustmenttype", String.valueOf(punch.getAdjustmenttype()));
            punchData.put("originaltimestamp", String.valueOf(punch.getOriginaltimestamp()));
            punchData.put("adjustedtimestamp", String.valueOf(punch.getAdjustedtimestamp()));
            
            jsonData.add(punchData);
            
        }
        String json = JSONValue.toJSONString(jsonData);
        return json; 
      
    }
}