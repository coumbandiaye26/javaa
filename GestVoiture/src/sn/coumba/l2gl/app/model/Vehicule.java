package sn.coumba.l2gl.app.model;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicule vehicule = (Vehicule) o;
        return Objects.equals(immatriculation, vehicule.immatriculation);
    }
    @Override
    public int hashCode() {
        return Objects.hash(immatriculation);
    }
    @Override
    public String toString() {
        return immatriculation + " - " + kilometrage + " km - " + annee +
                " - disponible: " + disponible +
                " - en panne: " + enPanne;
    }
}