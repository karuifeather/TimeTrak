package edu.jsu.mcis.cs310.tas_fa22.dao;

import edu.jsu.mcis.cs310.tas_fa22.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class PunchDAO {

    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";
    private static final String QUERY_LIST = "SELECT * FROM event WHERE badgeid = ? ORDER BY timestamp";
    private static final String QUERY_LIST_E = "SELECT * FROM event WHERE badgeid = ? AND timestamp > ? LIMIT 1";

    private final DAOFactory daoFactory;

    // constructor
    PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Punch find(int id) {

        Punch punch = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND);
                ps.setString(1, Integer.toString(id));

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {

                        int terminalid;
                        String badgeid;
                        EventType punchtype;
                        LocalDateTime originaltimestamp;

                        // get terminal id  
                        terminalid = rs.getInt("terminalid");

                        // getting badge
                        badgeid = rs.getString("badgeid");
                        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
                        Badge b = badgeDAO.find(badgeid);

                        // get punch type 
                        punchtype = EventType.values()[rs.getInt("eventtypeid")];

                        // get timestamp
                        originaltimestamp = rs.getTimestamp("timestamp").toLocalDateTime();

                        punch = new Punch(id, terminalid, b, originaltimestamp, punchtype);

                    }

                }

            }

        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }

        }

        return punch;

    }

    public ArrayList list(Badge badge, LocalDate date) {
        ArrayList<Punch> list = new ArrayList();
        
        Timestamp ts = Timestamp.valueOf(date.atStartOfDay());
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_LIST);
                ps.setString(1, badge.getId());

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        
                        Timestamp punchdate = rs.getTimestamp(4);
                        LocalDateTime local = punchdate.toLocalDateTime();
                        LocalDate ld = local.toLocalDate();
                        
                        if (ld.equals(date)) {
                            int id = rs.getInt(1);
                            list.add(find(id));
                        }
                        
                    }

                }

            }
            
            if ((list != null) && ((list.get(list.size() - 1)).getPunchtype() == EventType.CLOCK_IN)) {
                LocalDateTime newdate = list.get(list.size() - 1).getOriginaltimestamp();
                Timestamp newts = Timestamp.valueOf(newdate);
                
                ps = conn.prepareStatement(QUERY_LIST_E);
                ps.setString(1, badge.getId());
                ps.setString(2, newts.toString());
                    
                boolean hasresults = ps.execute();
                
                if (hasresults) {
                    
                    rs = ps.getResultSet();
                    
                    while (rs.next()) {
                        int id = rs.getInt(1);
                        list.add(find(id));
                    }
                    
                }
                    
            }

        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }

        }
        
        // testing
        for (Punch p : list) {
            System.out.println(p.toString());
        }
        
        return list;
        
    }
}
