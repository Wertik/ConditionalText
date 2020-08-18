package space.devport.wertik.conditionaltext.system.struct;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import space.devport.wertik.conditionaltext.ConditionalTextPlugin;
import space.devport.wertik.conditionaltext.system.utils.PlaceholderUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Setting {

    @Getter
    private final String placeholder;

    @Getter
    private List<String> rawRules = new ArrayList<>();

    private final List<Rule> rules = new LinkedList<>();

    public Setting(String placeholder) {
        this.placeholder = placeholder;
    }

    public Setting(String placeholder, List<String> rawRules) {
        this.placeholder = placeholder;
        this.rawRules = rawRules;
    }

    public void addRule(Rule rule) {
        this.rules.add(rule);
    }

    /**
     * Process the Setting and output the formatted text based on rules.
     */
    @Nullable
    public String process(Player player) {
        for (Rule rule : rules) {

            ConditionalTextPlugin.getInstance().getConsoleOutput().debug("Checking rule " + rule.toString());

            Object value = PlaceholderUtil.parsePlaceholder(player, placeholder);

            if (rule.check(value))
                return rule.getOutputFormatted();
        }
        return null;
    }
}