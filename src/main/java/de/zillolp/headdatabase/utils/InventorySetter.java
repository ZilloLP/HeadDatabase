package de.zillolp.headdatabase.utils;

import de.zillolp.headdatabase.HeadDatabase;
import de.zillolp.headdatabase.mysql.HeadsManager;
import de.zillolp.headdatabase.profiles.PlayerProfil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class InventorySetter {
    private static HashMap<UUID, PlayerProfil> playerProfiles = HeadDatabase.playerProfiles;

    public static void setStartPageInventory(Inventory inventory) {
        Integer[] designSlots = new Integer[]{0, 8, 9, 17, 18, 26, 27, 35, 36, 44};
        for (int slot : designSlots) {
            inventory.setItem(slot, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, "§8*").build());
        }
        ArrayList<String[]> lores = new ArrayList<>();
        for (String category : HeadsManager.categories) {
            int amount = 0;
            if (HeadsManager.heads.get(category) != null) {
                amount = HeadsManager.heads.get(category).size();
            }
            lores.add(new String[]{"§a" + amount + " §7Köpfe", " ", "§aLinksklick §8- §7Zum Öffnen"});
        }
        inventory.setItem(19, new ItemBuilder(Material.PLAYER_HEAD, "§aTiere", lores.get(0), "621668ef7cb79dd9c22ce3d1f3f4cb6e2559893b6df4a469514e667c16aa4").build());
        inventory.setItem(20, new ItemBuilder(Material.PLAYER_HEAD, "§aMonster", lores.get(1), "c84df79c49104b198cdad6d99fd0d0bcf1531c92d4ab6269e40b7d3cbbb8e98c").build());
        inventory.setItem(21, new ItemBuilder(Material.PLAYER_HEAD, "§aDekoration", lores.get(2), "29d772e3beaa5b91f329b2dcc2d246b2c8af5c3c675261807146d595767df").build());
        inventory.setItem(22, new ItemBuilder(Material.PLAYER_HEAD, "§aNahrung", lores.get(3), "a411cb54aaa1e3db555c785f6b75be1bf8e68b28e5cfc59c9a876894f61cdc17").build());
        inventory.setItem(23, new ItemBuilder(Material.PLAYER_HEAD, "§aFiguren", lores.get(4), "4dee244be08929dec08ff483bd5a856e0b6a740988174ec3047c74616f06f800").build());
        inventory.setItem(24, new ItemBuilder(Material.PLAYER_HEAD, "§aAlphabet", lores.get(5), "8ff88b122ff92513c6a27b7f67cb3fea97439e078821d6861b74332a2396").build());
        inventory.setItem(25, new ItemBuilder(Material.PLAYER_HEAD, "§aSonstiges", lores.get(6), "844498a0fe278956e3d04135ef4b1343d0548a7e208c61b1fb6f3b4dbc240da8").build());
    }

    public static void setCategoryPageInventory(Player player) {
        PlayerProfil playerProfil = playerProfiles.get(player.getUniqueId());
        Inventory inventory = playerProfil.getInventory();
        ScrollingInventory scrollingInventory = playerProfil.getScrollingInventory();
        scrollingInventory.loadPage();
        Integer[] designSlots = new Integer[]{0, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 53};
        for (int slot : designSlots) {
            inventory.setItem(slot, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, "§8*").build());
        }
        inventory.setItem(48, new ItemBuilder(Material.PLAYER_HEAD, "§7Letzte Seite", "f84f597131bbe25dc058af888cb29831f79599bc67c95c802925ce4afba332fc").build());
        inventory.setItem(50, new ItemBuilder(Material.PLAYER_HEAD, "§7Nächste Seite", "fcfe8845a8d5e635fb87728ccc93895d42b4fc2e6a53f1ba78c845225822").build());
    }
}
