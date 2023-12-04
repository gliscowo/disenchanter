package com.glisco.disenchanter.client;

import com.glisco.disenchanter.Disenchanter;
import com.glisco.disenchanter.DisenchanterScreenHandler;
import com.glisco.disenchanter.catalyst.Catalyst;
import com.glisco.disenchanter.catalyst.CatalystRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DisenchanterScreen extends HandledScreen<DisenchanterScreenHandler> {

    private static final Identifier TEXTURE = new Identifier("disenchanter", "textures/gui/container/disenchanter.png");

    private ButtonWidget disenchantButton = null;
    private boolean validCatalyst = true;

    public DisenchanterScreen(DisenchanterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        this.backgroundHeight = 190;
        this.playerInventoryTitleY += 24;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);

        disenchantButton.active = this.handler.getSlot(0).hasStack() && this.handler.getSlot(1).hasStack()
                && this.handler.getSlot(0).getStack().hasEnchantments() && !this.handler.getSlot(3).hasStack()
                && this.validCatalyst;
    }

    @Override
    protected void handledScreenTick() {
        this.validCatalyst = (Disenchanter.getConfig().allowDisenchantingWithoutCatalyst && !this.handler.getSlot(2).hasStack()) || CatalystRegistry.get(this.handler.getSlot(2).getStack()) != Catalyst.DEFAULT;
    }

    public int getBaseX() {
        return this.x;
    }

    public int getBaseY() {
        return this.y;
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        this.disenchantButton = new ButtonWidget(x + 46, y + 74, 84, 20, Text.translatable("disenchanter.gui.button"), button1 -> {
            ClientPlayNetworking.send(new Identifier(Disenchanter.MOD_ID, "disenchant_request"), PacketByteBufs.empty());
        });

        this.disenchantButton.active = false;

        this.addDrawableChild(disenchantButton);
    }
}
