package com.glisco.disenchanter.catalyst;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

public interface Catalyst {

    Catalyst DEFAULT = new Catalyst() {
        @Override
        public ItemStack transformInput(ItemStack input, Random random) {
            return ItemStack.EMPTY;
        }

        @Override
        public ItemStack generateOutput(ItemStack input, Random random) {
            final var resultStack = new ItemStack(Items.ENCHANTED_BOOK);

            var targetEnchantment = EnchantmentHelper.fromNbt(input.getEnchantments()).keySet().iterator().next();
            EnchantedBookItem.addEnchantment(resultStack, new EnchantmentLevelEntry(targetEnchantment, EnchantmentHelper.getLevel(targetEnchantment, input)));

            return resultStack;
        }
    };

    default ItemStack transformInput(ItemStack input, Random random) {
        return ItemStack.EMPTY;
    }

    ItemStack generateOutput(ItemStack input, Random random);

}
