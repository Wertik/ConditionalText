package space.devport.wertik.conditionaltext.system.struct;

import lombok.Getter;
import lombok.extern.java.Log;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;
import space.devport.dock.configuration.Configuration;
import space.devport.dock.util.StringUtil;
import space.devport.wertik.conditionaltext.system.utils.ParseUtil;
import space.devport.wertik.conditionaltext.system.utils.PlaceholderUtil;

import java.util.Collection;
import java.util.Collections;
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

    public Collection<Rule> getRules() {
        return Collections.unmodifiableList(this.rules);
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    // Process the given value and output the formatted text based on rules.
    @Nullable
    public String process(@Nullable OfflinePlayer player, Object value, String... arguments) {

        // Actually process the value into an output
        String output = null;

        for (Rule rule : rules) {
            if (rule.check(value, player, arguments)) {
                output = rule.getOutput();
                break;
            }
        }

        if (output == null) {
            return null;
        }

        // Parse arguments again (in case there were some in the output)
        output = ParseUtil.parseArguments(output, arguments);

        // Parse PAPI again and color the output.
        return StringUtil.color(PlaceholderAPI.setPlaceholders(player, output));
    }

    @Nullable
    public String process(@Nullable OfflinePlayer player, String valueString, String... arguments) {
        Object value = PlaceholderUtil.parsePlaceholderIntoObject(player, valueString);

        log.fine(String.format("Parsed: '%s' -> (%s)", placeholder, value.getClass().getSimpleName()));

        return process(player, value, arguments);
    }

    // Process the Setting placeholder and output the formatted text based on rules.
    @Nullable
    public String process(@Nullable OfflinePlayer player, String... arguments) {

        // Replace $n
        String placeholder = ParseUtil.parseArguments(this.placeholder, arguments);

        // Parse the placeholder into a comparable object.
        Object value = PlaceholderUtil.parsePlaceholderIntoObject(player, placeholder);

        return process(player, value, arguments);
    }
}