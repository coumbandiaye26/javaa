package src.sn.coumba.l2gl.app.model;

public class Location {
    private String dateDebut;
    private String dateFin;

    public Location(String dateDebut) {
        this.dateDebut = dateDebut;
        this.dateFin = null;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }
}