package czachor.jakub.statemachine;

import czachor.jakub.statemachine.examples.WaterTankStates;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StatesValidatorTest {

    @Test(expected = Exception.class)
    public void shouldThrowExceptionOnNull() throws Exception {
        List<WaterTankStates> subject = null;
        StatesValidator.validateStatesCollection("any", subject);
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionOnEmpty() throws Exception {
        List<WaterTankStates> subject = new ArrayList<>();
        StatesValidator.validateStatesCollection("any", subject);
    }

    @Test
    public void shouldNotThrowException() throws Exception {
        List<WaterTankStates> subject = Collections.singletonList(WaterTankStates.EMPTY);
        StatesValidator.validateStatesCollection("any", subject);
    }
}
