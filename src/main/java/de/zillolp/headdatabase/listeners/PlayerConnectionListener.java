package de.zillolp.headdatabase.listeners;

import de.zillolp.headdatabase.HeadDatabase;
import de.zillolp.headdatabase.profiles.PlayerProfil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerConnectionListener implements Listener {
    private HashMap<UUID, PlayerProfil> playerProfiles = HeadDatabase.playerProfiles;

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (playerProfiles.replace(player.getUniqueId(), new PlayerProfil()) == null) {
            playerProfiles.put(player.getUniqueId(), new PlayerProfil());
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playerProfiles.remove(player.getUniqueId());
    }
}
