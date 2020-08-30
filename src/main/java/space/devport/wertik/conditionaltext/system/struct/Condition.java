package space.devport.wertik.conditionaltext.system.struct;

import lombok.Getter;
import org.bukkit.entity.Player;
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

    public boolean check(Object value, Player... player) {
        Object required = this.required;

        if (required instanceof String && player.length > 0) {
            required = ParserUtil.parseObject(PlaceholderUtil.parsePlaceholder(player[0], (String) required));
        }

        return operator.apply(value, required);
    }
}