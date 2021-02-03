package space.devport.wertik.conditionaltext;

import space.devport.utils.DevportPlugin;
import space.devport.utils.text.language.LanguageDefaults;

public class ConditionalTextLanguage extends LanguageDefaults {

    public ConditionalTextLanguage(DevportPlugin plugin) {
        super(plugin);
    }

    @Override
    public void setDefaults() {
        addDefault("Commands.Invalid-Setting", "&cSetting &f%param% &cis invalid.");
        addDefault("Commands.Invalid-Target", "&cPlayer &f%param% &cis invalid.");

        addDefault("Commands.Try.Output", "&7Parsed, result: '&r%result%&7'");
    }
}
