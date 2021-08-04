package space.devport.wertik.conditionaltext;


import space.devport.dock.api.IDockedPlugin;
import space.devport.dock.text.language.LanguageDefaults;

public class ConditionalTextLanguage extends LanguageDefaults {

    public ConditionalTextLanguage(IDockedPlugin plugin) {
        super(plugin);
    }

    @Override
    public void setDefaults() {
        addDefault("Commands.Invalid-Setting", "&cSetting &f%param% &cis invalid.");
        addDefault("Commands.Invalid-Target", "&cPlayer &f%param% &cis invalid.");

        addDefault("Commands.Try.Output", "&7Parsed, result: '&r%result%&7'");

        addDefault("Commands.List.Empty", "&7No settings yet.");

        addDefault("Commands.List.Header", "&8&m        &f Loaded Settings &8&m        ");
        addDefault("Commands.List.Format", "&8 - &f%name% &7( &f%rules% rule(s) &7)");
    }
}
