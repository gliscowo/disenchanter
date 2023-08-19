package com.glisco.disenchanter.compat.rei;

import com.glisco.disenchanter.Disenchanter;
import com.glisco.disenchanter.VisitableTextContent;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class CatalystCategory implements DisplayCategory<CatalystDisplay> {

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Disenchanter.DISENCHANTER_BLOCK);
    }

    @Override
    public Text getTitle() {
        return Text.translatable("disenchanter.rei.title");
    }

    @Override
    public CategoryIdentifier<? extends CatalystDisplay> getCategoryIdentifier() {
        return DisenchanterReiPlugin.CATALYST;
    }

    @Override
    public List<Widget> setupDisplay(CatalystDisplay display, Rectangle bounds) {
        var widgets = new ArrayList<Widget>();
        var origin = new Point(bounds.x, bounds.y);

        widgets.add(Widgets.createSlot(new Point(origin.x + 5, origin.y - 9 + bounds.height / 2)).markInput().entries(display.getInputEntries().get(0)));

        var catalystName = Registries.ITEM.getId(((ItemStack) display.getInputEntries().get(0).get(0).castValue()).getItem()).getPath();
        var wrapped = MinecraftClient.getInstance().textRenderer.getTextHandler()
                .wrapLines(
                        Text.translatable("disenchanter.catalyst." + catalystName),
                        120, Style.EMPTY
                )
                .stream()
                .map(VisitableTextContent::new)
                .map(MutableText::of)
                .toList();

        int textHeight = wrapped.size() * 10;
        for (int i = 0; i < wrapped.size(); i++) {
            widgets.add(Widgets.createLabel(
                    new Point(origin.x + 25 + (bounds.width - 25) / 2, origin.y - 1 + bounds.height / 2 - textHeight / 2 + i * 10),
                    wrapped.get(i)
            ).color(0x4F4F4F).noShadow());
        }

        return widgets;
    }
}
