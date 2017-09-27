package com.verticals.models;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

public class Cell {
    private ArrayList<Entity> entities = new ArrayList<>();
    private Position position;
    private Hole hole;
    private Ball ball;

    public Cell(Position position) {
        this.position = position;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public boolean addEntity(Entity entity) {
        if (entities.size() > 0) {
            for (Entity curEntity : entities) {
                if (entity.getClass().equals(curEntity.getClass())) {
                    return false;
                }
            }
        }
        entities.add(entity);
        return true;
    }

    public boolean addHole(@NotNull Hole hole) {
        if(this.hole == null){
            this.hole = hole;
            return true;
        }
        return false;
    }

    public boolean addBall(@NotNull Ball ball){
        if(this.hole != null && this.hole.getId() != ball.getId()) return false;
        this.ball = ball;
        return true;
    }

    public Entity getBall() {
        if (entities.size() > 0) {
            for (Entity curEntity : entities) {
                if (curEntity instanceof Ball) {
                    return curEntity;
                }
            }
        }
        return null;
    }

    public boolean hasBall() {
        return this.getBall() != null;
    }

    public Entity getHole() {
        if (entities.size() > 0) {
            for (Entity curEntity : entities) {
                if (curEntity instanceof Hole) {
                    return curEntity;
                }
            }
        }
        return null;
    }

    public boolean hasHole() {
        return this.getHole() != null;
    }

    public Entity getWall() {
        if (entities.size() > 0) {
            for (Entity curEntity : entities) {
                if (curEntity instanceof Wall) {
                    return curEntity;
                }
            }
        }
        return null;
    }

    public boolean hasWall() {
        return this.getWall() != null;
    }

    public Position getPosition() {
        return position;
    }
}
