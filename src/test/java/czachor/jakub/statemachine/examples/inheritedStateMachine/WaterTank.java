package czachor.jakub.statemachine.examples.inheritedStateMachine;

import czachor.jakub.statemachine.models.Condition;
import czachor.jakub.statemachine.models.Behaviour;
import czachor.jakub.statemachine.StateMachine;
import czachor.jakub.statemachine.examples.WaterTankStates;
import czachor.jakub.statemachine.models.Event;

public class WaterTank extends StateMachine<WaterTankStates> {
    private int water = 0;

    public WaterTank() {
        super(WaterTankStates.EMPTY);
        setTransition(WaterTankStates.EMPTY, WaterTankStates.FILLING_UP, Event.empty());
        setTransition(WaterTankStates.FILLING_UP, WaterTankStates.FILLING_UP, Event.conditionedBehaviour(() -> water < 500, () -> water += 100)) ;
        setTransition(WaterTankStates.FILLING_UP, WaterTankStates.FULL, Event.conditionedBehaviour(() -> water == 500, Behaviour.none));
    }

    public int getWater() {
        return water;
    }
}
