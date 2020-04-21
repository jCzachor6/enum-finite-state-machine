package czachor.jakub.statemachine;

import java.util.*;
import java.util.stream.Collectors;

public class StateMachine<T> {
    private T currentState;
    private Map<T, List<Transition>> mappedTransitions;


    protected StateMachine(T startingState) {
        this.mappedTransitions = new HashMap<>();
        this.currentState = startingState;
    }

    public StateMachine<T> setTransition(T from, Transition transition) {
        if (mappedTransitions.containsKey(from)) {
            mappedTransitions.get(from).add(transition);
        } else {
            List<Transition> t = new ArrayList<>();
            t.add(transition);
            mappedTransitions.put(from, t);
        }
        return this;
    }

    public List<T> availableTransitions() {
        List<Transition> savedForCurrentState = mappedTransitions.get(this.currentState);
        return savedForCurrentState.stream().filter(t -> t.condition.condition()).map(t -> t.to).collect(Collectors.toList());
    }

    public StateMachine<T> tick() {
        return firstAvailableTransition().map(this::tick).orElse(this);
    }

    public StateMachine<T> setTransition(T from, T to, Condition condition, Event event) {
        Transition transition = new Transition(to, condition, event);
        return setTransition(from, transition);
    }

    public T state() {
        return currentState;
    }

    private StateMachine<T> tick(T to) {
        if (mappedTransitions.containsKey(currentState)) {
            mappedTransitions.get(currentState).stream().filter(t -> t.to.equals(to)).findFirst().ifPresent(t -> t.event.transition());
        }
        this.currentState = to;
        return this;
    }

    private Optional<T> firstAvailableTransition() {
        return availableTransitions().stream().findFirst();
    }

    public class Transition {
        public Transition(T to, Condition condition, Event event) {
            this.to = to;
            this.condition = condition;
            this.event = event;
        }

        private T to;
        private Condition condition;
        private Event event;
    }
}
