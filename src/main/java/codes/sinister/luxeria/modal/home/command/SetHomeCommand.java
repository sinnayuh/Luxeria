package codes.sinister.luxeria.modal.home.command;

import codes.sinister.luxeria.Luxeria;
import codes.sinister.luxeria.modal.home.HomeManager;
import codes.sinister.luxeria.modal.util.TranslateUtil;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.DefaultFor;

@Command({"sethome"})
public class SetHomeCommand {
    private final HomeManager homeManager;

    public SetHomeCommand(Luxeria luxeria) {
        this.homeManager = HomeManager.getInstance(luxeria.plugin);
    }

    @DefaultFor({"sethome"})
    public void onSetHomeCommand(Player player, String homeName) {
        if (homeName == null) {
            player.sendMessage(TranslateUtil.translate("&cYou must specify a home name!"));
            return;
        }

        homeManager.setHome(player, homeName);
    }
}