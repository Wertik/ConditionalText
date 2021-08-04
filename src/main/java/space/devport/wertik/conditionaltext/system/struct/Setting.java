package space.devport.wertik.conditionaltext.system.struct;

import lombok.Getter;
import lombok.extern.java.Log;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;
import space.devport.dock.configuration.Configuration;
import space.devport.dock.util.StringUtil;
import space.devport.wertik.conditionaltext.system.utils.PlaceholderUtil;

import java.util.LinkedList;
import java.util.List;

@Log
public class Setting {

    @Getter
    private final String placeholder;

    private final List<Rule> rules = new LinkedList<>();

    public Setting(String placeholder) {
        this.placeholder = placeholder;
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
            log.fine(String.format("Loaded rule %s", rule));
        }

        return setting;
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    // Process the given value and output the formatted text based on rules.
    @Nullable
    public String process(@Nullable OfflinePlayer player, Object value, String... arguments) {
        String output = process(player, value);

        output = parseArguments(output, arguments);

        return StringUtil.color(output != null ? PlaceholderAPI.setPlaceholders(player, output) : null);
    }

    @Nullable
    public String process(@Nullable OfflinePlayer player, String valueString, String... arguments) {
        Object value = PlaceholderUtil.parsePlaceholderIntoObject(player, valueString);
        return process(player, value, arguments);
    }

    // Process the Setting placeholder and output the formatted text based on rules.
    @Nullable
    public String process(@Nullable OfflinePlayer player, String... arguments) {
        String placeholder = parseArguments(this.placeholder, arguments);
        Object value = PlaceholderUtil.parsePlaceholderIntoObject(player, placeholder);
        return process(player, value, arguments);
    }

    // Run rules for given Object value.
    // Return the raw output.
    @Nullable
    public String process(@Nullable OfflinePlayer player, Object value) {
        for (Rule rule : rules) {
            if (rule.check(value, player))
                return rule.getOutput();
        }
        return null;
    }

    private String parseArguments(String string, String[] arguments) {
        for (int n = 0; n < arguments.length; n++) {
            String argument = arguments[n];
            string = string.replace("$" + n, argument);
        }
        return string;
    }
}