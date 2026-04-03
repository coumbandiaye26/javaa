package sn.coumba.l2gl.app.app;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import sn.coumba.l2gl.app.model.Action;
import sn.coumba.l2gl.app.model.Comparaison;
import sn.coumba.l2gl.app.model.Conducteur;
import sn.coumba.l2gl.app.model.Entretien;
import sn.coumba.l2gl.app.model.Location;
import sn.coumba.l2gl.app.model.Test;
import sn.coumba.l2gl.app.model.Transformation;
import sn.coumba.l2gl.app.model.Vehicule;
import sn.coumba.l2gl.app.service.ParcAutoService;

public class Main {
    public static void main(String[] args) {

        ParcAutoService service = new ParcAutoService();

        Vehicule v1 = new Vehicule("DK-111-AA", "Toyota", 45000, true, false, 2020);
        Vehicule v2 = new Vehicule("DK-222-BB", "Peugeot", 120000, false, true, 2015);
        Vehicule v3 = new Vehicule("DK-333-CC", "Renault", 80000, true, false, 2018);
        Vehicule v4 = new Vehicule("DK-444-DD", "Hyundai", 150000, true, false, 2010);

        List<Vehicule> flotte = new ArrayList<>();
        flotte.add(v1);
        flotte.add(v2);
        flotte.add(v3);
        flotte.add(v4);

        Conducteur c1 = new Conducteur("Awa", "B12345");
        Conducteur c2 = new Conducteur("Moussa", "C98765");

        Entretien e1 = new Entretien("Vidange", 25000);
        Entretien e2 = new Entretien("Freins", 50000);

        Location loc1 = new Location("2026-03-20");

        System.out.println("===== DÉMONSTRATION DES 14 COMPORTEMENTS =====");
//A TESTS
        Testeur<Vehicule> vehiculeDisponible = v -> v.isDisponible();
        System.out.println("1. v1 disponible ? " + vehiculeDisponible.tester(v1));

        System.out.println("Véhicules disponibles :");
        List<Vehicule> disponibles = service.filtrerVehicules(flotte, vehiculeDisponible);
        for (Vehicule v : disponibles) {
            System.out.println(v);
        }

        Testeur<Vehicule> vehiculeEnPanne = v -> v.isEnPanne();
        System.out.println("\n2. v2 en panne ? " + vehiculeEnPanne.tester(v2));

        int seuilKm = 100000;
        Testeur<Vehicule> kilometrageSuperieurSeuil = v -> v.getKilometrage() > seuilKm;
        System.out.println("3. v4 > " + seuilKm + " km ? " + kilometrageSuperieurSeuil.tester(v4));

        int seuilRevisionKm = 90000;
        int seuilAnnee = 2016;
        Testeur<Vehicule> vehiculeAReviser = v -> v.getKilometrage() > seuilRevisionKm || v.getAnnee() < seuilAnnee;
        System.out.println("4. v2 à réviser ? " + vehiculeAReviser.tester(v2));

        System.out.println("Véhicules à réviser :");
        List<Vehicule> aReviser = service.filtrerVehicules(flotte, vehiculeAReviser);
        for (Vehicule v : aReviser) {
            System.out.println(v);
        }

        Testeur<Conducteur> conducteurAutorise = c -> c.getPermis().startsWith("B");
        System.out.println("\n5. " + c1.getNom() + " autorisé ? " + conducteurAutorise.tester(c1));
        System.out.println("   " + c2.getNom() + " autorisé ? " + conducteurAutorise.tester(c2));

        // =========================================================
        // B — TRANSFORMATIONS
        // =========================================================

        // 6. Résumé véhicule
        Transformateur<Vehicule, String> resumeVehicule =
                v -> "Immat: " + v.getImmatriculation() + ", Marque: " + v.getMarque() +
                     ", Km: " + v.getKilometrage() + ", Année: " + v.getAnnee();

        System.out.println("\n6. Résumés véhicules :");
        List<String> resumes = service.mapperVehicules(flotte, resumeVehicule);
        for (String r : resumes) {
            System.out.println(r);
        }

        // 7. Extraire immatriculation
        Transformateur<Vehicule, String> extraireImmatriculation = v -> v.getImmatriculation();
        System.out.println("\n7. Immatriculations :");
        List<String> immats = service.mapperVehicules(flotte, extraireImmatriculation);
        for (String i : immats) {
            System.out.println(i);
        }

        // 8. Calculer âge du véhicule
        Transformateur<Vehicule, Integer> calculerAgeVehicule =
                v -> Year.now().getValue() - v.getAnnee();

        System.out.println("\n8. Âge de v1 : " + calculerAgeVehicule.transformer(v1) + " ans");

        // 9. Coût total d’un entretien
        int taxeFixe = 5000;
        Transformateur<Entretien, Integer> coutTotalEntretien =
                e -> e.getCout() + taxeFixe;

        System.out.println("9. Coût total entretien '" + e1.getLibelle() + "' : " + coutTotalEntretien.transformer(e1));
        System.out.println("   Coût total entretien '" + e2.getLibelle() + "' : " + coutTotalEntretien.transformer(e2));

        // =========================================================
        // C — ACTIONS
        // =========================================================

        // 10. Marquer véhicule en révision
        Action<Vehicule> marquerEnRevision = v -> v.setEnRevision(true);
        marquerEnRevision.executer(v1);
        System.out.println("\n10. v1 en révision ? " + v1.isEnRevision());

        // 11. Augmenter kilométrage d’un véhicule de X
        int ajoutKm = 1000;
        Action<Vehicule> augmenterKilometrage = v -> v.setKilometrage(v.getKilometrage() + ajoutKm);
        augmenterKilometrage.executer(v1);
        System.out.println("11. Nouveau kilométrage de v1 : " + v1.getKilometrage());

        // Utilisation service sur toute la flotte
        service.appliquerSurVehicules(flotte, augmenterKilometrage);
        System.out.println("Kilométrage après +1000 sur toute la flotte :");
        for (Vehicule v : flotte) {
            System.out.println(v.getImmatriculation() + " -> " + v.getKilometrage() + " km");
        }

        // 12. Terminer une location (mettre dateFin)
        Action<Location> terminerLocation = l -> l.setDateFin("2026-03-27");
        terminerLocation.executer(loc1);
        System.out.println("\n12. Date fin location : " + loc1.getDateFin());

        // =========================================================
        // D — COMPARAISONS / TRI
        // =========================================================

        // 13. Comparer deux véhicules par kilométrage
        Comparateur<Vehicule> comparerParKilometrage =
                (a, b) -> a.getKilometrage() - b.getKilometrage();

        service.trierVehicules(flotte, comparerParKilometrage);
        System.out.println("\n13. Flotte triée par kilométrage :");
        for (Vehicule v : flotte) {
            System.out.println(v);
        }

        // 14. Comparer deux véhicules par immatriculation
        Comparateur<Vehicule> comparerParImmatriculation =
                (a, b) -> a.getImmatriculation().compareTo(b.getImmatriculation());

        service.trierVehicules(flotte, comparerParImmatriculation);
        System.out.println("\n14. Flotte triée par immatriculation :");
        for (Vehicule v : flotte) {
            System.out.println(v);
        }

        System.out.println("\n===== FIN DES TESTS =====");
    }
}