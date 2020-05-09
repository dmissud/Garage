package org.dbs.garage.application.port.in;

public class RegisterGarageCmd {
    private final String name;
    private final String location;

    public RegisterGarageCmd(String name, String location) {
        this.name = name;
        this.location = location;
    }
    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

}
