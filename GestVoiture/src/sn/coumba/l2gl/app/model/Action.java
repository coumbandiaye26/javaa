package src.sn.coumba.l2gl.app.model;

@FunctionalInterface
public interface Action<T> {
    void executer(T objet);
}