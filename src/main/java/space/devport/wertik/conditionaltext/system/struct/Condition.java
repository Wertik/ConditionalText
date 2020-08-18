package space.devport.wertik.conditionaltext.system.struct;

import lombok.Getter;
import space.devport.wertik.conditionaltext.ConditionalTextPlugin;
import space.devport.wertik.conditionaltext.system.struct.operator.impl.ObjectOperatorFunction;

public class Condition {

    @Getter
    private final Object required;
    private final ObjectOperatorFunction operator;

    public Condition(Object required, ObjectOperatorFunction operator) {
        this.required = required;
        this.operator = operator;
    }

    public boolean check(Object value) {
        ConditionalTextPlugin.getInstance().getConsoleOutput().debug("Current value: " + value.toString() + ", required: " + required);
        return operator.apply(value, required);
    }
}