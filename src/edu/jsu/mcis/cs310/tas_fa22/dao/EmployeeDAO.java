package edu.jsu.mcis.cs310.tas_fa22.dao;

import edu.jsu.mcis.cs310.tas_fa22.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmployeeDAO {

    private static final String QUERY_FIND_ID = "SELECT * FROM employee WHERE id = ?";
    private static final String QUERY_FIND_BADGE = "SELECT * FROM employee WHERE badgeid = ?";

    private final DAOFactory daoFactory;

    EmployeeDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Employee find(int id) {
        Employee employee = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND_ID);
                ps.setInt(1, id);
                boolean foundresults = ps.execute();

                if (foundresults) {
                    rs = ps.getResultSet();

                    while (rs.next()) {
                        // declaring daos
                        BadgeDAO badgedao = new BadgeDAO(daoFactory);
                        ShiftDAO shiftdao = new ShiftDAO(daoFactory);
                        DepartmentDAO dptdao = new DepartmentDAO(daoFactory);

                        // get required data for employee
                        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String firstname = rs.getString("firstname");
                        String lastname = rs.getString("lastname");
                        String middlename = rs.getString("middlename");
                        LocalDateTime active = LocalDateTime.parse(rs.getString("active"), formatTime);
                        EmployeeType employeeType = EmployeeType.values()[rs.getInt("employeeTypeID")];

                        // fill up objects from databases
                        Badge badge = badgedao.find(rs.getString("badgeid"));
                        Department dpt = dptdao.find(rs.getInt("departmentid"));
                        Shift shift = shiftdao.find(badge);

                        employee = new Employee(
                                id,
                                firstname,
                                middlename,
                                lastname,
                                active,
                                badge,
                                dpt,
                                shift,
                                employeeType);

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

        return employee;
    }

    public Employee find(Badge badge) {
        Employee employee = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND_BADGE);
                ps.setString(1, badge.getId());

                boolean foundresults = ps.execute();

                if (foundresults) {
                    rs = ps.getResultSet();

                    while (rs.next()) {
                        // declaring daos
                        ShiftDAO shiftdao = new ShiftDAO(daoFactory);
                        DepartmentDAO dptdao = new DepartmentDAO(daoFactory);

                        // get required data for employee
                        DateTimeFormatter formattime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        int id = rs.getInt("id");
                        String firstname = rs.getString("firstname");
                        String lastname = rs.getString("lastname");
                        String middlename = rs.getString("middlename");
                        LocalDateTime active = LocalDateTime.parse(rs.getString("active"), formattime);
                        EmployeeType employeeType = EmployeeType.values()[rs.getInt("employeeTypeID")];

                        // fill up objects from databases
                        Department dpt = dptdao.find(rs.getInt("departmentid"));
                        Shift shift = shiftdao.find(rs.getInt("shiftid"));

                        employee = new Employee(
                                id,
                                firstname,
                                middlename,
                                lastname,
                                active,
                                badge,
                                dpt,
                                shift,
                                employeeType);

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
        
        return employee;
    }
}
