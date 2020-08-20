package space.devport.wertik.conditionaltext.system.utils;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import space.devport.wertik.conditionaltext.ConditionalTextPlugin;
import space.devport.wertik.conditionaltext.exceptions.InvalidOperatorException;
import space.devport.wertik.conditionaltext.system.struct.Condition;
import space.devport.wertik.conditionaltext.system.struct.Rule;
import space.devport.wertik.conditionaltext.system.struct.operator.Operators;
import space.devport.wertik.conditionaltext.system.struct.operator.impl.ObjectOperatorFunction;

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

        input = PlaceholderUtil.parsePlaceholder(null, input);

        try {
            return new Condition(Integer.parseInt(input), operator);
        } catch (NumberFormatException e) {
            ConditionalTextPlugin.getInstance().getConsoleOutput().debug("Left input is not an integer.");
        }

        try {
            return new Condition(Double.parseDouble(input), operator);
        } catch (NumberFormatException e) {
            ConditionalTextPlugin.getInstance().getConsoleOutput().debug("Left input is not a double.");
        }

        ConditionalTextPlugin.getInstance().getConsoleOutput().debug("Returning as string.");
        return new Condition(input, operator);
    }
}