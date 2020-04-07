package org.dbs.garage.domain;

public abstract class Moteur {
    private final String numSerie;
    private final int cylindre;
    private final int km;
    private final boolean neuf;

    public Moteur(String numSerie, int cylindre, int km, boolean neuf) {
        this.numSerie = numSerie;
        this.cylindre = cylindre;
        this.km = km;
        this.neuf = neuf;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public int getCylindre() {
        return cylindre;
    }

    public int getKm() {
        return km;
    }

    public boolean isNeuf() {
        return neuf;
    }

}
