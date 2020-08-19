package space.devport.wertik.conditionaltext.system.struct;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import space.devport.utils.text.StringUtil;
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
    public String process(Player player, String... arguments) {
        String placeholder = parseArguments(this.placeholder, arguments);

        String output = process(PlaceholderUtil.parsePlaceholder(player, placeholder));

        output = parseArguments(output, arguments);

        return StringUtil.color(output != null ? PlaceholderAPI.setPlaceholders(player, output) : null);
    }

    private String parseArguments(String string, String... arguments) {
        for (int n = 0; n < arguments.length; n++) {
            String argument = arguments[n];
            string = string.replace("$" + n, argument);
        }
        return string;
    }

    @Nullable
    public String process(String stringValue) {
        return process(PlaceholderUtil.parseObject(stringValue));
    }

    @Nullable
    public String process(Object value) {
        for (Rule rule : rules) {
            ConditionalTextPlugin.getInstance().getConsoleOutput().debug("Checking rule " + rule.toString());

            if (rule.check(value))
                return rule.getOutputFormatted();
        }
        return null;
    }
}