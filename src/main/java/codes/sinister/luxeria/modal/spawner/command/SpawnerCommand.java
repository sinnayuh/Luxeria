package codes.sinister.luxeria.modal.spawner.command;

import codes.sinister.luxeria.modal.util.TranslateUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.DefaultFor;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command({"increasedspawners", "inc"})
@CommandPermission("luxeria.admin")
public record SpawnerCommand() {
    @DefaultFor({"increasedspawners", "inc"})
    public void onBaseCommand(Player sender) {
        sender.sendMessage(TranslateUtil.translate(
                "&lilac&lLuxeria Spawners Help",
                "&lilac/inc help &7- Shows this help menu",
                "&lilac/inc getradius &7- Gets the spawner range",
                "&lilac/inc setradius <int> &7- Sets the spawner range"
        ));
    }

    @Subcommand({"help"})
    public void onHelpCommand(Player sender) {
        sender.sendMessage(TranslateUtil.translate(
                "&lilac&lLuxeria Spawners Help",
                "&lilac/inc help &7- Shows this help menu",
                "&lilac/inc getradius &7- Gets the spawner range",
                "&lilac/inc setradius <int> &7- Sets the spawner range"
        ));
    }

    @Subcommand({"setradius"})
    public void onSetRadiusCommand(Player sender, int radius) {
        Block targetBlock = sender.getTargetBlock(null, 5);

        if (targetBlock.getType() != Material.SPAWNER) {
            sender.sendMessage(TranslateUtil.translate("&cYou must be looking at a spawner!"));
            return;
        }

        CreatureSpawner spawner = (CreatureSpawner) targetBlock.getState();
        spawner.setRequiredPlayerRange(radius);
        spawner.update();

        sender.sendMessage(TranslateUtil.translate("&lilacSpawner range set to " + radius + " blocks"));
    }

    @Subcommand({"getradius"})
    public void onGetRadiusCommand(Player sender) {
        Block targetBlock = sender.getTargetBlock(null, 5);

        if (targetBlock.getType() != Material.SPAWNER) {
            sender.sendMessage(TranslateUtil.translate("&cYou must be looking at a spawner!"));
            return;
        }

        CreatureSpawner spawner = (CreatureSpawner) targetBlock.getState();
        int radius = spawner.getRequiredPlayerRange();

        sender.sendMessage(TranslateUtil.translate("&lilacSpawner range is currently " + radius + " blocks"));
    }
}
