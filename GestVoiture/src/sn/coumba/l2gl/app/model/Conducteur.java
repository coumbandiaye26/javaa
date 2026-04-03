package src.sn.coumba.l2gl.app.model;

public class Conducteur {
    private String nom;
    private String permis;

    public Conducteur(String nom, String permis) {
        this.nom = nom;
        this.permis = permis;
    }

    public String getNom() {
        return nom;
    }

    public String getPermis() {
        return permis;
    }
}