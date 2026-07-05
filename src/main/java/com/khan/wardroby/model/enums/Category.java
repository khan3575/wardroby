package com.khan.wardroby.model.enums;

public enum Category {
    TOP("Top & Shirts"),
    BOTTOM("Bottoms & Pants"),
    OUTERWEAR("Outerwear & Jackets"),
    FOOTWEAR("Shoes"),
    ACCESSORY("Accessories");

    private final String label;
    Category(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
