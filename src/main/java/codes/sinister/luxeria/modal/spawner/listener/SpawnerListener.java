package codes.sinister.luxeria.modal.spawner.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SpawnerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlaced(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        if (block.getType() == Material.SPAWNER) {
            CreatureSpawner spawner = (CreatureSpawner) block.getState();
            spawner.setRequiredPlayerRange(16);
            spawner.update();
            System.out.println("Increased Spawners range placed at " + block.getLocation());
        }
    }
}
