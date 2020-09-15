package com.glisco.disenchanter.main;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class DisenchanterScreen extends HandledScreen<ScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("disenchanter", "textures/gui/container/disenchanter.png");

    public DisenchanterScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        client.getTextureManager().bindTexture(TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        if (this.handler.getSlot(0).hasStack() && this.handler.getSlot(1).hasStack() && !this.handler.getSlot(2).hasStack()) {
            if (mouseX - x > 46 && mouseX - x < 129 && mouseY - y > 50 && mouseY - y < 69) {
                drawTexture(matrices, x + 46, y + 50, 0, 186, 84, 20);
            } else {
                drawTexture(matrices, x + 46, y + 50, 0, 166, 84, 20);
            }
        }

        drawCenteredText(matrices, MinecraftClient.getInstance().textRenderer, new TranslatableText("disenchanter.gui.button"), x + 87, y + 56, 0xffffffff);

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (mouseX - x > 46 && mouseX - x < 129 && mouseY - y > 53 && mouseY - y < 72 && this.handler.getSlot(0).hasStack() && this.handler.getSlot(1).hasStack() && !this.handler.getSlot(2).hasStack()) {
            MinecraftClient.getInstance().player.playSound(SoundEvents.ITEM_TOTEM_USE, SoundCategory.MASTER, 0.75f, (float) (0.8 + Math.random() * 0.4));
            this.handler.onButtonClick(this.client.player, 0);
            this.client.interactionManager.clickButton(this.handler.syncId, 0);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}
