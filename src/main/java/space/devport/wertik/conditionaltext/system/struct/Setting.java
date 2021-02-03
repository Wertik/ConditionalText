package space.devport.wertik.conditionaltext.system.struct;

import lombok.Getter;
import lombok.extern.java.Log;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import space.devport.utils.configuration.Configuration;
import space.devport.utils.logging.DebugLevel;
import space.devport.utils.text.StringUtil;
import space.devport.wertik.conditionaltext.system.utils.ParserUtil;
import space.devport.wertik.conditionaltext.system.utils.PlaceholderUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Log
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

    @Nullable
    public static Setting load(Configuration config, String path) {
        ConfigurationSection section = config.getFileConfiguration().getConfigurationSection(path);

        if (section == null) {
            log.warning(String.format("Could not load setting from %s, section does not exist.", config.composePath(path)));
            return null;
        }

        String placeholder = section.getString("placeholder");

        if (placeholder == null) {
            log.warning(String.format("Could not load setting %s, there's no placeholder defined at %s.", section.getName(), config.composePath(path + ".placeholder")));
            return null;
        }

        Setting setting = new Setting(placeholder);

        for (String ruleString : section.getStringList("rules")) {
            Rule rule = Rule.fromString(ruleString);

            if (rule == null) {
                log.warning(String.format("Could not load rule from %s at %s, it has an invalid operator.", ruleString, config.composePath(path + ".rules")));
                continue;
            }

            setting.addRule(rule);
            log.log(DebugLevel.DEBUG, String.format("Loaded rule %s", rule.toString()));
        }

        return setting;
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    //Process the Setting and output the formatted text based on rules.
    @Nullable
    public String process(Player player, String... arguments) {
        String placeholder = parseArguments(this.placeholder, arguments);

        String output = process(PlaceholderUtil.parsePlaceholderIntoObject(player, placeholder), player);

        output = parseArguments(output, arguments);

        return StringUtil.color(output != null ? PlaceholderAPI.setPlaceholders(player, output) : null);
    }

    private String parseArguments(String string, String[] arguments) {
        for (int n = 0; n < arguments.length; n++) {
            String argument = arguments[n];
            string = string.replace("$" + n, argument);
        }
        return string;
    }

    @Nullable
    public String process(String value) {
        return process(ParserUtil.parseObject(value));
    }

    @Nullable
    public String process(Object value, @Nullable Player player) {
        for (Rule rule : rules) {
            if (rule.check(value, player))
                return rule.getOutputFormatted();
        }
        return null;
    }

    @Nullable
    public String process(Object value) {
        return process(value, null);
    }
}