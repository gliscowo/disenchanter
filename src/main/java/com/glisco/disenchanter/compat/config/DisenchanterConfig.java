package com.glisco.disenchanter.compat.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.Nest;
import io.wispforest.owo.config.annotation.RangeConstraint;
import io.wispforest.owo.config.annotation.SectionHeader;

@Modmenu(modId = "disenchanter")
@Config(name = "disenchanter", wrapperName = "DisenchanterConfigModel")
public class DisenchanterConfig {

    @SectionHeader("general")
    public boolean allowDisenchantingWithoutCatalyst = true;

    @SectionHeader("catalystSettings")
    @Nest
    public EmeraldSettings emerald = new EmeraldSettings();
    @Nest
    public DiamondSettings diamond = new DiamondSettings();
    @Nest
    public EnderPearlSettings enderPearl = new EnderPearlSettings();
    @Nest
    public HeartOfTheSeaSettings heartOfTheSea = new HeartOfTheSeaSettings();
    @Nest
    public AmethystShardSettings amethystShard = new AmethystShardSettings();
    @Nest
    public NetherStarSettings netherStar = new NetherStarSettings();
    @Nest
    public ExperienceBottleSettings experienceBottle = new ExperienceBottleSettings();

    public static class EmeraldSettings {
        public boolean enabled = true;
        @RangeConstraint(min = 1, max = 64)
        public int requiredItemCount = 1;
    }

    public static class DiamondSettings {
        public boolean enabled = true;
        @RangeConstraint(min = 1, max = 64)
        public int requiredItemCount = 1;
    }

    public static class EnderPearlSettings {
        public boolean enabled = true;
        @RangeConstraint(min = 1, max = 64)
        public int requiredItemCount = 1;
    }

    public static class HeartOfTheSeaSettings {
        public boolean enabled = true;
        @RangeConstraint(min = 1, max = 64)
        public int requiredItemCount = 1;
    }

    public static class AmethystShardSettings {
        public boolean enabled = true;
        @RangeConstraint(min = 1, max = 64)
        public int requiredItemCount = 4;
    }

    public static class NetherStarSettings {
        public boolean enabled = true;
        @RangeConstraint(min = 1, max = 64)
        public int requiredItemCount = 1;
    }

    public static class ExperienceBottleSettings {
        public boolean enabled = true;
        @RangeConstraint(min = 1, max = 64)
        public int requiredItemCount = 1;
    }

    // Helper methods for legacy Map-based access
    public boolean isCatalystEnabled(String itemId) {
        return switch (itemId) {
            case "minecraft:emerald" -> emerald.enabled;
            case "minecraft:diamond" -> diamond.enabled;
            case "minecraft:ender_pearl" -> enderPearl.enabled;
            case "minecraft:heart_of_the_sea" -> heartOfTheSea.enabled;
            case "minecraft:amethyst_shard" -> amethystShard.enabled;
            case "minecraft:nether_star" -> netherStar.enabled;
            case "minecraft:experience_bottle" -> experienceBottle.enabled;
            default -> false;
        };
    }

    public int getRequiredItemCount(String itemId) {
        return switch (itemId) {
            case "minecraft:emerald" -> emerald.requiredItemCount;
            case "minecraft:diamond" -> diamond.requiredItemCount;
            case "minecraft:ender_pearl" -> enderPearl.requiredItemCount;
            case "minecraft:heart_of_the_sea" -> heartOfTheSea.requiredItemCount;
            case "minecraft:amethyst_shard" -> amethystShard.requiredItemCount;
            case "minecraft:nether_star" -> netherStar.requiredItemCount;
            case "minecraft:experience_bottle" -> experienceBottle.requiredItemCount;
            default -> 1;
        };
    }
}
