package czachor.jakub.statemachine;

@FunctionalInterface
public interface Condition {
    Condition always = () -> true;
    Condition never = () -> false;

    boolean condition();
}
