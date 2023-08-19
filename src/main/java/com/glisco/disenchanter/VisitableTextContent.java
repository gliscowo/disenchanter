package com.glisco.disenchanter;

import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.TextContent;

import java.util.Optional;

public record VisitableTextContent(StringVisitable content) implements TextContent {
    @Override
    public <T> Optional<T> visit(StringVisitable.StyledVisitor<T> visitor, Style style) {
        return content.visit(visitor, style);
    }

    @Override
    public <T> Optional<T> visit(StringVisitable.Visitor<T> visitor) {
        return content.visit(visitor);
    }
}
