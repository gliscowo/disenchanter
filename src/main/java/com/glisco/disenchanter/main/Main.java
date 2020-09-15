package com.glisco.disenchanter.main;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer {

    public static final Block DISENCHANTER_BLOCK = new DisenchanterBlock(FabricBlockSettings.of(Material.METAL).hardness(5f));
    public static BlockEntityType<DisenchanterBlockEntity> DISENCHANTER_BLOCK_ENTITY;

    public static final ScreenHandlerType<DisenchanterScreenHandler> DISENCHANTER_SCREEN_HANDLER;
    public static final Identifier DISENCHANTER = new Identifier("disenchanter", "disenchanter");

    static {
        DISENCHANTER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(DISENCHANTER, DisenchanterScreenHandler::new);
    }

    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier("disenchanter", "disenchanter"), DISENCHANTER_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("disenchanter", "disenchanter"), new BlockItem(DISENCHANTER_BLOCK, new Item.Settings().group(ItemGroup.MISC)));

        DISENCHANTER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "disenchanter:disenchanter", BlockEntityType.Builder.create(DisenchanterBlockEntity::new, DISENCHANTER_BLOCK).build(null));
    }
}
