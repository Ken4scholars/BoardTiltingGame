package com.verticals.models;

public class Ball extends Entity implements Cloneable{
    private static int count = 0;

    private boolean lockedIn;

    public boolean isLockedIn() {
        return lockedIn;
    }

    public void setLockedIn(boolean lockedIn) {
        this.lockedIn = lockedIn;
    }

    public Ball() {
        this.id = ++count;
    }

    @Override
    public Ball clone() {
        try {
            return (Ball)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof  Ball)) return false;

        Ball ball = (Ball) o;

        return id == ball.id;
    }

    @Override
    public int hashCode() {
        return 42* this.id + 17;
    }
}
