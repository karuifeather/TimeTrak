package edu.jsu.mcis.cs310.tas_fa22;

import java.time.LocalDateTime;

public class Employee {

    private int id;
    private String firstname, middlename, lastname;
    private LocalDateTime active;
    private String Badge;
    private String Department; // Location of Employees working Department
    private String Shift; // Assigned Work Shift
    private String employeeType; // temporary/part-time or full-time

    // Constructor
    public Employee(int id, String firstname, String middlename, 
            String lastname, LocalDateTime active, String Badge, 
            String Department, String Shift, String employeeType) 
    {
        this.id = id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.active = active;
        this.Badge = Badge;
        this.Department = Department;
        this.Shift = Shift;
        this.employeeType = employeeType;
    }
    
    public int getId() {
        return id;
    }
    
    public String getFirstname() {
        return firstname;
    }
    
    public String getMiddlename() {
        return middlename;
    }
    
    public String getLastname() {
        return lastname;
    }
    
    public LocalDateTime getActive() {
        return active;
    }
    
    public String getBadge() {
        return Badge;
    }
    
    public String getDepartment() {
        return Department;
    }
    
    public String getShift() {
        return Shift;
    }
    
    public String getEmployeeType() {
        return employeeType;
    }
}
