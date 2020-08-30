package space.devport.wertik.conditionaltext.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import space.devport.utils.commands.struct.ArgumentRange;
import space.devport.utils.commands.struct.CommandResult;
import space.devport.wertik.conditionaltext.ConditionalTextPlugin;
import space.devport.wertik.conditionaltext.commands.ConditionalTextSubCommand;

public class ReloadSubCommand extends ConditionalTextSubCommand {

    public ReloadSubCommand(ConditionalTextPlugin plugin) {
        super(plugin, "reload");
    }

    @Override
    protected CommandResult perform(CommandSender sender, String label, String[] args) {
        getPlugin().reload(sender);
        return CommandResult.SUCCESS;
    }

    @Override
    public @NotNull
    String getDefaultUsage() {
        return "/%label% reload";
    }

    @Override
    public @NotNull
    String getDefaultDescription() {
        return "Reload the plugin.";
    }

    @Override
    public @NotNull
    ArgumentRange getRange() {
        return new ArgumentRange(0);
    }
}