package czachor.jakub.statemachine.examples.inheritedStateMachine;

import czachor.jakub.statemachine.examples.WaterTankStates;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InheritedStateMachineTest {
    private WaterTank waterTank;

    @Test
    @Before
    public void initWaterTank() {
        waterTank = new WaterTank();
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
        assertNotEquals(0, waterTank.getWater());
    }

    @Test
    public void shouldChangeToFullAfter7Ticks() {
        for (int i = 0; i < 7; i++) {
            waterTank.tick();
        }
        assertEquals(WaterTankStates.FULL, waterTank.state());
        assertEquals(500, waterTank.getWater());
    }
}
