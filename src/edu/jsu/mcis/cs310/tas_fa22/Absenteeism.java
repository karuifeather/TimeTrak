package edu.jsu.mcis.cs310.tas_fa22;
import java.time.LocalDate; 


public class Absenteeism {
    //class fields
    private Employee employee;
    private LocalDate startDate;
    private float percent;
    
    //Constructor
    public Absenteeism(Employee employee, LocalDate startDate, float percent) {
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
    
    public float getPercent() {
        return percent;
    }
    
    public void setPercent(float percent) {
        this.percent = percent;
    }
    
    //toString Method
    @Override
    public String toString() {
        return "Absenteeism [employee=" + employee + ", startDate=" +startDate + ", percent=" + percent + "]";
    }
}
