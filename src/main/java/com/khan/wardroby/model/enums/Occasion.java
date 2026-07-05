package com.khan.wardroby.model.enums;

public enum Occasion {
    CASUAL("Casual"),
    OFFICE("Office"),
    FORMAL("Formal"),
    PARTY("Party"),
    WORKOUT("Work out"),
    LOUNGE("Lounge");

    private final String label;
    Occasion(String label)
    {
        this.label = label;
    }
    public String getLabel()
    {return label;}
}
