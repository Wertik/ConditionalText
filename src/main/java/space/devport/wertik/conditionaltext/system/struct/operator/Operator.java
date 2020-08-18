package space.devport.wertik.conditionaltext.system.struct.operator;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

public enum Operator {

    //TODO Make operators applicable to Strings.

    LESS("<", (x, y) -> x < y),
    GREATER(">", (x, y) -> x > y),
    LESS_OR_EQUAL("<=", (x, y) -> x <= y),
    GREATER_OR_EQUAL(">=", (x, y) -> x >= y),

    NOT_EQUAL("!=", (x, y) -> !x.equals(y)),
    EQUAL("=", Integer::equals);

    @Getter
    private final String sign;

    private final OperatorFunction<Integer, Integer, Boolean> operatorFunction;

    Operator(String sign, OperatorFunction<Integer, Integer, Boolean> operatorFunction) {
        this.sign = sign;
        this.operatorFunction = operatorFunction;
    }

    @Nullable
    public Operator fromString(String str) {
        for (Operator operator : values())
            if (operator.getSign().equalsIgnoreCase(str))
                return operator;
        return null;
    }

    public OperatorFunction<Integer, Integer, Boolean> getFunction() {
        return operatorFunction;
    }
}