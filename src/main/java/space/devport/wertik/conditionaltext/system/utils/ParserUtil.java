package space.devport.wertik.conditionaltext.system.utils;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import space.devport.wertik.conditionaltext.exceptions.rule.InvalidOperatorException;
import space.devport.wertik.conditionaltext.exceptions.rule.InvalidValueException;
import space.devport.wertik.conditionaltext.system.struct.Condition;
import space.devport.wertik.conditionaltext.system.struct.Rule;
import space.devport.wertik.conditionaltext.system.struct.operator.Operator;

@UtilityClass
public class ParserUtil {

    @NotNull
    public Rule parseRule(String input) throws InvalidValueException, InvalidOperatorException {

        // No condition specified.
        if (!input.contains(";")) {
            return new Rule(input);
        }

        Condition condition = parseCondition(input.split(";")[0]);

        return new Rule(input.split(";")[1], condition);
    }

    @NotNull
    public Condition parseCondition(String input) throws InvalidOperatorException, InvalidValueException {

        Operator operator = null;
        for (Operator loopOperator : Operator.values()) {
            if (input.startsWith(loopOperator.getSign())) {
                operator = loopOperator;
                input = input.replace(operator.getSign(), "");
            }
        }

        if (operator == null)
            throw new InvalidOperatorException();

        int requiredValue;
        try {
            requiredValue = Integer.parseInt(input);
        } catch (NumberFormatException numberFormatException) {
            throw new InvalidValueException();
        }

        return new Condition(requiredValue, operator);
    }
}