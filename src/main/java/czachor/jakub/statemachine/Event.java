package czachor.jakub.statemachine;

@FunctionalInterface
public interface Event {
    void transition();
}
