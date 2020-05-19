package org.dbs.garage.shell;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import org.dbs.garage.usage.port.in.IManageGarageStock;
import org.dbs.garage.usage.port.out.IConsultGarageStock;
import org.dbs.garage.usage.service.ConsultGarageStockImpl;
import org.dbs.garage.usage.service.ManageGarageStockImpl;
import org.dbs.garage.usage.port.out.GarageDesc;
import org.dbs.garage.usage.port.in.RegisterVehicleCmd;
import org.dbs.garage.domain.Marque;
import org.dbs.garage.infra.xml.RepositoryOfGarageXmlImpl;
import org.dbs.garage.infra.xml.RepositoryOfLocationXmlImpl;

public class GarageApplication {
    private TextMenu rootMenu;
    private IConsultGarageStock consultGarageStock = null;
    private IManageGarageStock enrichGarageStock = null;
    private static final Logger logger = LogManager.getLogger(GarageApplication.class);
    private Random random;

    public GarageApplication() {
        random = new Random();
        initMenuOfGarage();
        linkComponentOfApplication();
    }

    private void initMenuOfGarage() {
        TextMenuItem backLink = new TextMenuItem("Goes back to the root menu", null, null); // tie this up in a bit

        TextMenu subMenu = new TextMenu("Garage supervision");
        subMenu.addItem(new TextMenuItem("Description des garages",
                null, e -> doDescriptionOfGarage()));
        subMenu.addItem(new TextMenuItem("Approvisionnement des garages avec un stock bas",
                null, e -> doEnrichLowStockGarage()));
        subMenu.addItem(backLink);


        this.rootMenu = new TextMenu("Application Gestion de Garage");
        rootMenu.addItem(new TextMenuItem(subMenu, null));

        // Tie the backlink up
        backLink.setSubMenu(rootMenu);
    }

    private void doEnrichLowStockGarage() {
        logger.info("doEnrichLowStockGarage");
        List<GarageDesc> lstDescGarage = consultGarageStock.retrieveGarageWithLowStock(5);
        int idChassis = random.nextInt();
        logger.info(String.format("IdChassis = %d", idChassis));
        for (GarageDesc dDescOfGarage : lstDescGarage) {
            logger.info(dDescOfGarage);
            List<RegisterVehicleCmd> lstVehicleToRegister = new ArrayList<>();
            String nameGarageToEnrich = dDescOfGarage.getName();
            lstVehicleToRegister.add(new RegisterVehicleCmd(nameGarageToEnrich, "ID_" + random.nextInt(), Marque.DACIA));
            lstVehicleToRegister.add(new RegisterVehicleCmd(nameGarageToEnrich, "ID_" + random.nextInt(), Marque.RENAULT));
            lstVehicleToRegister.add(new RegisterVehicleCmd(nameGarageToEnrich, "ID_" + random.nextInt(), Marque.MERCEDES));
            lstVehicleToRegister.add(new RegisterVehicleCmd(nameGarageToEnrich, "ID_" + random.nextInt(), Marque.OPEL));
            enrichGarageStock.registerVehicleToGarage(lstVehicleToRegister);
        }
    }

    private void doDescriptionOfGarage() {
        logger.info("doDescriptionOfGarage");
        List<GarageDesc> lstDescGarage = consultGarageStock.retrieveSupervisionOfGarage();
        for (GarageDesc dDescOfGarage : lstDescGarage) {
            logger.info(dDescOfGarage);
        }
    }

    void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        TextMenu currentMenu = this.rootMenu;
        while (currentMenu != null) {
            System.out.println(currentMenu);
            System.out.print("Your Selection : ");
            String inp = "";
            try {
                inp = br.readLine();
                currentMenu = currentMenu.doOption(Integer.parseInt(inp));
            } catch (Exception ex) {
                System.out.println("Didn't understand " + inp);
                logger.error(ex);
            }
        }

    }

    private void linkComponentOfApplication() {
        consultGarageStock = new ConsultGarageStockImpl(RepositoryOfGarageXmlImpl.getInstance());
        enrichGarageStock = new ManageGarageStockImpl(RepositoryOfGarageXmlImpl.getInstance(),
                RepositoryOfLocationXmlImpl.getInstance());
    }
}
