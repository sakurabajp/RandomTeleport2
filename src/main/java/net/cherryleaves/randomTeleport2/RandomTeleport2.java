package net.cherryleaves.randomTeleport2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public final class RandomTeleport2 extends JavaPlugin implements @NotNull Listener {


    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        super.onDisable();
    }

    @EventHandler
    public void BlockBreak(BlockBreakEvent e) {
        int per = random();
        if(per < 100) {
            Player p = e.getPlayer();
            int highestY = 0;
            int X = random();
            int aY = 0;
            int Z = random();
            Material highestNonAirBlock = Material.AIR;
            if (e.getPlayer().getWorld().getName().equals("world_nether")) {
                X = random();
                Z = random();
                for (int y = highestY; y <= 128; y++) {
                    Material blockType = p.getWorld().getBlockAt(X, y, Z).getType();
                    aY = y;
                    if (blockType == Material.AIR && !p.getWorld().getBlockAt(X, y - 1, Z).getType().equals(Material.LAVA)) {
                        if (p.getWorld().getBlockAt(X, y - 2, Z).getType().equals(Material.LAVA)) {
                            p.getWorld().getBlockAt(X, y - 1, Z).setType(Material.GLASS);
                            p.sendMessage("飛ばされた先が溶岩の上だったのでブロックを敷いたよ！");
                        }
                        break;
                    }
                }
            }
            if (e.getPlayer().getWorld().getName().equals("world_the_end")) {
                highestY = e.getPlayer().getWorld().getMaxHeight();
                for (int y = highestY - 1; y >= 0; y--) {
                    Material blockType = p.getWorld().getBlockAt(X, y, Z).getType();
                    aY = y;
                    if (blockType != Material.AIR) {
                        highestNonAirBlock = blockType;
                        break;
                    }
                }
                if (aY == 0) {
                    aY = 60;
                    p.getWorld().getBlockAt(X, aY - 1, Z).setType(Material.GLASS);
                    p.sendMessage("飛ばされた先に何もなかったのでブロックを敷いたよ！");
                }
            } else {
                highestY = e.getPlayer().getWorld().getMaxHeight();
                for (int y = highestY - 1; y >= -64; y--) {
                    Material blockType = p.getWorld().getBlockAt(X, y, Z).getType();
                    aY = y;
                    if (blockType != Material.AIR) {
                        highestNonAirBlock = blockType;
                        break;
                    }
                }
            }
            Location a = new Location(p.getWorld(), X, aY + 1, Z);
            e.getPlayer().teleport(a);
        }
    }

    public static int random() {
        Random rand = new Random();
        return rand.nextInt(2000);
    }
}
