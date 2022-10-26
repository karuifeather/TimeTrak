package edu.jsu.mcis.cs310.tas_fa22;

public class Department {
    private final String id, description, terminalid;
    
    public Department(String id, String description, String terminalid) {
        this.id = id;
        this.description = description;
        this.terminalid = terminalid;
    }
    
    public String getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public String getTerminalId() {
        return terminalid;
    }
}
