package space.devport.wertik.conditionaltext;

import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.devport.wertik.conditionaltext.system.struct.Setting;

import java.util.Arrays;

@RequiredArgsConstructor
public class ConditionalTextExpansion extends PlaceholderExpansion {

    private final ConditionalTextPlugin plugin;

    @Override
    public @NotNull String getIdentifier() {
        return plugin.getPlaceholderIdentifier();
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {

        if (Strings.isNullOrEmpty(params))
            return "invalid-params";

        String[] args = params.split("_");

        Setting setting = plugin.getSettingManager().getSetting(params.split("_")[0]);

        if (setting == null)
            return "invalid-setting";

        String[] placeholderArguments = Arrays.copyOfRange(args, 1, args.length);

        String output = setting.process(player, placeholderArguments);

        return output == null ? plugin.getConfig().getString("empty-output", "no-match") : output;
    }
}