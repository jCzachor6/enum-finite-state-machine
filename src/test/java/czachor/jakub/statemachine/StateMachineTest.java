package czachor.jakub.statemachine;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class StateMachineTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldSetInitializedState() {
        StateMachine<TestStates> testMachine = new StateMachine<>(TestStates.ONE);
        assertEquals(testMachine.state(), TestStates.ONE);
    }

    @Test
    public void shouldBeSameGenericClass() {
        StateMachine<TestStates> testMachine = new StateMachine<>(TestStates.ONE);
        assertEquals(testMachine.state().getClass(), TestStates.ONE.getClass());
    }

    @Test
    public void setTransitionShouldFindOnlySelfTransition() {
        StateMachine<TestStates> testMachine = new StateMachine<>(TestStates.ONE);
        testMachine.setTransition(TestStates.ONE, TestStates.ONE, () -> true, () -> {});
        List<TestStates> list = testMachine.availableTransitions();
        assertEquals(1, list.size());
        assertEquals(TestStates.ONE, list.get(0));
    }

    @Test
    public void setTransitionShouldFindOnlySelfTransition2() {
        StateMachine<TestStates> testMachine = new StateMachine<TestStates>(TestStates.ONE);
        StateMachine.Transition t = testMachine.transition(TestStates.ONE, () -> true, () -> {});
        testMachine.setTransition(TestStates.ONE, t);
        List<TestStates> list = testMachine.availableTransitions();
        assertEquals(1, list.size());
        assertEquals(TestStates.ONE, list.get(0));
    }

    @Test
    public void availableTransitionsShouldFindNothing() {
        StateMachine<TestStates> testMachine = new StateMachine<>(TestStates.ONE);
        List<TestStates> list = testMachine.availableTransitions();
        assertEquals(0, list.size());
    }

    @Test
    public void availableTransitionsShouldFindNothingFalseCondition() {
        StateMachine<TestStates> testMachine = new StateMachine<>(TestStates.ONE);
        testMachine.setTransition(TestStates.ONE, TestStates.ONE, () -> false, () -> {});
        List<TestStates> list = testMachine.availableTransitions();
        assertEquals(0, list.size());
    }

    @Test
    public void availableTransitionsShouldFindTwo() {
        StateMachine<TestStates> testMachine = new StateMachine<>(TestStates.ONE);
        testMachine.setTransition(TestStates.ONE, TestStates.ONE, () -> true, () -> {});
        testMachine.setTransition(TestStates.ONE, TestStates.TWO, () -> true, () -> {});
        List<TestStates> list = testMachine.availableTransitions();
        assertEquals(2, list.size());
    }

    @Test
    public void firstAvailableShouldReturnEmptyOptional() {
        StateMachine<TestStates> testMachine = new StateMachine<>(TestStates.ONE);
        testMachine.setTransition(TestStates.ONE, TestStates.ONE, () -> false, () -> {});
        Optional<TestStates> ts = testMachine.firstAvailableTransition();
        assertFalse(ts.isPresent());
    }

    @Test
    public void firstAvailableShouldReturnNonEmptyOptional() {
        StateMachine<TestStates> testMachine = new StateMachine<>(TestStates.ONE);
        testMachine.setTransition(TestStates.ONE, TestStates.TWO, () -> true, () -> {});
        Optional<TestStates> ts = testMachine.firstAvailableTransition();
        assertTrue(ts.isPresent());
        assertEquals(TestStates.TWO, ts.get());
    }

    @Test
    public void shouldSelfTickWork() {
        AtomicInteger i = new AtomicInteger();
        StateMachine<TestStates> testMachine = new StateMachine<>(TestStates.ONE);
        testMachine.setTransition(TestStates.ONE, TestStates.TWO, () -> true, i::getAndIncrement);
        testMachine.tick();
        assertEquals(1, i.get());
    }

    @Test
    public void shouldSelfTickNotWork() {
        AtomicInteger i = new AtomicInteger();
        StateMachine<TestStates> testMachine = new StateMachine<>(TestStates.ONE);
        testMachine.setTransition(TestStates.ONE, TestStates.TWO, () -> false, i::getAndIncrement);
        testMachine.tick();
        assertEquals(0, i.get());
    }

    @Test
    public void shouldChangeState() {
        StateMachine<TestStates> testMachine = new StateMachine<>(TestStates.ONE);
        testMachine.setTransition(TestStates.ONE, TestStates.TWO, () -> true, ()->{});
        testMachine.tick();
        assertEquals(TestStates.TWO, testMachine.state());
    }

    @Test
    public void shouldChangeStateNotOnTransitions() {
        StateMachine<TestStates> testMachine = new StateMachine<>(TestStates.ONE);
        testMachine.tick(TestStates.TWO);
        assertEquals(TestStates.TWO, testMachine.state());
    }

    @Test
    public void shouldCreateTransition() {
        StateMachine<TestStates> testMachine = new StateMachine<>(TestStates.ONE);
        assertNotNull(testMachine.transition(TestStates.ONE, () -> true, () -> {}));
    }

    private enum TestStates {
        ONE,
        TWO,
        THREE
    }
}
