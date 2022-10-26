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

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append('#').append(id).append(' ');
        s.append('(').append(description).append(')');
        s.append("Terminal ID: ").append(terminalid).append(' ');

        return s.toString();
    }
}
