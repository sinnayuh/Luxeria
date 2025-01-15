package codes.sinister.luxeria.modal.home.listener;

import codes.sinister.luxeria.Luxeria;
import codes.sinister.luxeria.modal.home.HomeManager;
import codes.sinister.luxeria.modal.util.TranslateUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class HomeListener implements Listener {
    private final Luxeria luxeria;
    private final HomeManager homeManager;

    public HomeListener(Luxeria luxeria) {
        this.luxeria = luxeria;
        this.homeManager = HomeManager.getInstance(luxeria.plugin);
        luxeria.plugin.getServer().getPluginManager().registerEvents(this, luxeria.plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!homeManager.isPlayerTeleporting(player)) {
            return;
        }

        Location from = event.getFrom();
        Location to = event.getTo();

        double deltaX = Math.abs(from.getX() - to.getX());
        double deltaY = Math.abs(from.getY() - to.getY());
        double deltaZ = Math.abs(from.getZ() - to.getZ());

        if (deltaX < 0.001 && deltaY < 0.001 && deltaZ < 0.001) {
            return;
        }

        homeManager.cancelTeleport(player);
        player.sendMessage(TranslateUtil.translate("&cTeleport cancelled due to movement!"));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        luxeria.plugin.getServer().getScheduler().runTaskTimer(luxeria.plugin, () -> {
            for (Player player : luxeria.plugin.getServer().getOnlinePlayers()) {
                if (!homeManager.isPlayerTeleporting(player)) {
                    continue;
                }

                HomeManager.TeleportInfo info = homeManager.getTeleportInfo(player);
                if (System.currentTimeMillis() - info.getStartTime() >= 3000) {
                    player.teleport(info.getDestination());
                    player.sendMessage(TranslateUtil.translate("&lilacTeleported to '" + info.getHomeName() + "'!"));
                    homeManager.cancelTeleport(player);
                }
            }
        }, 1L, 1L);
    }
}