package com.glisco.disenchanter.compat.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.recipe.Ingredient;

import java.util.Collections;
import java.util.List;

public class CatalystDisplay implements Display {

    private final List<EntryIngredient> catalysts;

    public CatalystDisplay(Ingredient catalystItem) {
        this.catalysts = Collections.singletonList(EntryIngredients.ofIngredient(catalystItem));
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return catalysts;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return catalysts;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return DisenchanterReiPlugin.CATALYST;
    }
}
