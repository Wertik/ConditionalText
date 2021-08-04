package space.devport.wertik.conditionaltext.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.devport.dock.commands.struct.ArgumentRange;
import space.devport.dock.commands.struct.CommandResult;
import space.devport.dock.text.message.Message;
import space.devport.wertik.conditionaltext.ConditionalTextPlugin;
import space.devport.wertik.conditionaltext.commands.ConditionalTextSubCommand;
import space.devport.wertik.conditionaltext.system.struct.Setting;

import java.util.Map;

public class ListSubCommand extends ConditionalTextSubCommand {

    public ListSubCommand(ConditionalTextPlugin plugin) {
        super(plugin, "list");
    }

    @Override
    protected @NotNull CommandResult perform(@NotNull CommandSender sender, @NotNull String label, String[] args) {

        Map<String, Setting> settings = getPlugin().getSettingManager().getSettings();

        if (settings.isEmpty()) {
            language.sendPrefixed(sender, "Commands.List.Empty");
            return CommandResult.SUCCESS;
        }

        Message header = language.get("Commands.List.Header");
        String format = language.get("Commands.List.Format").toString();

        for (Map.Entry<String, Setting> entry : settings.entrySet()) {
            header.append(format.replace("%name%", entry.getKey()).replace("%rules%", String.valueOf(entry.getValue().getRules().size())));
        }

        header.send(sender);
        return CommandResult.SUCCESS;
    }

    @Override
    public @Nullable String getDefaultUsage() {
        return "/%label% list";
    }

    @Override
    public @Nullable String getDefaultDescription() {
        return "List loaded settings.";
    }

    @Override
    public @Nullable ArgumentRange getRange() {
        return new ArgumentRange(0);
    }
}
