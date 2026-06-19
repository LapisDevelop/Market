package com.lapisdev.market;

import org.bukkit.plugin.java.JavaPlugin;
import com.lapisdev.database.DatabaseConnectionManager;

public final class LapisMarket extends JavaPlugin {

    @Override
    public void onEnable() {
        getDataFolder().mkdirs();
        new DatabaseConnectionManager("jdbc:sqlite:" + getDataPath() + "/market.db");

        new LapisMarketItem().createTable();
    }

    @Override
    public void onDisable() {}
}
