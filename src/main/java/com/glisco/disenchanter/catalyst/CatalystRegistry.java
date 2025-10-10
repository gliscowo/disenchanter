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
        if (REGISTRY.containsKey(item)) {
            // Allow re-registration for config reloads
            REGISTRY.remove(item);
        }
        if (!enabled) return;

        REGISTRY.put(item, new CatalystEntry(catalyst, requiredCount));
    }

    public static void reload() {
        REGISTRY.clear();
    }

    public static void registerBehavior(String itemId, Catalyst catalyst, boolean enabled, int requiredCount) {
        if (!enabled) {
            System.out.println("[Disenchanter] Skipping disabled catalyst: " + itemId);
            return;
        }
        
        var identifier = net.minecraft.util.Identifier.tryParse(itemId);
        if (identifier == null) {
            System.err.println("[Disenchanter] Invalid item ID for catalyst: " + itemId);
            return;
        }
        
        var item = Registries.ITEM.get(identifier);
        if (item == null || item == net.minecraft.item.Items.AIR) {
            System.err.println("[Disenchanter] Item not found for catalyst: " + itemId);
            return;
        }
        
        System.out.println("[Disenchanter] Registering catalyst: " + itemId + " (x" + requiredCount + ") -> " + catalyst.getClass().getSimpleName());
        register(item, catalyst, true, requiredCount);
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
