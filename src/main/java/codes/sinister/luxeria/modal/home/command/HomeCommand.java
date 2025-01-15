package codes.sinister.luxeria.modal.home.command;

import codes.sinister.luxeria.Luxeria;
import codes.sinister.luxeria.modal.home.HomeManager;
import codes.sinister.luxeria.modal.util.TranslateUtil;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.DefaultFor;
import revxrsal.commands.annotation.Optional;

@Command({"home"})
public class HomeCommand {
    private final HomeManager homeManager;

    public HomeCommand(Luxeria luxeria) {
        this.homeManager = HomeManager.getInstance(luxeria.plugin);
    }

    @DefaultFor({"home"})
    public void onHomeCommand(Player player, @Optional String homeName) {
        if (homeName == null) {
            var homes = homeManager.getHomes(player);
            if (homes.isEmpty()) {
                player.sendMessage(TranslateUtil.translate("&cYou haven't set any homes yet!"));
                return;
            }
            homeName = homes.iterator().next();
        }
        homeManager.teleportToHome(player, homeName);
    }
}