package com.glisco.disenchanter.catalyst;

import com.glisco.disenchanter.Disenchanter;
import com.glisco.disenchanter.compat.config.DisenchanterConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public final class CatalystRegistry {

    private static final Map<Item, CatalystEntry> REGISTRY = new HashMap<>();

    public static void register( DisenchanterConfig.CatalystConfig config, Catalyst catalyst) {
        if (!config.enabled) return;
        
        Identifier itemIdentifier = new Identifier(config.item);

        Item item = Registry.ITEM.get(itemIdentifier);

        if (item == null) throw new IllegalArgumentException("Attempted to register catalyst for unknown item " + itemIdentifier);
        if (REGISTRY.containsKey(item)) throw new IllegalArgumentException("Attempted to register catalyst for item " + item + "twice");

        REGISTRY.put(item, new CatalystEntry(catalyst, config.required_item_count));
    }

    public static void registerFromConfig(String configKey, Catalyst catalyst) {
        register(Disenchanter.getConfig().catalysts.get(configKey), catalyst);
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
