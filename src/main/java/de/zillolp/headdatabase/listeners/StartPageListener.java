package de.zillolp.headdatabase.listeners;

import de.zillolp.headdatabase.HeadDatabase;
import de.zillolp.headdatabase.mysql.HeadsManager;
import de.zillolp.headdatabase.profiles.PlayerProfil;
import de.zillolp.headdatabase.utils.InventorySetter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;

public class StartPageListener implements Listener {
    private HashMap<UUID, PlayerProfil> playerProfiles = HeadDatabase.playerProfiles;

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null) {
            return;
        }
        InventoryView inventoryView = event.getView();
        String title = event.getView().getTitle();
        if (inventoryView == null || title == null) {
            return;
        }
        if (title.equalsIgnoreCase("§8Head Database")) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null || (!(item.hasItemMeta()))) {
                return;
            }
            ItemMeta itemMeta = item.getItemMeta();
            if (!(itemMeta.hasDisplayName())) {
                return;
            }
            String category = itemMeta.getDisplayName().substring(2);
            if (HeadsManager.categories.contains(category)) {
                PlayerProfil playerProfil = playerProfiles.get(player.getUniqueId());
                playerProfil.createTestInventory(category);
                InventorySetter.setCategoryPageInventory(player);
                player.openInventory(playerProfil.getInventory());
            }
        }
    }
}
