package com.verticals.models;

import com.sun.istack.internal.NotNull;
import com.verticals.managers.BoardManager;

import java.util.*;


public class Board {
    private int size;
    private boolean won;
    private String path;
    private ArrayList<ArrayList<Cell>> cells;
    private HashMap<Ball, Position> balls;
    private HashMap<Hole, Position> holes;
    private HashMap<Wall, Position> walls;

    public Board(int size) {
        won = true;
        path = "";
        cells = new ArrayList<>();
        balls = new HashMap<>();
        holes = new HashMap<>();
        walls = new HashMap<>();
        this.size = size;
        for (int i = 1; i <= size; i++) {
            ArrayList<Cell> column = new ArrayList<>();
            for (int j = 1; j <= size; j++) {
                Cell cell = new Cell(new Position(i, j));
                column.add(cell);
            }
            cells.add(column);
        }
    }

    public Board(int size, HashMap<Ball, Position> balls, HashMap<Hole, Position> holes, HashMap<Wall, Position> walls) {
        this(size);
        addEntities(balls);
        addEntities(holes);
        addEntities(walls);
//        for (Ball ball : balls.keySet()) {
//            addEntity(balls.get(ball), ball);
//        }
//        for (Hole hole : holes.keySet()) {
//            addEntity(holes.get(hole), hole);
//        }
//        for (Wall wall : walls.keySet()) {
//            addEntity(walls.get(wall), wall);
//        }
    }

    public boolean addEntities(HashMap<? extends Entity, Position> entities){
        for(Entity entity : entities.keySet()){
            if (!addEntity(entities.get(entity), entity))return false;
        }
        return true;
    }

    public boolean addEntity(Position position, Entity entity) {
        Cell cell = this.getCell(position);
        if (!cell.addEntity(entity)) return false;
        if (entity instanceof Ball) {
            balls.put((Ball) entity, position);
            if (!((Ball) entity).isLockedIn()) won = false;
        }
        if (entity instanceof Hole) holes.put((Hole) entity, position);
        if (entity instanceof Wall) walls.put((Wall) entity, position);
        return true;
    }

    public Cell getCell(@NotNull Position position) {
        return cells.get(position.getX() - 1).get(position.getY() - 1);
    }

    public int getSize() {
        return size;
    }

    public Board tilt(Direction direction) {
        HashMap<Ball, Position> newBalls = new HashMap<>();
        for (Map.Entry<Ball, Position> entry : sortBalls(new ArrayList<>(balls.entrySet()), direction)) {

            Ball ball = entry.getKey().clone();
            Position newPosition = moveBall(ball, direction);
            if(newPosition == null) return null;
            newBalls.put(ball, newPosition);
        }
        Board newBoard = new Board(getSize(), newBalls, new HashMap<>(holes), new HashMap<>(walls));
        newBoard.setPath(getPath() + direction);
        return newBoard;
    }

    public List<Map.Entry<Ball, Position>> sortBalls(List<Map.Entry<Ball, Position>> balls, Direction direction){
        balls.sort((Map.Entry<Ball, Position> obj1, Map.Entry<Ball, Position> obj2)-> {
                switch (direction){
                    case N: return obj1.getValue().getY() - obj2.getValue().getY();
                    case S: return obj2.getValue().getY() - obj1.getValue().getY();
                    case E: return obj1.getValue().getX() - obj2.getValue().getX();
                    case W: return obj2.getValue().getX() - obj1.getValue().getX();
                    default: return 0;
                }
            });
        Collections.reverse(balls);
        return balls;
    }

    private Position moveBall(Ball ball, Direction direction) {
        Position before = getBallPosition(ball);
        if (ball.isLockedIn()) return before;
        Position current = before.clone();
        int offsetX = 0, offsetY = 0;
        switch (direction) {
            case N: {
                offsetY = -1;
                break;
            }
            case S: {
                offsetY = 1;
                break;
            }
            case E: {
                offsetX = 1;
                break;
            }
            case W: {
                offsetX = -1;
                break;
            }

        }

        while (isLegalPosition(ball, current.moved(offsetX, offsetY))) {
            if (getCell(current).hasWall() && getCell(current.moved(offsetX, offsetY)).hasWall() &&
                    getCell(current).getWall().id == getCell(current.moved(offsetX, offsetY)).getWall().id) {
                // collision with wall
                break;
            }
            current.move(offsetX, offsetY);
            Cell cell = getCell(current);
            Hole hole = (Hole) cell.getHole();
            if (hole != null && hole.getId() != ball.getId()) {
                // in incorrect hole
                current = null;
                break;
            } else if (hole != null && hole.getId() == ball.getId()) {
                // in correct hole
                ball.setLockedIn(true);
                break;
            }
        }
        return current;
    }

    private boolean isLegalPosition(Ball ball, Position position) {
        if (!BoardManager.isValidPosition(position, size)) return false;
        if (ball.isLockedIn() && !getBallPosition(ball).equals(position)) return false;
        return !getCell(position).hasBall() || ((Ball) getCell(position).getBall()).isLockedIn();
    }

    private Position getBallPosition(Ball ball) {
        return balls.get(ball);
    }

    private Position getHolePosition(Hole hole) {
        return holes.get(hole);
    }

    private Position getWallPosition(Wall wall) {
        return walls.get(wall);
    }

    public boolean gameOver() {
        return won;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
