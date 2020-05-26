package czachor.jakub.statemachine;

@FunctionalInterface
public interface Event {
    Event none = () -> {};

    void transition();
}
