package space.devport.wertik.conditionaltext.system;

import lombok.Getter;
import lombok.extern.java.Log;
import org.jetbrains.annotations.Nullable;
import space.devport.utils.configuration.Configuration;
import space.devport.utils.logging.DebugLevel;
import space.devport.wertik.conditionaltext.ConditionalTextPlugin;
import space.devport.wertik.conditionaltext.system.struct.Setting;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Log
public class SettingManager {

    private final ConditionalTextPlugin plugin;

    private final Map<String, Setting> loadedSettings = new HashMap<>();

    @Getter
    private final Configuration config;

    public SettingManager(ConditionalTextPlugin plugin) {
        this.plugin = plugin;
        this.config = new Configuration(plugin, "settings");
    }

    public void load() {
        loadedSettings.clear();
        config.load();

        for (String settingName : config.getFileConfiguration().getKeys(false)) {
            Setting setting = Setting.load(config, settingName);

            if (setting == null)
                continue;

            loadedSettings.put(settingName, setting);
            log.log(DebugLevel.DEBUG, String.format("Loaded setting %s", settingName));
        }

        log.info(String.format("Loaded %d setting(s)...", loadedSettings.size()));
    }

    @Nullable
    public Setting getSetting(String settingName) {
        return loadedSettings.get(settingName);
    }

    public Map<String, Setting> getSettings() {
        return Collections.unmodifiableMap(loadedSettings);
    }
}