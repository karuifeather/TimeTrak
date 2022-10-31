package edu.jsu.mcis.cs310.tas_fa22.dao;

import edu.jsu.mcis.cs310.tas_fa22.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmployeeDAO {
    
    private static final String QUERY_FIND_ID = "SELECT * FROM employee WHERE id = ?";
    private static final String QUERY_FIND_BADGE = "SELECT * FROM employee WHERE badgeid = ?";
    
    private final DAOFactory daofactory;
    
    EmployeeDAO(DAOFactory daofactory) {
        this.daofactory = daofactory;
    }
    
    public Employee find(int id) {
        Employee employee = null;
        PreparedStatement statement = null;
        ResultSet result_set = null;
        
        try {
            Connection conn = daofactory.getConnection();
            
            if (conn.isValid(0)) {
                statement = conn.prepareStatement(QUERY_FIND_ID);
                statement.setInt(1, id);
                boolean foundresults = statement.execute();
                
                if (foundresults) {
                    result_set = statement.getResultSet();
                    
                    while (result_set.next()) {
                        BadgeDAO badgedao = new BadgeDAO(daofactory);
                        ShiftDAO shiftdao = new ShiftDAO(daofactory);
                        DepartmentDAO dptdao = new DepartmentDAO(daofactory);
                        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String firstname = result_set.getString("firstname");
                        String lastname = result_set.getString("lastname");
                        String middlename = result_set.getString("middlename");
                        LocalDateTime active = LocalDateTime.parse(result_set.getString("active"), formatTime);
                        Badge badge = badgedao.find(result_set.getString("badgeid"));
                        Department department = dptdao.find(result_set.getInt("departmentid"));
                        Shift shift = shiftdao.find(badge);
                        EmployeeType employeeType = EmployeeType.values()[result_set.getInt("employeeTypeID")];
                        employee = new Employee(id, firstname, lastname, middlename, active, badge, department, shift, employeeType);
             
                    }
                }
            }
        }
        
        catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        
        finally {
            if (result_set != null) {
                try {
                    result_set.close();
                }
                
                catch (SQLException e) {
            throw new DAOException(e.getMessage());
            }
        }
            
            if (statement != null) {
                try {
                    result_set.close();
                }
                
                catch (SQLException e) {
            throw new DAOException(e.getMessage());
            }
        }
    }
        
        return employee;
    }
    
    public Employee find(Badge badge) {
        Employee employee = null;
        PreparedStatement statement = null;
        ResultSet result_set = null;
        
        try {
            Connection conn = daofactory.getConnection();
            
            if (conn.isValid(0)) {
                statement = conn.prepareStatement(QUERY_FIND_BADGE);
                statement.setString(1, badge.getId());
                
                boolean foundresults = statement.execute();
                
                if (foundresults) {
                    result_set = statement.getResultSet();
                    
                    while (result_set.next()) {
                        ShiftDAO shiftdao = new ShiftDAO(daofactory);
                        DepartmentDAO dptdao = new DepartmentDAO(daofactory);
                        DateTimeFormatter formattime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        int id = result_set.getInt("id");
                        String firstname = result_set.getString("firstname");
                        String lastname = result_set.getString("lastname");
                        String middlename = result_set.getString("middlename");
                        LocalDateTime active = LocalDateTime.parse(result_set.getString("active"), formattime);
                        Department dpt = dptdao.find(result_set.getInt("departmentid"));
                        Shift shift = shiftdao.find(result_set.getInt("shiftid"));
                        EmployeeType employeeType = EmployeeType.values()[result_set.getInt("employeetypeid")];
                        employee = new Employee(id, firstname, lastname, middlename, active, badge, dpt, shift, employeeType);
                    }
                }
            }
        }
        
        catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        
        finally {
            if (result_set != null) {
                try {
                    result_set.close();
                }
                
                catch (SQLException e) {
            throw new DAOException(e.getMessage());
            }
        }
            if (statement != null) {
                try {
                    result_set.close();
                }
                
                catch (SQLException e) {
            throw new DAOException(e.getMessage());
            }
    }
}
        return employee;
    }
}
