package com.yikuni.mc.alicechatgptmcplugin;

import com.yikuni.mc.reflect.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

public final class AliceChatgptPlugin extends JavaPlugin {
    public static String HOST;
    public static String TOKEN;

    @Override
    public void onEnable() {
        // Plugin startup logic
        PluginLoader.run(AliceChatgptPlugin.class);

        HOST = getConfig().getString("host");
        TOKEN = getConfig().getString("token");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
