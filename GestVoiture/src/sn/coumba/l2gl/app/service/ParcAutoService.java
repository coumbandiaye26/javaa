package sn.<nomEtudiant>.l2gl.app.service;

import java.util.ArrayList;
import java.util.List;

import sn.<nomEtudiant>.l2gl.app.model.Action;
import sn.<nomEtudiant>.l2gl.app.model.Comparaison;
import sn.<nomEtudiant>.l2gl.app.model.Test;
import sn.<nomEtudiant>.l2gl.app.model.Transformation;
import sn.<nomEtudiant>.l2gl.app.model.Vehicule;

public class ParcAutoService {

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