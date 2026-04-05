package sn.coumba.l2gl.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;

import sn.coumba.l2gl.app.model.Action;
import sn.coumba.l2gl.app.model.Comparaison;
import sn.coumba.l2gl.app.model.Entretien;
import sn.coumba.l2gl.app.model.Test;
import sn.coumba.l2gl.app.model.Transformation;
import sn.coumba.l2gl.app.model.Vehicule;

public class ParcAutoService {

    private List<Vehicule> vehicules;
    private Map<String, Vehicule> indexParImmat;
    private Map<Long, List<Entretien>> entretiensParVehiculeId;

    public ParcAutoService() {
        vehicules = new ArrayList<>();
        indexParImmat = new HashMap<>();
        entretiensParVehiculeId = new HashMap<>();
    }
    public boolean ajouterVehicule(Vehicule v) {
        if (v == null || v.getImmatriculation() == null || v.getImmatriculation().isEmpty()) {
            return false;
        }

        if (indexParImmat.containsKey(v.getImmatriculation())) {
            return false;
        }
        vehicules.add(v);
        indexParImmat.put(v.getImmatriculation(), v);
        return true;
    }
    public boolean supprimerVehicule(String immat) {
        Vehicule v = indexParImmat.get(immat);

        if (v == null) {
            return false;
        }
        vehicules.remove(v);
        indexParImmat.remove(immat);

        // On supprime aussi ses entretiens
        entretiensParVehiculeId.remove(v.getId());

        return true;
    }
    public Vehicule rechercher(String immat) {
        return indexParImmat.get(immat);
    }
    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    public Set<Vehicule> vehiculesUniques() {
        return new HashSet<>(vehicules);
    }

    // =========================
    // GESTION DES ENTRETIENS
    // =========================

    public void ajouterEntretien(Entretien e) {
        if (e == null || e.getVehiculeId() == null) {
            return;
        }

        List<Entretien> liste = entretiensParVehiculeId.get(e.getVehiculeId());

        if (liste == null) {
            liste = new ArrayList<>();
            entretiensParVehiculeId.put(e.getVehiculeId(), liste);
        }

        liste.add(e);
    }

    public List<Entretien> getEntretiens(Long vehiculeId) {
        List<Entretien> liste = entretiensParVehiculeId.get(vehiculeId);

        if (liste == null) {
            return new ArrayList<>();
        }

        return liste;
    }

    // =========================
    // MÉTHODES FONCTIONNELLES
    // =========================

    public List<Vehicule> filtrerVehicules(List<Vehicule> src, Test<Vehicule> regle) {
        List<Vehicule> resultat = new ArrayList<>();

        for (Vehicule v : src) {
            if (regle.tester(v)) {
                resultat.add(v);
            }
        }
        return resultat;
    }
    public List<String> mapperVehicules(List<Vehicule> src, Transformation<Vehicule, String> f) {
        List<String> resultat = new ArrayList<>();

        for (Vehicule v : src) {
            resultat.add(f.transformer(v));
        }

        return resultat;
    }
    public void appliquerSurVehicules(List<Vehicule> src, Action<Vehicule> action) {
        for (Vehicule v : src) {
            action.executer(v);
        }
    }
    public void trierVehicules(List<Vehicule> src, Comparaison<Vehicule> cmp) {
        for (int i = 0; i < src.size() - 1; i++) {
            for (int j = 0; j < src.size() - 1 - i; j++) {
                if (cmp.comparer(src.get(j), src.get(j + 1)) > 0) {
                    Vehicule temp = src.get(j);
                    src.set(j, src.get(j + 1));
                    src.set(j + 1, temp);
                }
            }
        }
    }
}
    // =========================
    // MÉTHODES AVEC STREAM
    // =========================

    public List<Vehicule> getVehiculesDisponiblesStream() {
        return vehicules.stream()
                .filter(Vehicule::isDisponible)
                .collect(Collectors.toList());
    }

    public List<String> getImmatriculationsTrieesStream() {
        return vehicules.stream()
                .map(Vehicule::getImmatriculation)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Vehicule> getTop3KilometrageStream() {
        return vehicules.stream()
                .sorted((v1, v2) -> Integer.compare(v2.getKilometrage(), v1.getKilometrage()))
                .limit(3)
                .collect(Collectors.toList());
    }

    // =========================
        // STATISTIQUES & REGROUPEMENTS
        // =========================
    public double kilometrageMoyen() {
            return vehicules.stream()
                    .mapToInt(Vehicule::getKilometrage)
                    .average()
                    .orElse(0.0);
        }
    public Map<String, Long> nombreParEtat() {
            return vehicules.stream()
                 .collect(Collectors.groupingBy(
                     v -> {
                        if (v.isEnPanne()) return "En panne";
                         else if (v.isDisponible()) return "Disponible";
                         else return "Indisponible";
                        },
                            Collectors.counting()
        ));
        }
        public Map<Long, Integer> coutsEntretienParVehicule() {
            return entretiensParVehiculeId.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> e.getValue().stream()
                                .mapToInt(Entretien::getCout)
                                .sum()
                    ));
        }