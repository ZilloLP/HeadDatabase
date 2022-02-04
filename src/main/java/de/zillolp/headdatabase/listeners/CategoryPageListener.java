package de.zillolp.headdatabase.listeners;

import de.zillolp.headdatabase.HeadDatabase;
import de.zillolp.headdatabase.config.LanguageTools;
import de.zillolp.headdatabase.mysql.HeadsManager;
import de.zillolp.headdatabase.profiles.PlayerProfil;
import de.zillolp.headdatabase.utils.InventorySetter;
import de.zillolp.headdatabase.utils.ScrollingInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class CategoryPageListener implements Listener {
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
        if (HeadsManager.categories.contains(title.split(":")[0].substring(2))) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null || (!(item.hasItemMeta()))) {
                return;
            }
            ItemMeta itemMeta = item.getItemMeta();
            if (!(itemMeta.hasDisplayName())) {
                return;
            }
            if (item.getType() != Material.PLAYER_HEAD) {
                return;
            }
            String displayName = itemMeta.getDisplayName();
            if (displayName.equalsIgnoreCase("§7Letzte Seite")) {
                PlayerProfil playerProfil = playerProfiles.get(player.getUniqueId());
                ScrollingInventory scrollingInventory = playerProfil.getScrollingInventory();
                if (!(scrollingInventory.hasLast())) {
                    return;
                }
                scrollingInventory.lastPage();
                InventorySetter.setCategoryPageInventory(player);
            } else if (displayName.equalsIgnoreCase("§7Nächste Seite")) {
                PlayerProfil playerProfil = playerProfiles.get(player.getUniqueId());
                ScrollingInventory scrollingInventory = playerProfil.getScrollingInventory();
                if (!(scrollingInventory.hasNext())) {
                    return;
                }
                scrollingInventory.nextPage();
                InventorySetter.setCategoryPageInventory(player);
            } else {
                Integer[] designSlots = new Integer[]{0, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 53};
                int slot = event.getSlot();
                if (Arrays.asList(designSlots).contains(slot)) {
                    return;
                }
                if (slot >= 11 && slot <= 34) {
                    player.getInventory().addItem(item);
                }
            }
        }
    }
}
