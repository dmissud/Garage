package org.dbs.garage.shell;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {


    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        GarageApplication myGarageApplication = new GarageApplication();

        logger.info("Run the application with memory repository");
        myGarageApplication.run();

    }

}
