package space.devport.wertik.conditionaltext.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.devport.utils.commands.SubCommand;
import space.devport.utils.commands.struct.ArgumentRange;
import space.devport.utils.commands.struct.CommandResult;
import space.devport.utils.commands.struct.Preconditions;
import space.devport.wertik.conditionaltext.ConditionalTextPlugin;
import space.devport.wertik.conditionaltext.system.struct.Setting;

public class TrySubCommand extends SubCommand {

    private final ConditionalTextPlugin plugin;

    public TrySubCommand() {
        super("try");
        this.plugin = ConditionalTextPlugin.getInstance();

        this.preconditions = new Preconditions()
                .permissions("conditionaltext.try");
    }

    @Override
    protected CommandResult perform(CommandSender sender, String label, String[] args) {

        Setting setting = plugin.getSettingManager().getSetting(args[0]);

        if (setting == null) {
            language.getPrefixed("Commands.Invalid-Setting")
                    .replace("%param%", args[0])
                    .send(sender);
            return CommandResult.FAILURE;
        }

        Player target;
        if (args[1].equalsIgnoreCase("me")) {
            if (!(sender instanceof Player)) return CommandResult.NO_CONSOLE;

            target = (Player) sender;
        } else if (args[1].equalsIgnoreCase("none")) {
            target = null;
        } else {
            target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                language.getPrefixed("Commands.Invalid-Target")
                        .replace("%param%", args[1])
                        .send(sender);
                return CommandResult.FAILURE;
            }
        }

        String output = args.length > 2 ? setting.process(args[2]) : setting.process((Player) target, new String[0]);

        language.getPrefixed("Commands.Try.Output")
                .replace("%result%", output == null ? "" : output)
                .send(sender);
        return CommandResult.SUCCESS;
    }

    @Override
    public @NotNull String getDefaultUsage() {
        return "/%label% try <setting> <player/me/none> (value)";
    }

    @Override
    public @NotNull String getDefaultDescription() {
        return "Try to parse a setting on a player, or with a custom value.";
    }

    @Override
    public @NotNull ArgumentRange getRange() {
        return new ArgumentRange(2, 3);
    }
}