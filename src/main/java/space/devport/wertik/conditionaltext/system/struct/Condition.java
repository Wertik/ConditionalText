package space.devport.wertik.conditionaltext.system.struct;

import lombok.Getter;
import org.bukkit.entity.Player;
import space.devport.wertik.conditionaltext.ConditionalTextPlugin;
import space.devport.wertik.conditionaltext.system.struct.operator.impl.ObjectOperatorFunction;
import space.devport.wertik.conditionaltext.system.utils.PlaceholderUtil;

public class Condition {

    @Getter
    private final Object required;
    private final ObjectOperatorFunction operator;

    public Condition(Object required, ObjectOperatorFunction operator) {
        this.required = required;
        this.operator = operator;
    }

    public boolean check(Object value, Player... player) {
        ConditionalTextPlugin.getInstance().getConsoleOutput().debug("Current value: " + value.toString() + ", required: " + required);

        Object required = this.required;
        if (required instanceof String && player.length > 0)
            required = PlaceholderUtil.parsePlaceholder(player[0], (String) required);

        return operator.apply(value, required);
    }
}