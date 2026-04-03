package src.sn.coumba.l2gl.app.model;

@FunctionalInterface
public interface Comparaison<T> {
    int comparer(T objet1, T objet2);
}