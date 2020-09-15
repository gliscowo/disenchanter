package com.glisco.disenchanter.main;

import jdk.nashorn.internal.ir.Block;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BookItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;

import java.util.Map;

public class DisenchanterScreenHandler extends ScreenHandler {

    ScreenHandlerContext context;
    Inventory inventory;

    //This constructor gets called on the client when the server wants it to open the screenHandler,
    //The client will call the other constructor with an empty Inventory and the screenHandler will automatically
    //sync this empty inventory with the inventory on the server.
    public DisenchanterScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    //This constructor gets called from the BlockEntity on the server without calling the other constructor first, the server knows the inventory of the container
    //and can therefore directly provide it as an argument. This inventory will then be synced to the client.
    public DisenchanterScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(Main.DISENCHANTER_SCREEN_HANDLER, syncId);
        this.inventory = new SimpleInventory(3);
        this.context = context;
        this.addSlot(new Slot(inventory, 0, 17, 52) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.hasEnchantments();
            }
        });
        this.addSlot(new Slot(inventory, 1, 143, 52) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof BookItem;
            }
        });
        this.addSlot(new Slot(inventory, 2, 80, 17) {
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });

        //This will place the slot in the correct locations for a 3x3 Grid. The slots exist on both server and client!
        //This will not render the background of the slots however, this is the Screens job
        int m;
        int l;
        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }

    }


    public boolean onButtonClick(PlayerEntity player, int id) {
        if (!(player instanceof ServerPlayerEntity)) {
            for (int i = 0; i < 100; i++) {
                BlockPos pos =  ((BlockHitResult)player.rayTrace(5, 0, false)).getBlockPos();
                player.world.addParticle(ParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 0.685, pos.getZ() + 0.5, 0, 0, 0);
            }
        }

        ItemStack newBook = new ItemStack(Items.ENCHANTED_BOOK);
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.fromTag(inventory.getStack(0).getEnchantments());
        Enchantment enchantment = enchantments.keySet().iterator().next();
        EnchantedBookItem.addEnchantment(newBook, new EnchantmentLevelEntry(enchantment, enchantments.get(enchantment)));
        inventory.setStack(2, newBook);

        inventory.setStack(0, ItemStack.EMPTY);
        inventory.getStack(1).decrement(1);
        if (inventory.getStack(1).isEmpty()) {
            inventory.setStack(1, ItemStack.EMPTY);
        }
        this.sendContentUpdates();
        return true;
    }

    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, Main.DISENCHANTER_BLOCK);
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, blockPos) -> {
            this.dropInventory(player, player.world, this.inventory);
        });
    }
}