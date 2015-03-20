package net.dyu.enderpearlcooldown;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.logging.Logger;

public class Enderpearlcooldown extends JavaPlugin implements Listener {

    Logger log = Logger.getLogger("Minecraft");

    HashMap<String, BukkitRunnable> cooldownTask = new HashMap<String, BukkitRunnable>();
    HashMap<String, Integer> cooldownTime = new HashMap<String, Integer>();

    @Override
    public void onEnable() {
        log.info("[EnderpearlCooldown] Enabled.");

        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        log.info("[EnderpearlCooldown] Disabled.");

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        final Player p = e.getPlayer();

        if (p.getItemInHand().getType().equals(Material.ENDER_PEARL)) {
            if (p.getGameMode() == GameMode.SURVIVAL) {

                if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

                    e.setCancelled(true);

                }

                if (e.getAction() == Action.RIGHT_CLICK_AIR) {


                    if (cooldownTime.containsKey(p.getName())) {

                        p.sendMessage(ChatColor.YELLOW + "Pearl cooldown: " + ChatColor.RED + cooldownTime.get(p.getName()) + " seconds");

                        p.updateInventory();
                        e.setCancelled(true);

                    } else {

                        cooldownTime.put(p.getName(), 15);
                        cooldownTask.put(p.getName(), new BukkitRunnable() {

                            public void run() {

                                cooldownTime.put(p.getName(), cooldownTime.get(p.getName()) - 1);

                                if (cooldownTime.get(p.getName()) == 0) {
                                    cooldownTime.remove(p.getName());
                                    cooldownTask.remove(p.getName());

                                    cancel();

                                }

                            }

                        });

                        cooldownTask.get(p.getName()).runTaskTimer(this, 20, 20);

                    }

                }

            }

        }

    }

}


















