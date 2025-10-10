package com.glisco.disenchanter;

import com.glisco.disenchanter.catalyst.Catalyst;
import com.glisco.disenchanter.catalyst.CatalystRegistry;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

public final class DisenchantingLogic {

    private DisenchantingLogic() {}

    public static boolean canDisenchant(Inventory inventory, boolean allowWithoutCatalyst) {
        final ItemStack input = inventory.getStack(0);
        final ItemStack books = inventory.getStack(1);
        final ItemStack catalystStack = inventory.getStack(2);
        final ItemStack output = inventory.getStack(3);

        if (output != ItemStack.EMPTY && !output.isEmpty()) return false;
        if (input.isEmpty() || !input.hasEnchantments() || input.isIn(Disenchanter.BLACKLIST)) return false;
        if (books.isEmpty() || books.getItem() != Items.BOOK) return false;

        if (allowWithoutCatalyst && catalystStack.isEmpty()) return true;

        return CatalystRegistry.get(catalystStack) != Catalyst.DEFAULT;
    }

    public static void performDisenchant(Inventory inventory, Random random, boolean allowWithoutCatalyst) {
        if (!canDisenchant(inventory, allowWithoutCatalyst)) return;

        final ItemStack input = inventory.getStack(0).copy();
        final ItemStack catalystStack = inventory.getStack(2);

        final Catalyst catalyst = CatalystRegistry.get(catalystStack);

        final ItemStack processedInput = catalyst.transformInput(input, random);
        final ItemStack output = catalyst.generateOutput(inventory.getStack(0).copy(), random);

        inventory.setStack(3, output);
        inventory.setStack(0, processedInput);

        decrement(inventory, 1, 1);
        if (!(allowWithoutCatalyst && catalystStack.isEmpty()) && catalyst != Catalyst.DEFAULT) {
            decrement(inventory, 2, CatalystRegistry.getRequiredItemCount(catalyst));
        }
    }

    private static void decrement(Inventory inv, int idx, int by) {
        inv.getStack(idx).decrement(by);
        if (inv.getStack(idx).isEmpty()) inv.setStack(idx, ItemStack.EMPTY);
    }
}


