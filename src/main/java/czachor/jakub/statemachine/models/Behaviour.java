package czachor.jakub.statemachine.models;

@FunctionalInterface
public interface Behaviour {
    Behaviour none = () -> {};

    void transition();
}
