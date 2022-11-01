package edu.jsu.mcis.cs310.tas_fa22.dao;
import edu.jsu.mcis.cs310.tas_fa22.*;
import java.sql.*;
import java.util.*;

public class DepartmentDAO {
    private static final String QUERY_FIND_ID = "SELECT * FROM employee WHERE id = ?";
    private final DAOFactory daoFactory;
    
    // Constructor
    DepartmentDAO (DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public Department find(int id) {
        Department department = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            if (conn.isValid(0)) {
                ps = conn.ps(QUERY_FIND_ID);
                ps.setInt(1, id);
                boolean hasResults = ps.execute();
                
                if (hasResults) {
                    rs = ps.getResultSet();
                }
                while (rs.next()) {
                    // --- UNFINISHED ---
                    department = new Department(id, description, terminalid);
                }
            }
        }
    }
}
