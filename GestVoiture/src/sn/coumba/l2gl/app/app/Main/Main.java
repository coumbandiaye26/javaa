package sn.coumba.l2gl.app;

import java.util.List;
import java.util.Map;

import sn.coumba.l2gl.app.model.Vehicule;
import sn.coumba.l2gl.app.model.Entretien;
import sn.coumba.l2gl.app.service.ParcAutoService;

public class Main {
    public static void main(String[] args) {
        ParcAutoService service = new ParcAutoService();

        // ===== Étape 1 : Ajout de véhicules =====
        Vehicule v1 = new Vehicule(1L, "DK1234", 10000, true, false, 2020);
        Vehicule v2 = new Vehicule(2L, "DK5678", 50000, false, false, 2019);
        Vehicule v3 = new Vehicule(3L, "DK9999", 75000, true, true, 2018);
        Vehicule v4 = new Vehicule(4L, "DK2222", 30000, true, false, 2021);
        Vehicule v5 = new Vehicule(5L, "DK1111", 90000, false, true, 2017);

        service.ajouterVehicule(v1);
        service.ajouterVehicule(v2);
        service.ajouterVehicule(v3);
        service.ajouterVehicule(v4);
        service.ajouterVehicule(v5);

        System.out.println("=== Tous les véhicules ===");
        service.getVehicules().forEach(System.out::println);

        // ===== Étape 2 : Unicité HashSet =====
        System.out.println("\n=== Véhicules uniques (HashSet) ===");
        service.vehiculesUniques().forEach(System.out::println);

        // ===== Étape 3 : Entretiens =====
        Entretien e1 = new Entretien(101L, 1L, "Vidange", 15000);
        Entretien e2 = new Entretien(102L, 1L, "Changement pneus", 8000);
        Entretien e3 = new Entretien(103L, 2L, "Révision générale", 5000);
        service.ajouterEntretien(e1);
        service.ajouterEntretien(e2);
        service.ajouterEntretien(e3);

        System.out.println("\n=== Entretiens du véhicule 1 ===");
        service.getEntretiens(1L).forEach(System.out::println);

        // ===== Étape 4 & 5 : Streams =====
        System.out.println("\n=== Véhicules disponibles (Stream) ===");
        service.getVehiculesDisponiblesStream().forEach(System.out::println);

        System.out.println("\n=== Immatriculations triées (Stream) ===");
        service.getImmatriculationsTrieesStream().forEach(System.out::println);

        System.out.println("\n=== Top 3 véhicules par kilométrage ===");
        service.getTop3KilometrageStream().forEach(System.out::println);

        // ===== Étape 6 : Statistiques & regroupements =====
        System.out.println("\n=== Kilométrage moyen ===");
        System.out.println(service.kilometrageMoyen());

        System.out.println("\n=== Nombre de véhicules par état ===");
        Map<String, Long> parEtat = service.nombreParEtat();
        parEtat.forEach((etat, nb) -> System.out.println(etat + " : " + nb));

        System.out.println("\n=== Total des coûts d’entretien par véhicule ===");
        Map<Long, Integer> couts = service.coutsEntretienParVehicule();
        couts.forEach((id, total) -> System.out.println("Véhicule " + id + " : " + total));
    }
}