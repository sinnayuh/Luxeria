package codes.sinister.luxeria;

import codes.sinister.luxeria.modal.board.BoardAdapter;
import codes.sinister.luxeria.modal.debug.listener.PhantomListener;
import codes.sinister.luxeria.modal.home.command.DeleteHomeCommand;
import codes.sinister.luxeria.modal.home.command.HomeCommand;
import codes.sinister.luxeria.modal.home.command.ListHomeCommand;
import codes.sinister.luxeria.modal.home.command.SetHomeCommand;
import codes.sinister.luxeria.modal.home.listener.HomeListener;
import codes.sinister.luxeria.modal.debug.command.DebugCommand;
import codes.sinister.luxeria.modal.debug.listener.SpawnerListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.util.List;

public final class Luxeria {
    public final @NotNull JavaPlugin plugin;
    private final @NotNull BukkitCommandHandler commandHandler;

    private static Luxeria luxeria;

    public static Luxeria getInstance() {
        return luxeria;
    }

    public Luxeria(@NotNull JavaPlugin plugin) {
        luxeria = this;

        this.plugin = plugin;
        this.commandHandler = BukkitCommandHandler.create(plugin);

        registerCommands();
        registerListeners();
    }

    private void registerCommands() {
        commandHandler.register(
                new DebugCommand(),
                new HomeCommand(this),
                new SetHomeCommand(this),
                new ListHomeCommand(this),
                new DeleteHomeCommand(this)
        );
    }

    private void registerListeners() {
        List.of(
            new SpawnerListener(),
            new PhantomListener(),
            new HomeListener(this),
            new BoardAdapter(this)
        ).forEach(listener -> plugin.getServer().getPluginManager().registerEvents(listener, plugin));
    }

    public void disable() {
        luxeria = null;
    }
}