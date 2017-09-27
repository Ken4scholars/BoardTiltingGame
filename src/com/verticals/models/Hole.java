package com.verticals.models;

public class Hole extends Entity {
    private static int count = 0;

    public Hole() {
        this.id = ++count;
    }
}
