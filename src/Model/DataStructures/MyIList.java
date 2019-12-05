package Model.DataStructures;

public interface MyIList<T> {
    boolean isEmpty();
    void add(T el);
    void clear();
    T get(int index);
    int size();
}
