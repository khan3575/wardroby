package com.khan.wardroby.model.enums;

public enum Season {
    SPRING("Spring"),
    SUMMER("Summer"),
    FALL("Fall"),
    WINTER("Winter"),
    ALL_SEASON("All Season");

    private final String label;
    Season(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
