package space.devport.wertik.conditionaltext.system.utils;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import space.devport.utils.ConsoleOutput;
import space.devport.wertik.conditionaltext.ConditionalTextPlugin;
import space.devport.wertik.conditionaltext.exceptions.InvalidOperatorException;
import space.devport.wertik.conditionaltext.system.struct.Condition;
import space.devport.wertik.conditionaltext.system.struct.Rule;
import space.devport.wertik.conditionaltext.system.struct.operator.Operators;
import space.devport.wertik.conditionaltext.system.struct.operator.impl.ObjectOperatorFunction;

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

        Condition condition = parseCondition(input.split(";")[0]);

        return new Rule(input.split(";")[1], condition);
    }

    @NotNull
    public Condition parseCondition(String input) throws InvalidOperatorException {

        ObjectOperatorFunction operator = null;

        for (String sign : Operators.operatorFunctions.keySet()) {

            if (input.startsWith(sign)) {
                operator = Operators.getFunction(sign);
                input = input.replace(sign, "");
            }
        }

        if (operator == null)
            throw new InvalidOperatorException();

        return new Condition(parseObject(input), operator);
    }

    @NotNull
    public Object parseObject(String input) {

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            ConsoleOutput.getInstance().debug("Input is not an integer.");
        }

        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            ConsoleOutput.getInstance().debug("Input is not a double.");
        }

        try {
            return LocalTime.parse(input, ConditionalTextPlugin.getInstance().getTimeFormatter());
        } catch (DateTimeParseException e) {
            ConsoleOutput.getInstance().debug("Input is not a time.");
        }

        ConsoleOutput.getInstance().debug("Returning as a string.");
        return input;
    }
}