package czachor.jakub.statemachine.models;

public class Event {
    private Condition condition;
    private Behaviour behaviour;

    private Event() {
    }

    public static Event empty() {
        return Event.conditionedBehaviour(Condition.always, Behaviour.none);
    }

    public static Event conditionedBehaviour(Condition c, Behaviour b) {
        Event event = new Event();
        event.condition = c;
        event.behaviour = b;
        return event;
    }

    public Condition getCondition() {
        return condition;
    }

    public Behaviour getBehaviour() {
        return behaviour;
    }
}
