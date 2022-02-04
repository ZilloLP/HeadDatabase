package de.zillolp.headdatabase.profiles;

import de.zillolp.headdatabase.mysql.HeadsManager;
import de.zillolp.headdatabase.utils.ScrollingInventory;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class PlayerProfil {
    private Inventory inventory;
    private ScrollingInventory scrollingInventory;

    public void createTestInventory(String category) {
        HashMap<String, LinkedHashMap<String, ItemStack>> heads = HeadsManager.heads;
        inventory = Bukkit.createInventory(null, 6 * 9, "§8" + category + ": §a" + heads.get(category).size());
        LinkedList<ItemStack> categoryHeads = new LinkedList<>();
        for (Map.Entry<String, ItemStack> head : heads.get(category).entrySet()) {
            categoryHeads.add(head.getValue());
        }
        scrollingInventory = new ScrollingInventory(inventory, categoryHeads, new Integer[]{16, 17, 18, 19, 25, 26, 27, 28}, 11, 34);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public ScrollingInventory getScrollingInventory() {
        return scrollingInventory;
    }
}
