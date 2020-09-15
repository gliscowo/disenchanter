package com.glisco.disenchanter.main;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class MainClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(Main.DISENCHANTER_SCREEN_HANDLER, DisenchanterScreen::new);
    }
}
