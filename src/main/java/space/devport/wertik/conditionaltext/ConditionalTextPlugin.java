package space.devport.wertik.conditionaltext;

import lombok.Getter;
import space.devport.utils.DevportPlugin;
import space.devport.wertik.conditionaltext.commands.ConditionalTextCommand;
import space.devport.wertik.conditionaltext.commands.subcommands.ReloadSubCommand;
import space.devport.wertik.conditionaltext.system.SettingManager;

public class ConditionalTextPlugin extends DevportPlugin {

    @Getter
    private static ConditionalTextPlugin instance;

    @Getter
    private SettingManager settingManager;

    @Override
    public void onPluginEnable() {
        instance = this;

        settingManager = new SettingManager(this);
        settingManager.loadSettings();

        setupPlaceholders();

        addMainCommand(new ConditionalTextCommand())
                .addSubCommand(new ReloadSubCommand());
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
        settingManager.loadSettings();
        setupPlaceholders();
    }

    @Override
    public boolean useLanguage() {
        return true;
    }

    @Override
    public boolean useHolograms() {
        return false;
    }

    @Override
    public boolean useMenus() {
        return false;
    }
}
