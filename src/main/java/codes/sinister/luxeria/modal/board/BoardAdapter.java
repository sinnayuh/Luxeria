package codes.sinister.luxeria.modal.board;

import codes.sinister.luxeria.Luxeria;
import codes.sinister.luxeria.modal.util.TranslateUtil;
import fr.mrmicky.fastboard.adventure.FastBoard;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BoardAdapter implements Listener {
    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private final Luxeria luxeria;

    public BoardAdapter(Luxeria luxeria) {
        this.luxeria = luxeria;

        luxeria.plugin.getServer().getScheduler().runTaskTimer(luxeria.plugin, () -> {
            for (FastBoard board : this.boards.values()) {
                updateBoard(board);
            }
        }, 0, 20);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        FastBoard board = new FastBoard(player);

        board.updateTitle(TranslateUtil.translate("&lilac&lSMP"));
        this.boards.put(player.getUniqueId(), board);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        FastBoard board = this.boards.remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }
    }

    private int getTotalMobKills(Player player) {
        int totalKills = 0;
        for (EntityType entityType : EntityType.values()) {
            try {
                totalKills += player.getStatistic(Statistic.KILL_ENTITY, entityType);
            } catch (IllegalArgumentException ignored) {

            }
        }
        return totalKills;
    }

    private void updateBoard(FastBoard board) {
        Player player = board.getPlayer();

        board.updateLines(
                TranslateUtil.translate(""),
                TranslateUtil.translate("&lilac* &7Online: &lilac" + luxeria.plugin.getServer().getOnlinePlayers().size()),
                TranslateUtil.translate("&lilac* &7Deaths: &lilac" + player.getStatistic(Statistic.DEATHS)),
                TranslateUtil.translate("&lilac* &7Kills: &lilac" + getTotalMobKills(player)),
                TranslateUtil.translate("&lilac* &7Raids Won: &lilac" + player.getStatistic(Statistic.RAID_WIN)),
                TranslateUtil.translate("                    ")
        );
    }
}