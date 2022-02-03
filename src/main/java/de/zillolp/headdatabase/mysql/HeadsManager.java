package de.zillolp.headdatabase.mysql;

import de.zillolp.headdatabase.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HeadsManager {
    public static List<String> categories;
    public static HashMap<String, LinkedHashMap<String, ItemStack>> heads;

    public static void loadHeads() {
        heads = new HashMap<>();
        categories = Arrays.asList(new String[]{"Tiere", "Monster", "Dekoration", "Nahrung", "Figuren", "Alphabet", "Sonstiges"});
        for (String category : categories) {
            LinkedHashMap<String, ItemStack> categoryHeads = new LinkedHashMap<>();
            for (Map.Entry<String, String> head : getHeads(category).entrySet()) {
                categoryHeads.put(head.getKey(), new ItemBuilder(Material.PLAYER_HEAD, "§a" + head.getKey(), head.getValue()).build());
            }
            heads.put(category, categoryHeads);
        }
    }

    public static void addHead(String category, String headName, String textureURL) {
        LinkedHashMap<String, ItemStack> categoryHeads = heads.get(category);
        categoryHeads.put(headName, new ItemBuilder(Material.PLAYER_HEAD, "§a" + headName, textureURL).build());
        heads.replace(category, categoryHeads);
        String query = "INSERT INTO " + MySQL.tableName + "(CATEGORY, HEADNAME, TEXTUREURL) VALUES ('" + category + "','" + headName + "','" + textureURL + "');";
        MySQL.update(query);
    }

    public static void removeHead(String category, String headName) {
        LinkedHashMap<String, ItemStack> categoryHeads = heads.get(category);
        categoryHeads.remove(headName);
        heads.replace(category, categoryHeads);
        String query = "DELETE FROM " + MySQL.tableName + " WHERE CATEGORY= '" + category + "' AND HEADNAME= '" + headName + "'";
        MySQL.update(query);
    }

    public static boolean isCategory(String category) {
        return categories.contains(category);
    }

    public static boolean isHead(String category, String headName) {
        String query = "SELECT * FROM " + MySQL.tableName + " WHERE CATEGORY= '" + category + "' AND HEADNAME= '" + headName + "'";
        try {
            ResultSet rs = MySQL.query(query);
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static LinkedHashMap<String, String> getHeads(String category) {
        LinkedHashMap<String, String> heads = new LinkedHashMap<>();
        String query = "SELECT * FROM " + MySQL.tableName + " WHERE CATEGORY= '" + category + "'";
        try {
            ResultSet rs = MySQL.query(query);
            while (rs.next()) {
                heads.put(rs.getString("HEADNAME"), rs.getString("TEXTUREURL"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return heads;
    }

    public static ItemStack getHead(String category, String headName) {
        String query = "SELECT * FROM " + MySQL.tableName + " WHERE CATEGORY= '" + category + "' AND HEADNAME= '" + headName + "'";
        try {
            ResultSet rs = MySQL.query(query);
            if (rs.next()) {
                return new ItemBuilder(Material.PLAYER_HEAD, "§a" + rs.getString("HEADNAME"), rs.getString("TEXTUREURL")).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ItemStack(Material.BARRIER);
    }
}
