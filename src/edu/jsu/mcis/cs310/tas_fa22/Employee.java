package edu.jsu.mcis.cs310.tas_fa22;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Employee {

    private Integer id;
    private String firstname, middlename, lastname;
    private LocalDateTime active;
    private Badge badge;
    private Department department; // Location of Employees working Department
    private Shift shift; // Assigned Work Shift
    private EmployeeType employeeType; // temporary/part-time or full-time

    // Constructor
    public Employee(
            Integer id, 
            String firstname, 
            String middlename,
            String lastname, 
            LocalDateTime active, 
            Badge badge,
            Department department, 
            Shift shift, 
            EmployeeType employeeType) {
        this.id = id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.active = active;
        this.badge = badge;
        this.department = department;
        this.shift = shift;
        this.employeeType = employeeType;
    }

    public Integer getId() {
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

    public Badge getBadge() {
        return badge;
    }

    public Department getDepartment() {
        return department;
    }

    public Shift getShift() {
        return shift;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        // "ID #82: Taylor, Jennifer T (#ADD650A8), Type: Full-Time, Department: Office, Active: 02/13/2016"
        s
            .append("ID #")
            .append(id)
            .append(": ")
            .append(lastname)
            .append(", ")
            .append(firstname)
            .append(" ")
            .append(middlename)
            .append(" (#")
            .append(badge.getId())
            .append("), Type: ")
            .append(employeeType)
            .append(", Department: ")
            .append(department.getDescription())
            .append(", Active: ")
            .append(active.format(format));

        return s.toString();
    }
}