package com.glisco.disenchanter.client;

import com.glisco.disenchanter.Disenchanter;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DisenchanterClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(Disenchanter.DISENCHANTER_SCREEN_HANDLER, DisenchanterScreen::new);

        ClientPlayNetworking.registerGlobalReceiver(new Identifier(Disenchanter.MOD_ID, "disenchant_event"), (client, handler, buf, responseSender) -> {
            var pos = buf.readBlockPos();
            client.execute(() -> {
                client.player.playSound(SoundEvents.ITEM_TOTEM_USE, SoundCategory.BLOCKS, .5f, .8f + client.world.random.nextFloat() * .4f);

                for (int i = 0; i < 100; i++) {
                    client.world.addParticle(ParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 0.685, pos.getZ() + 0.5, 0, 0, 0);
                }
            });

        });
    }
}
