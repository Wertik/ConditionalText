package space.devport.wertik.conditionaltext.commands;

import space.devport.dock.commands.MainCommand;
import space.devport.dock.commands.struct.CommandResult;
import space.devport.wertik.conditionaltext.ConditionalTextPlugin;
import space.devport.wertik.conditionaltext.commands.subcommands.ListSubCommand;
import space.devport.wertik.conditionaltext.commands.subcommands.TrySubCommand;

public class ConditionalTextCommand extends MainCommand {

    public ConditionalTextCommand(ConditionalTextPlugin plugin) {
        super(plugin, "conditionaltext");

        withSubCommand(plugin.buildSubCommand("reload")
                .withDefaultUsage("/%label% reload")
                .withDefaultDescription("Reload the plugin.")
                .withRange(0)
                .withExecutor((sender, label, args) -> {
                    plugin.reload(sender);
                    return CommandResult.SUCCESS;
                }));
        withSubCommand(new TrySubCommand(plugin));
        withSubCommand(new ListSubCommand(plugin));
    }

    @Override
    public String getDefaultUsage() {
        return "/%label%";
    }

    @Override
    public String getDefaultDescription() {
        return "Displays this.";
    }
}