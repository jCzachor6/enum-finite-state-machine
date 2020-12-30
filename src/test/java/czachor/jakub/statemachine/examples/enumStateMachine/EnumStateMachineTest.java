package czachor.jakub.statemachine.examples.enumStateMachine;

import czachor.jakub.statemachine.models.Condition;
import czachor.jakub.statemachine.models.Behaviour;
import czachor.jakub.statemachine.StateMachine;
import czachor.jakub.statemachine.examples.WaterTankStates;
import czachor.jakub.statemachine.models.Event;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EnumStateMachineTest {
    private StateMachine<WaterTankStates> waterTank;
    private int water = 0;

    @Test
    @Before
    public void initWaterTank() {
        water = 0;
        waterTank = new StateMachine<>(WaterTankStates.EMPTY);
        waterTank
                .setTransition(WaterTankStates.EMPTY, WaterTankStates.FILLING_UP, Event.empty())
                .setTransition(WaterTankStates.FILLING_UP, WaterTankStates.FILLING_UP, Event.conditionedBehaviour(() -> water < 500, () -> water += 100))
                .setTransition(WaterTankStates.FILLING_UP, WaterTankStates.FULL, Event.conditionedBehaviour(() -> water == 500, Behaviour.none));
    }

    @Test
    public void shouldStartEmpty() {
        assertEquals(WaterTankStates.EMPTY, waterTank.state());
    }

    @Test
    public void shouldChangeToFillingUp() {
        waterTank.tick();
        assertEquals(WaterTankStates.FILLING_UP, waterTank.state());
    }

    @Test
    public void shouldIncreaseWaterLevel() {
        waterTank.tick();
        waterTank.tick();
        assertNotEquals(0, water);
    }

    @Test
    public void shouldChangeToFullAfter7Ticks() {
        for (int i = 0; i < 7; i++) {
            waterTank.tick();
        }
        assertEquals(WaterTankStates.FULL, waterTank.state());
        assertEquals(500, water);
    }
}
