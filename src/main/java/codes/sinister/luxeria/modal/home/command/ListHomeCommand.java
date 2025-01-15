package codes.sinister.luxeria.modal.home.command;

import codes.sinister.luxeria.Luxeria;
import codes.sinister.luxeria.modal.home.HomeManager;
import codes.sinister.luxeria.modal.util.TranslateUtil;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.DefaultFor;

@Command({"listhome"})
public class ListHomeCommand {
    private final HomeManager homeManager;

    public ListHomeCommand(Luxeria luxeria) {
        this.homeManager = HomeManager.getInstance(luxeria.plugin);
    }

    @DefaultFor({"listhome"})
    public void onListHomeCommand(Player player) {
        var homes = homeManager.getHomes(player);
        if (homes.isEmpty()) {
            player.sendMessage(TranslateUtil.translate("&cYou haven't set any homes yet!"));
            return;
        }

        player.sendMessage(TranslateUtil.translate("&7Your homes (" + homes.size() + "/" + HomeManager.getMaxHomes() + "):"));
        for (String home : homes) {
            player.sendMessage(TranslateUtil.translate("&7- " + home));
        }
    }
}