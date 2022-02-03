package de.zillolp.headdatabase.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {
    private ItemStack itemstack;
    private Material material;
    private String displayName;
    private List<String> lore;
    private boolean enchanted;
    private String textureURL;

    public ItemBuilder(Material material) {
        this.material = material;
    }

    public ItemBuilder(Material material, String displayName) {
        this.material = material;
        this.displayName = displayName;
    }

    public ItemBuilder(Material material, String displayName, String textureURL) {
        this.material = material;
        this.displayName = displayName;
        this.textureURL = textureURL;
    }

    public ItemBuilder(Material material, String displayName, String[] lore) {
        this.material = material;
        this.displayName = displayName;
        this.lore = Arrays.asList(lore);
    }

    public ItemBuilder(Material material, String displayName, String[] lore, String textureURL) {
        this.material = material;
        this.displayName = displayName;
        this.lore = Arrays.asList(lore);
        this.textureURL = textureURL;
    }

    public ItemBuilder(Material material, String displayName, boolean enchanted) {
        this.material = material;
        this.displayName = displayName;
        this.enchanted = enchanted;
    }

    public ItemStack build() {
        itemstack = new ItemStack(material);
        ItemMeta itemMeta = itemstack.getItemMeta();
        if (displayName != null) {
            itemMeta.setDisplayName(displayName);
        }
        if (lore != null) {
            itemMeta.setLore(lore);
        }
        if (enchanted) {
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if (textureURL != null) {
            try {
                GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
                if (textureURL.contains("http://textures.minecraft.net/texture/")) {
                    gameProfile.getProperties().put("textures", new Property("textures", Base64Coder.encodeString("{textures:{SKIN:{url:\"" + textureURL + "\"}}}")));
                } else {
                    gameProfile.getProperties().put("textures", new Property("textures", Base64Coder.encodeString("{textures:{SKIN:{url:\"" + "http://textures.minecraft.net/texture/" + textureURL + "\"}}}")));
                }
                Field profileField = itemMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(itemMeta, gameProfile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        itemstack.setItemMeta(itemMeta);
        return itemstack;
    }
}
