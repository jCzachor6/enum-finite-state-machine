package czachor.jakub.statemachine.models;

@FunctionalInterface
public interface Condition {
    Condition always = () -> true;
    Condition never = () -> false;

    boolean condition();
}
