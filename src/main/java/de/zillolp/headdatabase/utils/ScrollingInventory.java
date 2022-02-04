package de.zillolp.headdatabase.utils;

import java.util.Arrays;
import java.util.LinkedList;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ScrollingInventory {
    private Inventory inventory;
    private LinkedList<ItemStack> items;
    private Integer[] skipped_slots;
    private int start_slot;
    private int stop_slot;
    private int page;

    public ScrollingInventory(Inventory inventory, LinkedList<ItemStack> items, Integer[] skipped_slots, int start_slot, int stop_slot) {
        this.inventory = inventory;
        this.items = items;
        this.skipped_slots = skipped_slots;
        this.start_slot = start_slot;
        this.stop_slot = stop_slot;
        this.page = 0;
    }

    public boolean hasNext() {
        if (items == null || items.size() <= 0) {
            return false;
        }
        int slots = stop_slot - start_slot - skipped_slots.length;
        return items.size() > slots * (page + 1);
    }

    public boolean hasLast() {
        return page > 0;
    }

    public void nextPage() {
        if (hasNext()) {
            page++;
            loadPage();
        }
    }

    public void lastPage() {
        if (hasLast()) {
            page--;
            loadPage();
        }
    }

    public int getPage() {
        return page + 1;
    }

    public void loadPage() {
        if (items == null || items.size() <= 0) {
            int slot = stop_slot / 2;
            if (slot % 2 != 0) {
                slot = slot - 4;
            }
            inventory.setItem(slot, new ItemBuilder(Material.BARRIER, "§cEs sind keine Köpfe vorhanden.").build());
            return;
        }
        int slots = stop_slot - start_slot - skipped_slots.length;
        int count = slots * page;
        int field = 0;
        for (int slot = start_slot; slot < stop_slot; slot++) {
            if (!(Arrays.asList(skipped_slots).contains(slot))) {
                int item = field + count;
                if (item < items.size()) {
                    inventory.setItem(slot, items.get(item));
                } else {
                    inventory.setItem(slot, new ItemStack(Material.AIR));
                }
                field++;
            }
        }
    }
}
