package space.devport.wertik.conditionaltext.system.struct;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.extern.java.Log;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;
import space.devport.wertik.conditionaltext.system.struct.operator.Operators;
import space.devport.wertik.conditionaltext.system.struct.operator.impl.OperatorWrapper;
import space.devport.wertik.conditionaltext.system.utils.ParseUtil;
import space.devport.wertik.conditionaltext.system.utils.PlaceholderUtil;

@Log
public class Condition {

    @Getter
    private final Object required;
    private final OperatorWrapper operator;

    public Condition(Object required, OperatorWrapper operator) {
        this.required = required;
        this.operator = operator;
    }

    @Nullable
    public static Condition fromString(String input) {

        if (Strings.isNullOrEmpty(input))
            return null;

        OperatorWrapper operator = null;

        for (String sign : Operators.operatorFunctions.keySet()) {

            if (input.startsWith(sign)) {
                input = input.replaceFirst(sign, "");
                operator = new OperatorWrapper(sign, Operators.getFunction(sign));
                break;
            }
        }

        return operator == null ? null : new Condition(ParseUtil.parseObject(input), operator);
    }

    public boolean check(Object value, @Nullable OfflinePlayer player) {
        Object required = this.required;

        // Parse placeholders in String requirements.
        if (required instanceof String)
            required = ParseUtil.parseObject(PlaceholderUtil.parsePlaceholders(player, (String) required));

        boolean out = operator.apply(value, required);

        log.fine(String.format("'%s' (%s) %s '%s' (%s) -> %b",
                value, value.getClass().getSimpleName(), operator.getSign(), required, required.getClass().getSimpleName(), out));

        return out;
    }
}