package space.devport.wertik.conditionaltext;

import lombok.Getter;
import space.devport.utils.DevportPlugin;
import space.devport.utils.UsageFlag;
import space.devport.wertik.conditionaltext.commands.ConditionalTextCommand;
import space.devport.wertik.conditionaltext.commands.subcommands.ReloadSubCommand;
import space.devport.wertik.conditionaltext.commands.subcommands.TrySubCommand;
import space.devport.wertik.conditionaltext.system.SettingManager;

import java.time.format.DateTimeFormatter;

public class ConditionalTextPlugin extends DevportPlugin {

    @Getter
    private static ConditionalTextPlugin instance;

    @Getter
    private SettingManager settingManager;

    @Getter
    private DateTimeFormatter timeFormatter;

    @Override
    public void onPluginEnable() {
        instance = this;

        loadOptions();

        settingManager = new SettingManager(this);
        settingManager.loadSettings();

        setupPlaceholders();

        addMainCommand(new ConditionalTextCommand())
                .addSubCommand(new ReloadSubCommand())
                .addSubCommand(new TrySubCommand());

        new ConditionalTextLanguage();
    }

    private void loadOptions() {
        this.timeFormatter = DateTimeFormatter.ofPattern(this.configuration.getString("formats.time", "H:m:s"));
    }

    private void setupPlaceholders() {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            //TODO unregister on 2.10.9+
            new ConditionalTextExpansion(this).register();
        }
    }

    @Override
    public void onPluginDisable() {
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
}