package space.devport.wertik.conditionaltext.system.struct.operator;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;
import space.devport.utils.ConsoleOutput;
import space.devport.wertik.conditionaltext.system.struct.operator.impl.ObjectOperatorFunction;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class Operators {

    public Map<String, ObjectOperatorFunction> operatorFunctions = new HashMap<>();

    static {
        operatorFunctions.put("<", (input, required) -> {
            ConsoleOutput.getInstance().debug("Operator < values: " + input.toString() + " - " + required.toString());

            // Numbers
            if (input instanceof Number && required instanceof Number) {
                ConsoleOutput.getInstance().debug("They're both numbers.");
                return ((Number) input).floatValue() < ((Number) required).floatValue();
            }

            // Time
            if (input instanceof LocalTime && required instanceof LocalTime) {
                ConsoleOutput.getInstance().debug("Both are times.");
                return ((LocalTime) input).isBefore((LocalTime) required);
            }

            return false;
        });

        operatorFunctions.put(">", (input1, input2) -> {
            if (input1 instanceof Number && input2 instanceof Number) {
                return ((Number) input1).floatValue() > ((Number) input2).floatValue();
            }
            return false;
        });

        operatorFunctions.put(">=", (input1, input2) -> {
            if (input1 instanceof Number && input2 instanceof Number) {
                return ((Number) input1).floatValue() >= ((Number) input2).floatValue();
            }
            return false;
        });

        operatorFunctions.put("<=", (input1, input2) -> {
            if (input1 instanceof Number && input2 instanceof Number) {
                return ((Number) input1).floatValue() <= ((Number) input2).floatValue();
            }
            return false;
        });

        operatorFunctions.put("=", (input1, input2) -> {
            if (input1 instanceof Number && input2 instanceof Number) {
                return ((Number) input1).floatValue() == ((Number) input2).floatValue();
            }

            if (input1 instanceof String && input2 instanceof String) {
                return ((String) input1).equalsIgnoreCase((String) input2);
            }

            return false;
        });

        operatorFunctions.put("!=", (input1, input2) -> {
            if (input1 instanceof Number && input2 instanceof Number) {
                return ((Number) input1).floatValue() != ((Number) input2).floatValue();
            }

            if (input1 instanceof String && input2 instanceof String) {
                return !((String) input1).equalsIgnoreCase((String) input2);
            }

            return false;
        });
    }

    @Nullable
    public ObjectOperatorFunction getFunction(String str) {
        for (Map.Entry<String, ObjectOperatorFunction> entry : operatorFunctions.entrySet())
            if (entry.getKey().equalsIgnoreCase(str))
                return entry.getValue();
        return null;
    }
}