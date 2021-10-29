package com.glisco.disenchanter.catalyst;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CatalystRegistry {

    private static final Map<Item, Catalyst> REGISTRY = new HashMap<>();

    public static void register(Item item, Catalyst catalyst) {
        if (REGISTRY.containsKey(item)) throw new IllegalArgumentException("Attempted to register catalyst for item " + item + "twice");
        REGISTRY.put(item, catalyst);
    }

    public static Catalyst get(Item item) {
        return REGISTRY.getOrDefault(item, Catalyst.DEFAULT);
    }

    public static boolean isCatalyst(Item item) {
        return REGISTRY.containsKey(item);
    }

    public static List<Item> getCatalysts() {
        return new ArrayList<>(REGISTRY.keySet());
    }

}
