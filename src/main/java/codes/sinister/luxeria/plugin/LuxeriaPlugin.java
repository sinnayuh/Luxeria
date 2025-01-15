package codes.sinister.luxeria.plugin;

import codes.sinister.luxeria.Luxeria;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class LuxeriaPlugin extends JavaPlugin implements Listener {
    private @NotNull Luxeria luxeria;

    @Override
    public void onEnable() {
        luxeria = new Luxeria(this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        Optional.of(luxeria).ifPresent(Luxeria::disable);
    }
}
