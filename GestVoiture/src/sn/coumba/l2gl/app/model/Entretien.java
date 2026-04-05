package sn.coumba.l2gl.app.model;

public class Entretien {
    private String libelle;
    private int cout;

    public Entretien(String libelle, int cout) {
        this.libelle = libelle;
        this.cout = cout;
    }

    public String getLibelle() {
        return libelle;
    }

    public int getCout() {
        return cout;
    }
}