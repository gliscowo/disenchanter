package com.glisco.disenchanter.client;

import com.glisco.disenchanter.Disenchanter;
import com.glisco.disenchanter.DisenchanterScreenHandler;
import com.glisco.disenchanter.catalyst.Catalyst;
import com.glisco.disenchanter.catalyst.CatalystRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.text.WordUtils;

@Environment(EnvType.CLIENT)
public class DisenchanterClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(Disenchanter.DISENCHANTER_SCREEN_HANDLER, DisenchanterScreen::new);

        ClientPlayNetworking.registerGlobalReceiver(new Identifier(Disenchanter.MOD_ID, "disenchant_event"), (client, handler, buf, responseSender) -> {
            var pos = buf.readBlockPos();
            client.execute(() -> {
                client.world.playSound(pos, SoundEvents.ITEM_TOTEM_USE, SoundCategory.BLOCKS, .5f, .8f + client.world.random.nextFloat() * .4f, false);

                for (int i = 0; i < 100; i++) {
                    client.world.addParticle(ParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 0.685, pos.getZ() + 0.5, 0, 0, 0);
                }
            });
        });

        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            if (MinecraftClient.getInstance().player == null) return;
            if (!(MinecraftClient.getInstance().player.currentScreenHandler instanceof DisenchanterScreenHandler handler)) return;

            if (stack.isIn(Disenchanter.BLACKLIST)) {
                lines.add(1, Text.translatable("text.disenchanter.blacklisted").formatted(Formatting.DARK_GRAY));
            }

            final var catalyst = CatalystRegistry.getUnchecked(stack);
            if (catalyst == Catalyst.DEFAULT) return;

            final var catalystStack = handler.getSlot(2).getStack();
            if (!catalystStack.isEmpty() && stack == catalystStack) {
                int requiredCount = CatalystRegistry.getRequiredItemCount(catalyst);
                if (catalystStack.getCount() < requiredCount) {
                    lines.add(new TranslatableText("text.disenchanter.extra_catalysts_required", requiredCount - catalystStack.getCount())
                            .formatted(Formatting.RED));
                }
            }

            if (catalyst != null) {
                final String description = I18n.translate("disenchanter.catalyst." + stack.getItem().getRegistryEntry().registryKey().getValue().getPath());
                for (var line : WordUtils.wrap(description, 35).split("\n")) {
                    lines.add(new LiteralText(line).formatted(Formatting.GRAY));
                }
            }
        });
    }
}
