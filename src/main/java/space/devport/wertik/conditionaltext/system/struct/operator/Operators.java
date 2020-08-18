package space.devport.wertik.conditionaltext.system.struct.operator;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;
import space.devport.wertik.conditionaltext.ConditionalTextPlugin;
import space.devport.wertik.conditionaltext.system.struct.operator.impl.ObjectOperatorFunction;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class Operators {

    public Map<String, ObjectOperatorFunction> operatorFunctions = new HashMap<>();

    //TODO Throw exception when it's not applicable
    static {
        operatorFunctions.put("<", (input1, input2) -> {
            ConditionalTextPlugin.getInstance().getConsoleOutput().debug("Operator < values: " + input1.toString() + " - " + input2.toString());
            if (input1 instanceof Number && input2 instanceof Number) {
                ConditionalTextPlugin.getInstance().getConsoleOutput().debug("They're both numbers.");
                return ((Number) input1).floatValue() < ((Number) input2).floatValue();
            }
            return false;
        });

        operatorFunctions.put(">", (input1, input2) -> {
            if (input1 instanceof Number && input2 instanceof Number) {
                return ((Number) input1).floatValue() > ((Number) input2).floatValue();
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