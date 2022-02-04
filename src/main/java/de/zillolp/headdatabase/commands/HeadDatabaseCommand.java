package de.zillolp.headdatabase.commands;

import de.zillolp.headdatabase.config.LanguageTools;
import de.zillolp.headdatabase.mysql.HeadsManager;
import de.zillolp.headdatabase.utils.InventorySetter;
import de.zillolp.headdatabase.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HeadDatabaseCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(LanguageTools.getONLY_PLAYER());
            return false;
        }
        Player player = (Player) sender;
        if (!(player.hasPermission("hdb.use"))) {
            player.sendMessage(LanguageTools.getNO_PERMISSION());
            return false;
        }
        int length = args.length;
        if (length == 0) {
            Inventory inventory = Bukkit.createInventory(null, 5 * 9, "§8Head Database");
            InventorySetter.setStartPageInventory(inventory);
            player.openInventory(inventory);
        } else if (length >= 3) {
            String category = args[1];
            if (!(HeadsManager.isCategory(category))) {
                player.sendMessage(LanguageTools.getPREFIX() + "§cDie angegebene Kategorie existiert nicht.");
                return false;
            }
            String headName = args[2];
            if (args[0].equalsIgnoreCase("add")) {
                if (args.length < 4) {
                    return false;
                }
                if (length > 4) {
                    for (int number = 3; number < length - 1; number++) {
                        headName = headName + " " + args[number];
                    }
                }
                if (HeadsManager.isHead(category, headName)) {
                    player.sendMessage(LanguageTools.getPREFIX() + "§cEin Kopf mit dem Namen §4" + headName + " §cexistiert bereits.");
                    return false;
                }
                String textureURL = args[length - 1];
                if (!(textureURL.contains("http://textures.minecraft.net/texture/"))) {
                    player.sendMessage(LanguageTools.getPREFIX() + "§cBitte gebe einen richtigen Link an.");
                    return false;
                }
                HeadsManager.addHead(category, headName, textureURL.replace("http://textures.minecraft.net/texture/", ""));
                player.getInventory().addItem(new ItemBuilder(Material.PLAYER_HEAD, "§a" + headName, textureURL).build());
                player.sendMessage(LanguageTools.getPREFIX() + "§7Der Kopf §a" + headName + " §7wurde hinzugefügt.");
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (length > 3) {
                    for (int number = 3; number < length; number++) {
                        headName = headName + " " + args[number];
                    }
                }
                if (!(HeadsManager.isHead(category, headName))) {
                    player.sendMessage(LanguageTools.getPREFIX() + "§cEin Kopf mit dem Namen §4" + headName + " §cexistiert nicht.");
                    return false;
                }
                HeadsManager.removeHead(category, headName);
                player.sendMessage(LanguageTools.getPREFIX() + "§7Der Kopf §a" + headName + " §7wurde entfernt.");
            } else if (args[0].equalsIgnoreCase("give")) {
                if (length > 3) {
                    for (int number = 3; number < length; number++) {
                        headName = headName + " " + args[number];
                    }
                }
                if (!(HeadsManager.isHead(category, headName))) {
                    player.sendMessage(LanguageTools.getPREFIX() + "§cEin Kopf mit dem Namen §4" + headName + " §cexistiert nicht.");
                    return false;
                }
                player.getInventory().addItem(HeadsManager.getHead(category, headName));
                player.sendMessage(LanguageTools.getPREFIX() + "§7Du hast den Kopf §a" + headName + " §7bekommen.");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            LinkedList<String> options = new LinkedList<>();
            options.add("give");
            options.add("add");
            options.add("remove");
            return options;
        } else if (args.length == 2) {
            return HeadsManager.categories;
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("add") || HeadsManager.heads.get(args[1]) == null) {
                return null;
            }
            LinkedList<String> categoryHeads = new LinkedList<>();
            for (Map.Entry<String, ItemStack> head : HeadsManager.heads.get(args[1]).entrySet()) {
                categoryHeads.add(head.getKey());
            }
            return categoryHeads;
        }
        return null;
    }
}
