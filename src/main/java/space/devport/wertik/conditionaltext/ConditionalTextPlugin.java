package space.devport.wertik.conditionaltext;

import lombok.Getter;
import lombok.extern.java.Log;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import space.devport.utils.DevportPlugin;
import space.devport.utils.UsageFlag;
import space.devport.utils.utility.DependencyUtil;
import space.devport.utils.utility.VersionUtil;
import space.devport.wertik.conditionaltext.commands.ConditionalTextCommand;
import space.devport.wertik.conditionaltext.system.SettingManager;

import java.time.format.DateTimeFormatter;

@Log
public class ConditionalTextPlugin extends DevportPlugin {

    @Getter
    private static ConditionalTextPlugin instance;

    @Getter
    private final SettingManager settingManager = new SettingManager(this);

    @Getter
    private DateTimeFormatter timeFormatter;

    @Getter
    private String placeholderIdentifier;

    @Override
    public void onPluginEnable() {
        instance = this;

        loadOptions();

        settingManager.load();

        registerPlaceholders();

        registerMainCommand(new ConditionalTextCommand(this));

        new ConditionalTextLanguage(this).register();
    }

    private void loadOptions() {
        this.timeFormatter = DateTimeFormatter.ofPattern(configuration.getString("formats.time", "H:m:s"));
        this.placeholderIdentifier = configuration.getString("placeholder-identifier", "conditionaltext");
    }

    private void unregisterPlaceholders() {
        if (!DependencyUtil.isEnabled("PlaceholderAPI"))
            return;

        PlaceholderAPIPlugin placeholderAPI = PlaceholderAPIPlugin.getInstance();

        // Unregister is only present on 2.10.9 and above.
        if (VersionUtil.compareVersions(placeholderAPI.getDescription().getVersion(), "2.10.9") >= 0) {

            PlaceholderExpansion expansion = placeholderAPI.getLocalExpansionManager().getExpansion(placeholderIdentifier);

            if (expansion != null) {
                expansion.unregister();
                log.info(String.format("Unregistered old expansion (%s)...", expansion.getVersion()));
            }
        }
    }

    private void registerPlaceholders() {
        if (DependencyUtil.isEnabled("PlaceholderAPI")) {
            unregisterPlaceholders();
            new ConditionalTextExpansion(this).register();
            log.info("Registered placeholder expansion.");
        }
    }

    @Override
    public void onPluginDisable() {
        unregisterPlaceholders();
    }

    @Override
    public void onReload() {
        loadOptions();
        settingManager.load();
        registerPlaceholders();
    }

    @Override
    public UsageFlag[] usageFlags() {
        return new UsageFlag[]{UsageFlag.LANGUAGE, UsageFlag.COMMANDS, UsageFlag.CONFIGURATION};
    }
}