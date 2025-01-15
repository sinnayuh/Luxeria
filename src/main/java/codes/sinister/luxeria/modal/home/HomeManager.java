package codes.sinister.luxeria.modal.home;

import codes.sinister.luxeria.modal.util.TranslateUtil;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class HomeManager {
    private static HomeManager instance;
    private final File homeFile;
    private final FileConfiguration homeConfig;
    private final Plugin plugin;
    private final Map<UUID, TeleportInfo> teleportTasks = new HashMap<>();
    private static final int MAX_HOMES = 3;

    public static int getMaxHomes() {
        return MAX_HOMES;
    }

    private HomeManager(Plugin plugin) {
        this.plugin = plugin;
        this.homeFile = new File(plugin.getDataFolder(), "homes.yml");
        if (!homeFile.exists()) {
            plugin.saveResource("homes.yml", false);
        }
        this.homeConfig = YamlConfiguration.loadConfiguration(homeFile);
    }

    public static HomeManager getInstance(Plugin plugin) {
        if (instance == null) {
            instance = new HomeManager(plugin);
        }
        return instance;
    }

    public boolean hasHome(Player player, String homeName) {
        return homeConfig.contains("homes." + player.getUniqueId() + "." + homeName);
    }

    public Set<String> getHomes(Player player) {
        String path = "homes." + player.getUniqueId();
        if (!homeConfig.contains(path)) {
            return new HashSet<>();
        }
        return homeConfig.getConfigurationSection(path).getKeys(false);
    }

    public void setHome(Player player, String homeName) {
        Set<String> homes = getHomes(player);
        if (homes.size() >= MAX_HOMES && !homes.contains(homeName)) {
            player.sendMessage(TranslateUtil.translate("&cYou can only have " + MAX_HOMES + " homes!"));
            return;
        }

        Location location = player.getLocation();
        String path = "homes." + player.getUniqueId() + "." + homeName;

        homeConfig.set(path + ".world", location.getWorld().getName());
        homeConfig.set(path + ".x", location.getX());
        homeConfig.set(path + ".y", location.getY());
        homeConfig.set(path + ".z", location.getZ());
        homeConfig.set(path + ".yaw", location.getYaw());
        homeConfig.set(path + ".pitch", location.getPitch());

        try {
            homeConfig.save(homeFile);
            player.sendMessage(TranslateUtil.translate("&lilacHome '" + homeName + "' set!"));
        } catch (IOException e) {
            player.sendMessage(TranslateUtil.translate("&cFailed to save home location!"));
            e.printStackTrace();
        }
    }

    public void deleteHome(Player player, String homeName) {
        String path = "homes." + player.getUniqueId() + "." + homeName;
        if (!hasHome(player, homeName)) {
            player.sendMessage(TranslateUtil.translate("&cHome '" + homeName + "' not found!"));
            return;
        }

        homeConfig.set(path, null);
        try {
            homeConfig.save(homeFile);
            player.sendMessage(TranslateUtil.translate("&cHome '" + homeName + "' deleted!"));
        } catch (IOException e) {
            player.sendMessage(TranslateUtil.translate("&cFailed to delete home!"));
            e.printStackTrace();
        }
    }

    public void teleportToHome(Player player, String homeName) {
        if (!hasHome(player, homeName)) {
            player.sendMessage(TranslateUtil.translate("&cHome '" + homeName + "' not found!"));
            return;
        }

        cancelTeleport(player);

        Location startLocation = player.getLocation();
        String path = "homes." + player.getUniqueId() + "." + homeName;
        Location homeLocation = new Location(
                plugin.getServer().getWorld(homeConfig.getString(path + ".world")),
                homeConfig.getDouble(path + ".x"),
                homeConfig.getDouble(path + ".y"),
                homeConfig.getDouble(path + ".z"),
                (float) homeConfig.getDouble(path + ".yaw"),
                (float) homeConfig.getDouble(path + ".pitch")
        );

        player.sendMessage(TranslateUtil.translate("&7Teleporting in 3 seconds. Don't move!"));
        TeleportInfo teleportInfo = new TeleportInfo(startLocation, homeLocation, homeName);
        teleportTasks.put(player.getUniqueId(), teleportInfo);
    }

    public void cancelTeleport(Player player) {
        teleportTasks.remove(player.getUniqueId());
    }

    public boolean isPlayerTeleporting(Player player) {
        return teleportTasks.containsKey(player.getUniqueId());
    }

    public TeleportInfo getTeleportInfo(Player player) {
        return teleportTasks.get(player.getUniqueId());
    }

    public static class TeleportInfo {
        private final Location startLocation;
        private final Location destination;
        private final String homeName;
        private final long startTime;

        public TeleportInfo(Location startLocation, Location destination, String homeName) {
            this.startLocation = startLocation;
            this.destination = destination;
            this.homeName = homeName;
            this.startTime = System.currentTimeMillis();
        }

        public Location getStartLocation() { return startLocation; }
        public Location getDestination() { return destination; }
        public String getHomeName() { return homeName; }
        public long getStartTime() { return startTime; }
    }
}