package org.dbs.appli;

import org.dbs.garage.*;
import org.dbs.garagememory.RepositoryOfGarageMemory;

public class Main {

    public static IRepositoryOfGarage repoGarage = null;
    public static final String SUPER_TUTURE = "Super Tuture";
    public static final String PUTEAU = "Puteau";

    public static void main(String[] args) {

        initApplication();

        Garage myGarage = null;
        try {
            myGarage = repoGarage.retrieveGarageByName(SUPER_TUTURE);
        } catch (UnknowGarage unknowGarage) {
            unknowGarage.printStackTrace();
        }

        System.out.println(myGarage);

//        Garage garage = new Garage();
//        System.out.println(garage);
//        Vehicle lag1 = new Lagouna();
//        lag1.setMoteur(new MoteurEssence("150 Chevaux", 10256d));
//        lag1.addOption(new GPS());
//        lag1.addOption(new SiegeChauffant());
//        lag1.addOption(new VitreElectrique());
//        garage.addVoiture(lag1);
//
//        Vehicle A300B_2 = new A300B();
//        A300B_2.setMoteur(new MoteurElectrique("1500 W", 1234d));
//        A300B_2.addOption(new Climatisation());
//        A300B_2.addOption(new BarreDeToit());
//        A300B_2.addOption(new SiegeChauffant());
//        garage.addVoiture(A300B_2);
//
//        Vehicle d4_1 = new D4();
//        d4_1.setMoteur(new MoteurDiesel("200 Hdi", 25684.80d));
//        d4_1.addOption(new BarreDeToit());
//        d4_1.addOption(new Climatisation());
//        d4_1.addOption(new GPS());
//        garage.addVoiture(d4_1);
//
//        Vehicle lag2 = new Lagouna();
//        lag2.setMoteur(new MoteurDiesel("500 Hdi", 456987d));
//        garage.addVoiture(lag2);   	 Vehicle A300B_1 = new A300B();
//        A300B_1.setMoteur(new MoteurHybride("ESSENCE/Electrique", 12345.95d));
//        A300B_1.addOption(new VitreElectrique());
//        A300B_1.addOption(new BarreDeToit());
//        garage.addVoiture(A300B_1);
//
//        Vehicle d4_2 = new D4();
//        d4_2.setMoteur(new MoteurElectrique("100 KW", 1224d));
//        d4_2.addOption(new SiegeChauffant());
//        d4_2.addOption(new BarreDeToit());
//        d4_2.addOption(new Climatisation());
//        d4_2.addOption(new GPS());
//        d4_2.addOption(new VitreElectrique());
//        garage.addVoiture(d4_2);
    }

    private static void initApplication() {
        repoGarage = RepositoryOfGarageMemory.getInstance();
    }
}
