package com.glisco.disenchanter.catalyst;

import com.glisco.disenchanter.Disenchanter;
import com.glisco.disenchanter.compat.config.DisenchanterConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public final class CatalystRegistry {

    private static final Map<Item, CatalystEntry> REGISTRY = new HashMap<>();

    public static void register(Item item, Catalyst catalyst, boolean enabled, int requiredCount) {
        if (REGISTRY.containsKey(item)) throw new IllegalArgumentException("Attempted to register catalyst for item " + item + "twice");
        if (!enabled) return;

        REGISTRY.put(item, new CatalystEntry(catalyst, requiredCount));
    }

    public static void registerFromConfig(Item item, Catalyst catalyst) {
        var itemId = Registries.ITEM.getId(item).toString();
        var config = Disenchanter.getConfig();
        
        switch (itemId) {
            case "minecraft:emerald" -> register(item, catalyst, config.emerald.enabled(), config.emerald.requiredItemCount());
            case "minecraft:diamond" -> register(item, catalyst, config.diamond.enabled(), config.diamond.requiredItemCount());
            case "minecraft:ender_pearl" -> register(item, catalyst, config.enderPearl.enabled(), config.enderPearl.requiredItemCount());
            case "minecraft:heart_of_the_sea" -> register(item, catalyst, config.heartOfTheSea.enabled(), config.heartOfTheSea.requiredItemCount());
            case "minecraft:amethyst_shard" -> register(item, catalyst, config.amethystShard.enabled(), config.amethystShard.requiredItemCount());
            case "minecraft:nether_star" -> register(item, catalyst, config.netherStar.enabled(), config.netherStar.requiredItemCount());
            case "minecraft:experience_bottle" -> register(item, catalyst, config.experienceBottle.enabled(), config.experienceBottle.requiredItemCount());
        }
    }

    public static Catalyst get(ItemStack stack) {
        var entry = REGISTRY.get(stack.getItem());
        if (entry == null) return Catalyst.DEFAULT;
        if (stack.getCount() < entry.amount) return Catalyst.DEFAULT;
        return entry.catalyst;
    }

    public static Catalyst getUnchecked(ItemStack stack) {
        final var entry = REGISTRY.get(stack.getItem());
        return entry == null ? Catalyst.DEFAULT : entry.catalyst;
    }

    public static int getRequiredItemCount(Catalyst catalyst) {
        final var candidate = REGISTRY.values().stream().filter(catalystEntry -> catalystEntry.catalyst == catalyst).findAny();
        return candidate.isEmpty() ? -1 : candidate.get().amount;
    }

    public static void forEach(BiConsumer<Item, CatalystEntry> action) {
        REGISTRY.forEach(action);
    }

    public static boolean isCatalyst(Item item) {
        return REGISTRY.containsKey(item);
    }

    public static List<Item> getCatalysts() {
        return new ArrayList<>(REGISTRY.keySet());
    }

    public record CatalystEntry(Catalyst catalyst, int amount) {}

}
