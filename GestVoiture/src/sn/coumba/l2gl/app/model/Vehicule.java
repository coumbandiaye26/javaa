package src.sn.coumba.l2gl.app.model;

public class Vehicule {
    private String immatriculation;
    private int kilometrage;
    private boolean disponible;
    private boolean enPanne;
    private int annee;

    public Vehicule(String immatriculation, int kilometrage, boolean disponible, boolean enPanne, int annee) {
        this.immatriculation = immatriculation;
        this.kilometrage = kilometrage;
        this.disponible = disponible;
        this.enPanne = enPanne;
        this.annee = annee;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public int getKilometrage() {
        return kilometrage;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public boolean isEnPanne() {
        return enPanne;
    }

    public int getAnnee() {
        return annee;
    }

    public void setKilometrage(int kilometrage) {
        this.kilometrage = kilometrage;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setEnPanne(boolean enPanne) {
        this.enPanne = enPanne;
    }
    @Override
    public String toString() {
        return immatriculation + " - " + marque + " - " + kilometrage + " km - " + annee;
    }
}