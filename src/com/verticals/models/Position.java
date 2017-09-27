package com.verticals.models;

public class Position implements Cloneable{
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Position)) return false;

        Position position = (Position) o;

//        if (x != position.x) return false;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return 42 * x + y + 17;
    }

    @Override
    public Position clone() {
        try {
            return (Position)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    public int moveX(int x) {
        this.x += x;
        return this.x;
    }

    public int moveY(int y) {
        this.y += y;
        return this.y;
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public Position moved(int x, int y) {
        return new Position(this.x + x, this.y + y);
    }
}
