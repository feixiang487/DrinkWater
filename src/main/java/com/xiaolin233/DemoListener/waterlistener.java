package com.xiaolin233.DemoListener;

import com.xiaolin233.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import java.util.Random;

public class waterlistener implements Listener {
    private final BossBar bossBar;
    private final int watermath;
    private final Main main;
    private int watersum = 6000;
    public waterlistener(Main main) {
        this.main = main;
        this.watermath = main.getConfig().getInt("watermath"
                .






















                );
        this.bossBar = Bukkit.createBossBar("当前口渴值", BarColor.BLUE, BarStyle.SOLID);
    }

    @EventHandler
    public void playerbossbar(PlayerJoinEvent e){
        e.setJoinMessage("欢迎："+e.getPlayer().getName()+"玩家使用饮水插件");
        bossBar.setProgress(1.0);
        bossBar.addPlayer(e.getPlayer());
    }
    @EventHandler
    public void playerhard(PlayerItemConsumeEvent e){
        Player player = e.getPlayer();
        ItemStack item = e.getItem();
        Random rand =  new Random();
        if (item.getType().equals(Material.POTION)) {
            int a = rand.nextInt(watermath) + 51;
            if (watersum >= 6000) {
                player.sendMessage(ChatColor.RED + "你不需要喝太多水");
                watersum = 6000;
                player.sendMessage(ChatColor.GREEN + "当前口渴值:" + watersum);
                setProgressBar(watersum);
                return;
            }
            player.sendMessage(ChatColor.RED + "你喝了一瓶水，口渴值加:" + a);
            watersum += a;
            setProgressBar(watersum);
        }
    }
    @EventHandler
    public void runplayer(PlayerAnimationEvent e){
        Player player = e.getPlayer();
        PlayerAnimationType animationType = e.getAnimationType();
        if (animationType.equals(PlayerAnimationType.ARM_SWING)) {
            watersum -= 1;
            setProgressBar( watersum);
            if (watersum == 150) {
                player.sendMessage(ChatColor.RED + "你的口渴值:" + watersum + "你必须喝水");
            }
            if (watersum <= 0) {
                player.sendMessage("你的口渴值为0,你已经死亡，已经恢复你的口渴值");
                watersum = 6000;
            }
        }
    }

    @EventHandler
    public void closebossbar(){
        bossBar.removeAll();
    }
    @EventHandler
    public void runplayer(PlayerMoveEvent e){
        if (!e.isCancelled()){
            watersum -= 1;
            setProgressBar(watersum);
        }
    }

    private void setProgressBar(int currentSum) {
        double progress = Math.min(Math.max(currentSum / 6000.0, 0.0), 1.0);
        bossBar.setProgress(progress);
    }
}
