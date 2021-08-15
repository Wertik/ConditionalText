package space.devport.wertik.conditionaltext.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.devport.dock.commands.struct.ArgumentRange;
import space.devport.dock.commands.struct.CommandResult;
import space.devport.wertik.conditionaltext.ConditionalTextPlugin;
import space.devport.wertik.conditionaltext.commands.ConditionalTextSubCommand;
import space.devport.wertik.conditionaltext.system.struct.Setting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrySubCommand extends ConditionalTextSubCommand {

    public TrySubCommand(ConditionalTextPlugin plugin) {
        super(plugin, "try");
    }

    @SuppressWarnings("deprecation")
    @Override
    protected @NotNull CommandResult perform(@NotNull CommandSender sender, @NotNull String label, String[] args) {

        Setting setting = getPlugin().getSettingManager().getSetting(args[0]);

        if (setting == null) {
            language.getPrefixed("Commands.Invalid-Setting")
                    .replace("%param%", args[0])
                    .send(sender);
            return CommandResult.FAILURE;
        }

        OfflinePlayer target;
        if (args[1].equalsIgnoreCase("me")) {
            if (!(sender instanceof Player))
                return CommandResult.NO_CONSOLE;

            target = (Player) sender;
        } else if (args[1].equalsIgnoreCase("none"))
            target = null;
        else
            target = Bukkit.getOfflinePlayer(args[1]);

        String output = args.length > 2 ? setting.process(target, args[2], new String[0]) : setting.process(target);

        language.getPrefixed("Commands.Try.Output")
                .replace("%result%", output == null ? "" : output)
                .send(sender);
        return CommandResult.SUCCESS;
    }

    @Override
    public @Nullable List<String> requestTabComplete(@NotNull CommandSender sender, String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(getPlugin().getSettingManager().getSettings().keySet());
        } else if (args.length == 2) {
            List<String> out = Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
            out.add("none");
            out.add("me");
            return out;
        }
        return null;
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