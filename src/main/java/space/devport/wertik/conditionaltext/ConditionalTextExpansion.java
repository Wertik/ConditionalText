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
        return "conditionaltext";
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
    public String onPlaceholderRequest(Player player, @NotNull String params) {

        if (Strings.isNullOrEmpty(params)) return "invalid_params";

        Setting setting = plugin.getSettingManager().getSetting(params.split("_")[0]);

        if (setting == null) return "invalid_setting";

        String[] args = Arrays.copyOfRange(params.split("_"), 1, params.split("_").length - 1);

        plugin.getConsoleOutput().debug("Custom arguments: " + Arrays.toString(args));

        String output = setting.process(player);

        return output == null ? "invalid_input_placeholder" : output;
    }
}