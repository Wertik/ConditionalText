package space.devport.wertik.conditionaltext.system.utils;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import space.devport.utils.ConsoleOutput;
import space.devport.wertik.conditionaltext.ConditionalTextPlugin;
import space.devport.wertik.conditionaltext.exceptions.InvalidOperatorException;
import space.devport.wertik.conditionaltext.system.struct.Condition;
import space.devport.wertik.conditionaltext.system.struct.Rule;
import space.devport.wertik.conditionaltext.system.struct.operator.Operators;
import space.devport.wertik.conditionaltext.system.struct.operator.struct.impl.OperatorWrapper;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@UtilityClass
public class ParserUtil {

    @NotNull
    public Rule parseRule(String input) throws InvalidOperatorException {

        // No condition specified.
        if (!input.contains(";")) {
            return new Rule(input);
        }

        String[] arr = input.split(";");

        Condition condition = parseCondition(arr[0]);

        return new Rule(arr.length > 1 ? arr[1] : "", condition);
    }

    @NotNull
    public Condition parseCondition(String input) throws InvalidOperatorException {

        OperatorWrapper operator = null;

        for (String sign : Operators.operatorFunctions.keySet()) {

            if (input.startsWith(sign)) {
                input = input.replace(sign, "");
                operator = new OperatorWrapper(sign, Operators.getFunction(sign));
            }
        }

        if (operator == null)
            throw new InvalidOperatorException(input);

        return new Condition(parseObject(input), operator);
    }

    @NotNull
    public Object parseObject(String input) {

        Object out = null;

        try {
            out = Integer.parseInt(input);
        } catch (NumberFormatException ignored) {
        }

        if (out == null)
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException ignored) {
            }

        if (out == null)
            try {
                return LocalTime.parse(input, ConditionalTextPlugin.getInstance().getTimeFormatter());
            } catch (DateTimeParseException ignored) {
            }

        if (out == null) out = input;

        ConsoleOutput.getInstance().debug("Object parsed: " + input + " -> " + out.toString() + " (" + out.getClass().getSimpleName() + ")");
        return out;
    }
}