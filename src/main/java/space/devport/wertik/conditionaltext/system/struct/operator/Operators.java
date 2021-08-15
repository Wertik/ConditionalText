package space.devport.wertik.conditionaltext.system.struct.operator;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;
import space.devport.wertik.conditionaltext.system.struct.operator.impl.ObjectOperator;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public class Operators {

    public Map<String, ObjectOperator> operatorFunctions = new LinkedHashMap<>();

    static {
        operatorFunctions.put("<=", (input, required) -> {

            if (input instanceof Number && required instanceof Number) {
                return ((Number) input).floatValue() <= ((Number) required).floatValue();
            }

            if (input instanceof LocalTime && required instanceof LocalTime) {
                return ((LocalTime) input).isBefore((LocalTime) required) || input.equals(required);
            }

            return false;
        });

        operatorFunctions.put(">=", (input, required) -> {

            if (input instanceof Number && required instanceof Number) {
                return ((Number) input).floatValue() >= ((Number) required).floatValue();
            }

            if (input instanceof LocalTime && required instanceof LocalTime) {
                return ((LocalTime) input).isAfter((LocalTime) required) || input.equals(required);
            }

            return false;
        });

        operatorFunctions.put("<", (input, required) -> {

            if (input instanceof Number && required instanceof Number) {
                return ((Number) input).floatValue() < ((Number) required).floatValue();
            }

            if (input instanceof LocalTime && required instanceof LocalTime) {
                return ((LocalTime) input).isBefore((LocalTime) required);
            }

            return false;
        });

        operatorFunctions.put(">", (input, required) -> {

            if (input instanceof Number && required instanceof Number) {
                return ((Number) input).floatValue() > ((Number) required).floatValue();
            }

            if (input instanceof LocalTime && required instanceof LocalTime) {
                return ((LocalTime) input).isAfter((LocalTime) required);
            }

            return false;
        });

        operatorFunctions.put("%", (input, required) -> {

            if (input instanceof Number && required instanceof Number) {
                return ((Number) input).floatValue() % ((Number) required).floatValue() == 0;
            }

            return false;
        });

        operatorFunctions.put("==", Objects::equals);

        operatorFunctions.put("!==", (a, b) -> !Objects.equals(a, b));

        operatorFunctions.put("=", (input, required) -> {
            if (input instanceof String && required instanceof String) {
                return ((String) input).equalsIgnoreCase((String) required);
            }

            return Objects.equals(input, required);
        });

        operatorFunctions.put("!=", (input, required) -> {
            if (input instanceof String && required instanceof String) {
                return !((String) input).equalsIgnoreCase((String) required);
            }

            return !Objects.equals(input, required);
        });

        operatorFunctions.put("empty", (input, required) -> String.valueOf(input).isEmpty());

        operatorFunctions.put("!empty", (input, required) -> !String.valueOf(input).isEmpty());
    }

    @Nullable
    public ObjectOperator getFunction(String str) {
        for (Map.Entry<String, ObjectOperator> entry : operatorFunctions.entrySet())
            if (entry.getKey().equalsIgnoreCase(str))
                return entry.getValue();
        return null;
    }
}