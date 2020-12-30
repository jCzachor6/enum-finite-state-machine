package czachor.jakub.statemachine.validator;

import java.util.List;

public class StatesValidator {
    public static void validateStatesCollection(String states, List<?> collection) throws IllegalArgumentException {
        if (isNullOrEmpty(collection)) {
            throw new IllegalArgumentException(String.format("Parameter %s can not be empty. ", states));
        }
    }

    private static boolean isNullOrEmpty(final List<?> c) {
        return c == null || c.isEmpty();
    }
}
