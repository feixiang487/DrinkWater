package com.xiaolin233;

import com.xiaolin233.DemoListener.waterlistener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("插件已启用");
        getServer().getPluginManager().registerEvents(new waterlistener(this), this);
    }
    @Override
    public void onDisable() {
        getLogger().info("插件已关闭");
    }
}