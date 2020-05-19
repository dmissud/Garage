package org.dbs.garage.usage.port.in;

public class UnregisterGarageCmd {
    private final String name;

    public UnregisterGarageCmd(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
