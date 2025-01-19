package codes.sinister.luxeria.modal.debug.listener;

import org.bukkit.entity.Phantom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class PhantomListener implements Listener {
    private static boolean phantomSpawningDisabled = true; // Default to disabled

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPhantomSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Phantom && phantomSpawningDisabled) {
            event.setCancelled(true);
        }
    }

    public static boolean isPhantomSpawningDisabled() {
        return phantomSpawningDisabled;
    }

    public static void togglePhantomSpawning() {
        phantomSpawningDisabled = !phantomSpawningDisabled;
    }
}