package com.lapisdev.market;

import com.lapisdev.tasks.RunTask;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import com.lapisdev.database.DatabaseConnectionManager;

public final class LapisMarket extends JavaPlugin {

    @Override
    public void onEnable() {
        RunTask.plugin = this;
        getDataFolder().mkdirs();
        new DatabaseConnectionManager("jdbc:sqlite:" + getDataPath() + "/market.db");

        new LapisMarketItem().createTable();

        getServer().getPluginManager().registerEvents(new MarketListener(), this);
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, registry -> {
            MarketCommand.register(registry.registrar());
        });
    }

    @Override
    public void onDisable() {}
}
