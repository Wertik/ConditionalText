package space.devport.wertik.conditionaltext.commands;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import space.devport.dock.commands.SubCommand;
import space.devport.dock.commands.struct.ArgumentRange;
import space.devport.wertik.conditionaltext.ConditionalTextPlugin;

public abstract class ConditionalTextSubCommand extends SubCommand {

    @Getter
    private final ConditionalTextPlugin plugin;

    public ConditionalTextSubCommand(ConditionalTextPlugin plugin, String name) {
        super(plugin, name);
        setPermissions();
        this.plugin = plugin;
    }

    @Override
    public abstract @Nullable String getDefaultUsage();

    @Override
    public abstract @Nullable String getDefaultDescription();

    @Override
    public abstract @Nullable ArgumentRange getRange();
}