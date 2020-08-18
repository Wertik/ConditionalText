package space.devport.wertik.conditionaltext.system;

import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import space.devport.utils.configuration.Configuration;
import space.devport.wertik.conditionaltext.ConditionalTextPlugin;
import space.devport.wertik.conditionaltext.exceptions.rule.InvalidOperatorException;
import space.devport.wertik.conditionaltext.system.struct.Rule;
import space.devport.wertik.conditionaltext.system.struct.Setting;
import space.devport.wertik.conditionaltext.system.utils.ParserUtil;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class SettingManager {

    private final ConditionalTextPlugin plugin;

    private final Map<String, Setting> loadedSettings = new HashMap<>();

    private Configuration settings;

    public Setting getSetting(String settingName) {
        return this.loadedSettings.getOrDefault(settingName, null);
    }

    public void loadSettings() {

        this.loadedSettings.clear();

        if (settings == null)
            settings = new Configuration(plugin, "settings");
        else
            settings.load();

        for (String settingName : settings.getFileConfiguration().getKeys(false)) {
            Setting setting = loadSetting(settingName);

            if (setting == null) continue;

            this.loadedSettings.put(settingName, setting);
        }

        plugin.getConsoleOutput().info("Loaded " + this.loadedSettings.size() + " setting(s)...");
    }

    private Setting loadSetting(String name) {
        ConfigurationSection section = settings.getFileConfiguration().getConfigurationSection(name);

        if (section == null) {
            plugin.getConsoleOutput().err("Could not load setting " + name + ", section does not exist.");
            return null;
        }

        String placeholder = section.getString("placeholder", null);

        if (placeholder == null) {
            plugin.getConsoleOutput().err("Could not load setting " + name + ", there's not placeholder defined.");
            return null;
        }

        Setting setting = new Setting(placeholder);

        for (String ruleString : section.getStringList("rules")) {

            Rule rule;
            try {
                rule = ParserUtil.parseRule(ruleString);
            } catch (InvalidOperatorException e) {
                plugin.getConsoleOutput().err("Could not parse rule " + ruleString + " in setting " + name + ", reason: " + e.getMessage());
                continue;
            }

            setting.addRule(rule);
        }

        return setting;
    }
}