package de.zillolp.headdatabase;

import de.zillolp.headdatabase.commands.HeadDatabaseCommand;
import de.zillolp.headdatabase.config.LanguageTools;
import de.zillolp.headdatabase.listeners.CategoryPageListener;
import de.zillolp.headdatabase.listeners.PlayerConnectionListener;
import de.zillolp.headdatabase.listeners.StartPageListener;
import de.zillolp.headdatabase.mysql.HeadsManager;
import de.zillolp.headdatabase.mysql.MySQL;
import de.zillolp.headdatabase.profiles.PlayerProfil;
import de.zillolp.headdatabase.utils.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class HeadDatabase extends JavaPlugin {
    private static HeadDatabase headDatabase;
    public static HashMap<UUID, PlayerProfil> playerProfiles;

    @Override
    public void onEnable() {
        headDatabase = this;
        if (register()) {
            init(Bukkit.getPluginManager());
            Bukkit.getConsoleSender().sendMessage("§7[§aHeadDatabase§7] §aDas Plugin wurde gestartet.");
        } else {
            Bukkit.getPluginManager().disablePlugin(headDatabase);
        }
    }

    @Override
    public void onDisable() {
        MySQL.close();
        Bukkit.getConsoleSender().sendMessage("§7[§aHeadDatabase§7] §cDas Plugin wurde gestoppt.");
    }

    private boolean register() {
        if (!(new ConfigUtil("language.yml").exist())) {
            saveResource("language.yml", false);
        }
        if (!(new ConfigUtil("mysql.yml").exist())) {
            saveResource("mysql.yml", false);
        }
        LanguageTools.load();
        MySQL.load();
        return MySQL.connected;
    }

    private void init(PluginManager pluginManager) {
        HeadsManager.loadHeads();
        playerProfiles = new HashMap<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerProfiles.put(player.getUniqueId(), new PlayerProfil());
        }
        getCommand("hdb").setExecutor(new HeadDatabaseCommand());
        pluginManager.registerEvents(new CategoryPageListener(), this);
        pluginManager.registerEvents(new PlayerConnectionListener(), this);
        pluginManager.registerEvents(new StartPageListener(), this);
    }

    public static HeadDatabase getHeadDatabase() {
        return headDatabase;
    }
}
