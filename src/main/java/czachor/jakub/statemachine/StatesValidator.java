package czachor.jakub.statemachine;

import java.util.List;

class StatesValidator {
    static void validateStatesCollection(String states, List<?> collection) throws Exception {
        if (isNullOrEmpty(collection)) {
            throw new Exception(String.format("Parameter %s can not be empty. ", states));
        }
    }

    private static boolean isNullOrEmpty(final List<?> c) {
        return c == null || c.isEmpty();
    }
}
