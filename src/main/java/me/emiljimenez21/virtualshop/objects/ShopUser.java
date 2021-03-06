package me.emiljimenez21.virtualshop.objects;

import me.emiljimenez21.virtualshop.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/** version checker credit to Zombie_Striker from bukkit.org forums */

private static final String SERVER_VERSION;
static {
    String name = Bukkit.getServer().getClass().getName();
    name = name.substring(name.indexOf("craftbukkit.") + "craftbukkit.".length());
    name = name.substring(0, name.indexOf("."));
    SERVER_VERSION = name;
}

public static boolean isVersionHigherThan(int mainVersion, int secondVersion) {
    String firstChar = SERVER_VERSION.substring(1, 2);
    int fInt = Integer.parseInt(firstChar);
    if (fInt < mainVersion)
        return false;
    StringBuilder secondChar = new StringBuilder();
    for (int i = 3; i < 10; i++) {
        if (SERVER_VERSION.charAt(i) == '_' || SERVER_VERSION.charAt(i) == '.')
            break;
        secondChar.append(SERVER_VERSION.charAt(i));
    }

    int sInt = Integer.parseInt(secondChar.toString());
    if (sInt <= secondVersion)
        return false;
    return true;
}

public class ShopUser {
    public UUID uuid = null;
    public String name = null;
    public Inventory inventory = null;
    public Player player = null;

    /**
     * Create a shop user by a string UUID and a name
     *
     * @param uuid The UUID of a player
     * @param name The Name of a player
     */
    public ShopUser(String uuid, String name) {
        this(UUID.fromString(uuid), name);
    }

    /**
     * Create a shop user using a player
     *
     * @param player The player that this user will represent
     */
    public ShopUser(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.inventory = player.getInventory();
    }


    /**
     * Create a shop user by a UUID and their Name
     * @param uuid The UUID of the player
     * @param name The name of the player associated with the UUID
     */
    public ShopUser(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.player = Bukkit.getPlayer(uuid);
        if (this.player == null) {
            this.player = Bukkit.getOfflinePlayer(uuid).getPlayer();
        }

        if(this.player != null) {
            this.inventory = player.getInventory();
        }
    }



    public void playErrorSound() {
        if(Settings.sound)
            try {
                if(isVersionHigherThan(1,12)){
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                }
                else{
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 1, 1);
                }
            } catch (Exception e){
                // Do Nothing
            }
    }

    public void playPostedListing() {
        if(Settings.sound)
            try {
                if(isVersionHigherThan(1,12)){
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
                }
                else{
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BELL, 1, 1);
                }
            } catch (Exception e){
                // Do Nothing
            }
    }

    public void playCancelledListing() {
        if(Settings.sound)
            try {
                if(isVersionHigherThan(1,12)){
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                }
                else{
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 1, 1);
                }
            } catch (Exception e){
                // Do Nothing
            }
    }

    public void playPurchased() {
        if(Settings.sound)
            try {
                if(isVersionHigherThan(1,12)){
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 1);
                }
                else{
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_CHIME, 1, 1);
                }
            } catch (Exception e){
                // Do Nothing
            }
    }

    public void playProductSold() {
        if(Settings.sound)
            try {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            } catch (Exception e){
                // Do Nothing
            }
    }

    public int getAvailableSlots() {
        int quantity = 0;

        for(int i = 0; i < this.inventory.getSize()-5; i++) {
            ItemStack slot = this.inventory.getItem(i);

            if (slot == null) {
                quantity++;
            }
        }
        return quantity;
    }

    public int getQuantity(ItemStack lookup){
        int quantity = 0;

        for(int i = 0; i < this.inventory.getSize(); i++) {
            ItemStack slot = this.inventory.getItem(i);

            if(slot == null) {
                continue;
            }

            ShopItem sItem = new ShopItem(slot);

            if(!sItem.isModified()) {
                if(slot.getType() == lookup.getType()) {
                    quantity += slot.getAmount();
                }
            }
        }

        return quantity;
    }
}
