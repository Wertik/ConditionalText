package space.devport.wertik.conditionaltext.system.struct;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
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

    @Nullable
    public static Rule fromString(String input) {

        // No condition specified.
        if (!input.contains(";"))
            return new Rule(input);

        String[] arr = input.split(";");

        Condition condition = Condition.fromString(arr[0]);

        return condition == null ? null : new Rule(arr.length > 1 ? arr[1] : "", condition);
    }

    public boolean check(Object value, Player... player) {
        return !hasCondition() || condition.check(value, player);
    }

    public boolean hasCondition() {
        return condition != null;
    }

    public String getOutputFormatted() {
        return StringUtil.color(output);
    }

    @Override
    public String toString() {
        return output + ";" + (hasCondition() ? condition.getRequired().toString() : "null");
    }
}