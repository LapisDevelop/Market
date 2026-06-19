package com.lapisdev.market;

import com.lapisdev.database.DatabaseTable;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.UUID;

public class LapisMarketItem implements DatabaseTable<LapisMarketItem> {
    public int id;
    public String owner;
    public String material;
    public int price;
    public int quantity;
    public int stock;
    public int chestX;
    public int chestY;
    public int chestZ;
    public int signX;
    public int signY;
    public int signZ;

    public LapisMarketItem() {}

    public LapisMarketItem(UUID owner, Material material, int price, int quantity, Location location, Location signLocation) {
        this.owner = owner.toString();
        this.material = material.toString();
        this.price = price;
        this.quantity = quantity;
        this.chestX = location.getBlockX();
        this.chestY = location.getBlockY();
        this.chestZ = location.getBlockZ();
        this.signX = signLocation.getBlockX();
        this.signY = signLocation.getBlockY();
        this.signZ = signLocation.getBlockZ();
        this.stock = 0;
    }
}
