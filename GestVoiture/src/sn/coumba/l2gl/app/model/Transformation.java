package sn.coumba.l2gl.app.model;

@FunctionalInterface
public interface Transformation<T, R> {
    R transformer(T objet);
}