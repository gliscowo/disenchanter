package com.glisco.disenchanter.compat.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import java.util.HashMap;
import java.util.Map;

@Config(name = "disenchanter")
public class DisenchanterConfig implements ConfigData {

    public Map<String, CatalystConfig> catalysts = new HashMap<>();

    public DisenchanterConfig() {
        catalysts.put("minecraft:emerald", new CatalystConfig("minecraft:emerald"));
        catalysts.put("minecraft:diamond", new CatalystConfig("minecraft:diamond"));
        catalysts.put("minecraft:ender_pearl", new CatalystConfig("minecraft:ender_pearl"));
        catalysts.put("minecraft:heart_of_the_sea", new CatalystConfig("minecraft:heart_of_the_sea"));
        catalysts.put("minecraft:amethyst_shard", new CatalystConfig("minecraft:amethyst_shard"));
        catalysts.put("minecraft:nether_star", new CatalystConfig("minecraft:nether_star"));
        catalysts.put("minecraft:experience_bottle", new CatalystConfig("minecraft:experience_bottle"));
    }

    public static class CatalystConfig {
        public boolean enabled = true;
        public String item;
        public int required_item_count = 1;

        CatalystConfig(String item_name) {
            item = item_name;
        }
    }

}
