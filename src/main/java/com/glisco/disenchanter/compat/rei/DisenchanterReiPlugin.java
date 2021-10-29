package com.glisco.disenchanter.compat.rei;

import com.glisco.disenchanter.Disenchanter;
import com.glisco.disenchanter.catalyst.CatalystRegistry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

public class DisenchanterReiPlugin implements REIClientPlugin {

    public static final CategoryIdentifier<CatalystDisplay> CATALYST = CategoryIdentifier.of(new Identifier(Disenchanter.MOD_ID, "catalyst"));

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        CatalystRegistry.getCatalysts().forEach(item -> registry.add(new CatalystDisplay(Ingredient.ofItems(item))));
    }

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new CatalystCategory());
    }
}
