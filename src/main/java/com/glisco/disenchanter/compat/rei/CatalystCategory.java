package com.glisco.disenchanter.compat.rei;

import com.glisco.disenchanter.Disenchanter;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Language;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

public class CatalystCategory implements DisplayCategory<CatalystDisplay> {

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Disenchanter.DISENCHANTER_BLOCK);
    }

    @Override
    public Text getTitle() {
        return new TranslatableText("disenchanter.rei.title");
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

        var catalystName = Registry.ITEM.getId(((ItemStack) display.getInputEntries().get(0).get(0).castValue()).getItem()).getPath();
        String description = Language.getInstance().get("disenchanter.catalyst." + catalystName);

        var wrapped = WordUtils.wrap(description, 20, "\n", false).split("\n");
        int textHeight = wrapped.length * 10;

        for (int i = 0; i < wrapped.length; i++) {
            widgets.add(Widgets.createLabel(new Point(origin.x + 25 + (bounds.width - 25) / 2, origin.y - 1 + bounds.height / 2 - textHeight / 2 + i * 10),
                    new LiteralText(wrapped[i])).color(0x4F4F4F).noShadow());
        }

        return widgets;
    }
}
