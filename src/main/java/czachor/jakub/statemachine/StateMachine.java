package czachor.jakub.statemachine;

import java.util.*;
import java.util.stream.Collectors;

public class StateMachine<T extends Enum<T>> {
    private T currentState;
    private final Map<T, List<Transition>> mappedTransitions;

    public StateMachine(T startingState) {
        this.mappedTransitions = new HashMap<>();
        this.currentState = startingState;
    }

    public StateMachine<T> setTransition(T from, T to, Condition condition, Event event) {
        return setTransition(from, new Transition(to, condition, event));
    }

    public StateMachine<T> setTransition(List<T> fromStates, T to, Condition condition, Event event) throws Exception {
        StatesValidator.validateStatesCollection("from", fromStates);
        fromStates.forEach(state -> setTransition(state, new Transition(to, condition, event)));
        return this;
    }

    public StateMachine<T> setTransition(T from, List<T> toStates, Condition condition, Event event) throws Exception {
        StatesValidator.validateStatesCollection("to", toStates);
        toStates.forEach(state -> setTransition(from, new Transition(state, condition, event)));
        return this;
    }

    public StateMachine<T> setTransition(List<T> fromStates, List<T> toStates, Condition condition, Event event) throws Exception {
        StatesValidator.validateStatesCollection("from", fromStates);
        StatesValidator.validateStatesCollection("to", toStates);
        for (T state : fromStates) {
            setTransition(state, toStates, condition, event);
        }
        return this;
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
        if (savedForCurrentState != null) {
            return savedForCurrentState
                    .stream()
                    .filter(t -> t.condition.condition())
                    .map(t -> t.to)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public StateMachine<T> tick() {
        return firstAvailableTransition().map(this::tick).orElse(this);
    }

    public StateMachine<T> tick(T to) {
        if (mappedTransitions.containsKey(currentState)) {
            mappedTransitions.get(currentState).stream().filter(t -> t.to.equals(to)).findFirst().ifPresent(t -> t.event.transition());
        }
        this.currentState = to;
        return this;
    }

    public T state() {
        return currentState;
    }

    public Transition transition(T to, Condition condition, Event event) {
        return new Transition(to, condition, event);
    }

    public Optional<T> firstAvailableTransition() {
        return availableTransitions().stream().findFirst();
    }

    public class Transition {
        public Transition(T to, Condition condition, Event event) {
            this.to = to;
            this.condition = condition;
            this.event = event;
        }

        private final T to;
        private final Condition condition;
        private final Event event;
    }
}
