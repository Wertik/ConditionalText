package space.devport.wertik.conditionaltext.system.struct;

import com.google.common.base.Strings;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.devport.wertik.conditionaltext.system.struct.operator.Operators;
import space.devport.wertik.conditionaltext.system.struct.operator.struct.impl.OperatorWrapper;
import space.devport.wertik.conditionaltext.system.utils.ParserUtil;
import space.devport.wertik.conditionaltext.system.utils.PlaceholderUtil;

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
                input = input.replace(sign, "");
                operator = new OperatorWrapper(sign, Operators.getFunction(sign));
                break;
            }
        }

        return operator == null ? null : new Condition(ParserUtil.parseObject(input), operator);
    }

    public boolean check(Object value, @Nullable Player player) {
        Object required = this.required;

        // Parse placeholders in String requirements
        if (required instanceof String && player != null)
            required = ParserUtil.parseObject(PlaceholderUtil.parsePlaceholder(player, (String) required));

        return operator.apply(value, required);
    }
}