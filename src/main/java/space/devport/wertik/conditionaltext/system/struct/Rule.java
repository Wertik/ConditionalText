package space.devport.wertik.conditionaltext.system.struct;

import lombok.Getter;
import space.devport.utils.text.StringUtil;

public class Rule {

    @Getter
    private final String output;

    @Getter
    private Condition condition;

    public Rule(String output, Condition condition) {
        this.output = output;
        this.condition = condition;
    }

    public Rule(String output) {
        this.output = output;
    }

    public boolean check(Object value) {
        return !hasCondition() || condition.check(value);
    }

    public String getOutputFormatted() {
        return StringUtil.color(output);
    }

    public boolean hasCondition() {
        return condition != null;
    }

    @Override
    public String toString() {
        return output + ";" + (hasCondition() ? condition.getRequired().toString() : "null");
    }
}