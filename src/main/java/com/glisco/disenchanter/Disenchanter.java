package com.glisco.disenchanter;

import com.glisco.disenchanter.catalyst.Catalysts;
import com.glisco.disenchanter.compat.config.DisenchanterConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Disenchanter implements ModInitializer {

    public static final String MOD_ID = "disenchanter";
    public static final Identifier DISENCHANTER_HANDLER_ID = new Identifier(MOD_ID, "disenchanter");

    public static final Block DISENCHANTER_BLOCK = new DisenchanterBlock();
    public static final ScreenHandlerType<DisenchanterScreenHandler> DISENCHANTER_SCREEN_HANDLER;

    private static DisenchanterConfig CONFIG;

    static {
        DISENCHANTER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(DISENCHANTER_HANDLER_ID, DisenchanterScreenHandler::new);
    }

    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "disenchanter"), DISENCHANTER_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "disenchanter"), new BlockItem(DISENCHANTER_BLOCK, new Item.Settings().group(ItemGroup.MISC)));

        AutoConfig.register(DisenchanterConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(DisenchanterConfig.class).get();

        Catalysts.registerDefaults();

        ServerPlayNetworking.registerGlobalReceiver(new Identifier(MOD_ID, "disenchant_request"), (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (!(player.currentScreenHandler instanceof DisenchanterScreenHandler disenchanter)) return;
                disenchanter.onDisenchantRequest();
            });
        });
    }

    public static DisenchanterConfig getConfig() {
        return CONFIG;
    }
}
