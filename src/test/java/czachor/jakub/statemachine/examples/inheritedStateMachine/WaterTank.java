package czachor.jakub.statemachine.examples.inheritedStateMachine;

import czachor.jakub.statemachine.Condition;
import czachor.jakub.statemachine.Event;
import czachor.jakub.statemachine.StateMachine;
import czachor.jakub.statemachine.examples.WaterTankStates;

public class WaterTank extends StateMachine<WaterTankStates> {
    private int water = 0;

    public WaterTank() {
        super(WaterTankStates.EMPTY);
        setTransition(WaterTankStates.EMPTY, WaterTankStates.FILLING_UP, Condition.always, Event.none);
        setTransition(WaterTankStates.FILLING_UP, WaterTankStates.FILLING_UP, () -> water < 500, () -> water += 100) ;
        setTransition(WaterTankStates.FILLING_UP, WaterTankStates.FULL, () -> water == 500, Event.none);
    }

    public int getWater() {
        return water;
    }
}
