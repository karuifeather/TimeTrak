package edu.jsu.mcis.cs310.tas_fa22;
import java.time.LocalDate; 


public class Absenteeism {
    //class fields
    private Employee employee;
    private LocalDate startDate;
    private Double percent;
    
    //Constructor
    public Absenteeism(Employee employee, LocalDate startDate, Double percent) {
        this.employee = employee;
        this.startDate = startDate;
        this.percent = percent;
    }
    
    //Getters and Setters
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public Double getPercent() {
        return percent;
    }
    
    public void setPercent(Double percent) {
        this.percent = percent;
    }
    
    //toString Method
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        // "#F1EE0555 (Pay Period Starting 08-05-2018): -20.00%"
        s
                .append("#")
                .append(employee)
                .append(' ')
                .append("(Pay Period Starting ")
                .append(startDate)
                .append("): ")
                .append(percent)
                .append("%");

        return s.toString();

        
    }
}
