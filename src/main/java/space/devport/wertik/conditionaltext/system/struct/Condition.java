package space.devport.wertik.conditionaltext.system.struct;

import space.devport.wertik.conditionaltext.system.struct.operator.Operator;

public class Condition {

    private final int required;
    private final Operator operator;

    public Condition(int required, Operator operator) {
        this.required = required;
        this.operator = operator;
    }

    public boolean check(int value) {
        return operator.getFunction().apply(value, required);
    }
}