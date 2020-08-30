package space.devport.wertik.conditionaltext;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import space.devport.utils.DevportPlugin;
import space.devport.utils.UsageFlag;
import space.devport.utils.utility.VersionUtil;
import space.devport.wertik.conditionaltext.commands.ConditionalTextCommand;
import space.devport.wertik.conditionaltext.commands.subcommands.ReloadSubCommand;
import space.devport.wertik.conditionaltext.commands.subcommands.TrySubCommand;
import space.devport.wertik.conditionaltext.system.SettingManager;

import java.time.format.DateTimeFormatter;

public class ConditionalTextPlugin extends DevportPlugin {

    @Getter
    private SettingManager settingManager;

    @Getter
    private DateTimeFormatter timeFormatter;

    @Override
    public void onPluginEnable() {
        loadOptions();

        settingManager = new SettingManager(this);
        settingManager.loadSettings();

        setupPlaceholders();

        addMainCommand(new ConditionalTextCommand())
                .addSubCommand(new ReloadSubCommand(this))
                .addSubCommand(new TrySubCommand(this));

        new ConditionalTextLanguage();
    }

    private void loadOptions() {
        this.timeFormatter = DateTimeFormatter.ofPattern(this.configuration.getString("formats.time", "H:m:s"));
    }

    private void unregisterPlaceholders() {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null &&
                PlaceholderAPI.isRegistered("conditionaltext") &&
                VersionUtil.compareVersions(PlaceholderAPIPlugin.getInstance().getDescription().getVersion(), "2.10.9") >= 0) {

            PlaceholderExpansion expansion = PlaceholderAPIPlugin.getInstance().getLocalExpansionManager().getExpansion("conditionaltext");

            if (expansion != null) {
                expansion.unregister();
                consoleOutput.info("Unregistered old expansion version...");
            }
        }
    }

    private void setupPlaceholders() {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            unregisterPlaceholders();
            new ConditionalTextExpansion(this).register();
            consoleOutput.info("Registered placeholder expansion.");
        }
    }

    @Override
    public void onPluginDisable() {
        unregisterPlaceholders();
    }

    @Override
    public void onReload() {
        loadOptions();
        settingManager.loadSettings();
        setupPlaceholders();
    }

    @Override
    public UsageFlag[] usageFlags() {
        return new UsageFlag[]{UsageFlag.LANGUAGE, UsageFlag.COMMANDS, UsageFlag.CONFIGURATION};
    }

    public static ConditionalTextPlugin getInstance() {
        return getPlugin(ConditionalTextPlugin.class);
    }
}