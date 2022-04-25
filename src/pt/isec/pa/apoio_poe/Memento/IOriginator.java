package pt.isec.pa.apoio_poe.Memento;

public interface IOriginator {
    IMemento save();
    void restore(IMemento memento);
}