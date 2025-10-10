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

    @SectionHeader("catalystBehaviors")
    @Nest
    public TwoRandomBehavior twoRandom = new TwoRandomBehavior("minecraft:emerald", 1);
    @Nest
    public FirstPlusTwoRandomBehavior firstPlusTwoRandom = new FirstPlusTwoRandomBehavior("minecraft:diamond", 1);
    @Nest
    public OneRandomPreserveItemBehavior oneRandomPreserveItem = new OneRandomPreserveItemBehavior("minecraft:ender_pearl", 1);
    @Nest
    public AllReducedLevelBehavior allReducedLevel = new AllReducedLevelBehavior("minecraft:heart_of_the_sea", 1);
    @Nest
    public FirstOnlyPreserveItemBehavior firstOnlyPreserveItem = new FirstOnlyPreserveItemBehavior("minecraft:amethyst_shard", 4);
    @Nest
    public AllFullLevelPreserveItemBehavior allFullLevelPreserveItem = new AllFullLevelPreserveItemBehavior("minecraft:nether_star", 1);
    @Nest
    public MaxLevelOnlyBehavior maxLevelOnly = new MaxLevelOnlyBehavior("minecraft:experience_bottle", 1);

    public static class TwoRandomBehavior {
        public boolean enabled = true;
        public String itemId = "minecraft:emerald";
        @RangeConstraint(min = 1, max = 64)
        public int requiredItemCount = 1;
        
        public TwoRandomBehavior() {}
        public TwoRandomBehavior(String itemId, int count) {
            this.itemId = itemId;
            this.requiredItemCount = count;
        }
    }

    public static class FirstPlusTwoRandomBehavior {
        public boolean enabled = true;
        public String itemId = "minecraft:diamond";
        @RangeConstraint(min = 1, max = 64)
        public int requiredItemCount = 1;
        
        public FirstPlusTwoRandomBehavior() {}
        public FirstPlusTwoRandomBehavior(String itemId, int count) {
            this.itemId = itemId;
            this.requiredItemCount = count;
        }
    }

    public static class OneRandomPreserveItemBehavior {
        public boolean enabled = true;
        public String itemId = "minecraft:ender_pearl";
        @RangeConstraint(min = 1, max = 64)
        public int requiredItemCount = 1;
        
        public OneRandomPreserveItemBehavior() {}
        public OneRandomPreserveItemBehavior(String itemId, int count) {
            this.itemId = itemId;
            this.requiredItemCount = count;
        }
    }

    public static class AllReducedLevelBehavior {
        public boolean enabled = true;
        public String itemId = "minecraft:heart_of_the_sea";
        @RangeConstraint(min = 1, max = 64)
        public int requiredItemCount = 1;
        
        public AllReducedLevelBehavior() {}
        public AllReducedLevelBehavior(String itemId, int count) {
            this.itemId = itemId;
            this.requiredItemCount = count;
        }
    }

    public static class FirstOnlyPreserveItemBehavior {
        public boolean enabled = true;
        public String itemId = "minecraft:amethyst_shard";
        @RangeConstraint(min = 1, max = 64)
        public int requiredItemCount = 4;
        
        public FirstOnlyPreserveItemBehavior() {}
        public FirstOnlyPreserveItemBehavior(String itemId, int count) {
            this.itemId = itemId;
            this.requiredItemCount = count;
        }
    }

    public static class AllFullLevelPreserveItemBehavior {
        public boolean enabled = true;
        public String itemId = "minecraft:nether_star";
        @RangeConstraint(min = 1, max = 64)
        public int requiredItemCount = 1;
        
        public AllFullLevelPreserveItemBehavior() {}
        public AllFullLevelPreserveItemBehavior(String itemId, int count) {
            this.itemId = itemId;
            this.requiredItemCount = count;
        }
    }

    public static class MaxLevelOnlyBehavior {
        public boolean enabled = true;
        public String itemId = "minecraft:experience_bottle";
        @RangeConstraint(min = 1, max = 64)
        public int requiredItemCount = 1;
        
        public MaxLevelOnlyBehavior() {}
        public MaxLevelOnlyBehavior(String itemId, int count) {
            this.itemId = itemId;
            this.requiredItemCount = count;
        }
    }

}
