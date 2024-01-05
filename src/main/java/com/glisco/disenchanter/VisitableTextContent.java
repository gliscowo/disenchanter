package com.glisco.disenchanter;

import com.mojang.serialization.MapCodec;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.TextContent;

import java.util.Optional;

public record VisitableTextContent(StringVisitable content) implements TextContent {

    private static final Type<VisitableTextContent> DUMMY_TYPE = new Type<>(MapCodec.unit(new VisitableTextContent(StringVisitable.EMPTY)), "disenchanter:visitable_text");

    @Override
    public <T> Optional<T> visit(StringVisitable.StyledVisitor<T> visitor, Style style) {
        return content.visit(visitor, style);
    }

    @Override
    public <T> Optional<T> visit(StringVisitable.Visitor<T> visitor) {
        return content.visit(visitor);
    }

    @Override
    public Type<?> getType() {
        return DUMMY_TYPE;
    }
}
