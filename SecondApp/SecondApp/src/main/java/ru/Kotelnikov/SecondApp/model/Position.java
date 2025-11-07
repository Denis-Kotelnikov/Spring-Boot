package ru.Kotelnikov.SecondApp.model;

import lombok.Getter;

@Getter
public enum Position {
    DEV(2.2, false),
    HR(2.1, false),
    TLC(2.4, false),
    MANAGER(3.0, true),
    DIRECTOR(3.5, true),
    EXECUTIVE(4.0, true);

    private final double positionCoefficient;
    private final boolean isManager;

    Position(double positionCoefficient, boolean isManager) {
        this.positionCoefficient = positionCoefficient;
        this.isManager = isManager;
    }
}
