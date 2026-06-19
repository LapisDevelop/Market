package com.lapisdev.market;

import com.lapisdev.tasks.RunTask;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.Comparator;

public class MarketCommand {
    public static void register(Commands commands) {
        commands.register(Commands.literal("market")
                .then(Commands.argument("material", StringArgumentType.string())
                        .executes(MarketCommand::executeSearch))
                .executes(MarketCommand::executeList).build());
    }

    public static int executeList(CommandContext<CommandSourceStack> ctx) {
        RunTask.async(() -> {
            ArrayList<LapisMarketItem> items = new LapisMarketItem().select();
            items.removeIf(item -> item.stock < item.quantity);
            items.sort(Comparator.comparingDouble(item -> (double) item.price / item.quantity));

            ctx.getSource().getSender().sendMessage(Component.text("--- Market ---", NamedTextColor.GOLD));
            if (items.isEmpty()) {
                ctx.getSource().getSender().sendMessage(Component.text("No shops found with enough stock.", NamedTextColor.RED));
            } else {
                for (LapisMarketItem item : items) {
                    ctx.getSource().getSender().sendMessage(formatShopItem(item));
                }
            }
        });
        return 1;
    }

    public static int executeSearch(CommandContext<CommandSourceStack> ctx) {
        String materialName = StringArgumentType.getString(ctx, "material").toUpperCase();
        RunTask.async(() -> {
            ArrayList<LapisMarketItem> items = new LapisMarketItem().select();
            items.removeIf(item -> !item.material.equalsIgnoreCase(materialName) || item.stock < item.quantity);
            items.sort(Comparator.comparingDouble(item -> (double) item.price / item.quantity));

            ctx.getSource().getSender().sendMessage(Component.text("--- Market: " + formatMaterial(materialName) + " ---", NamedTextColor.GOLD));
            if (items.isEmpty()) {
                ctx.getSource().getSender().sendMessage(Component.text("No shops found with enough stock.", NamedTextColor.RED));
            } else {
                for (LapisMarketItem item : items) {
                    ctx.getSource().getSender().sendMessage(formatShopItem(item));
                }
            }
        });
        return 1;
    }

    private static Component formatShopItem(LapisMarketItem item) {
        String materialName = formatMaterial(item.material);
        return Component.text(materialName + ": ", NamedTextColor.YELLOW)
                .append(Component.text(item.quantity + " for " + item.price + " ", NamedTextColor.WHITE))
                .append(Component.text("(Stock: " + item.stock + ") ", NamedTextColor.GRAY))
                .append(Component.text("@ " + item.chestX + " " + item.chestY + " " + item.chestZ, NamedTextColor.AQUA));
    }

    public static String formatMaterial(String material) {
        String name = material.toLowerCase().replace("_", " ");
        String[] words = name.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}
