package com.verticals.models;

public class Wall extends Entity {
    private static int count = 0;

    public enum Orientation {
        horizontal,
        vertical
    }

    public Orientation orientation;

    public Wall(Orientation orientation) {
        this.id = ++count;
        this.orientation = orientation;
    }
}
