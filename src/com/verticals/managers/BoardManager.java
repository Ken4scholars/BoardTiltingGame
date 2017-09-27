package com.verticals.managers;

import com.verticals.models.*;

public class BoardManager {

    public static Board CreateBoard() {
        Board board = new Board(InputManager.getBoardSize());

        for (int i = 1; i <= InputManager.getNumHolesNBalls(); i++) {
            board.addEntity(InputManager.getBall(i), new Ball());
            board.addEntity(InputManager.getHole(i), new Hole());
        }

        for (int i = 1; i <= InputManager.getNumWalls(); i++) {
            Position[] positions = InputManager.getWall(i);

            Wall.Orientation orientation = positions[0].getX() == positions[1].getX() ? Wall.Orientation.horizontal :
                    Wall.Orientation.vertical;
            Wall wall1 = new Wall(orientation);
            Wall wall2 = new Wall(orientation);
            wall2.setId(wall1.getId());
            board.addEntity(positions[0], wall1);
            board.addEntity(positions[1], wall2);
        }
        return board;
    }

    public static void showBoard(Board board) {
        for (int y = -1; y <= board.getSize() + 1; y++) {
            StringBuilder row = new StringBuilder();

            Cell cell;
            for (int x = 0; x <= board.getSize() + 1; x++) {
                if (y < 1) {
                    // draw Header
                    if (y == -1 && x != 0) row.append(String.format("\t %d \t", x));
                    if (y == 0 && x == 0) row.append("\t");
                    if (y == 0 && x != 0) row.append("-------");

                } else if (y == board.getSize() + 1){
                    if(x == 0) row.append("\t");
                    else row.append("-------");
                }else {
                    // draw Board
                    if (x == 0) {
                        row.append(String.format("%d |", y));
                        continue;
                    }
                    else if (x == board.getSize() + 1) {
                        row.append("\t|");
                        continue;
                    }
                    cell = board.getCell(new Position(x,y));
                    String item = "";
                    if (cell.hasBall()) {
                        item += String.format("B%d", cell.getBall().getId());
                    }
                    if (cell.hasHole()) {
                        item += String.format("H%d", cell.getHole().getId());
                    }
                    if (cell.hasWall()) {
                        item += String.format("W%d", cell.getWall().getId());
                    }

                    row.append(String.format("\t%s\t", item));
                }
            }
            System.out.println(row);
            System.out.println();
        }
        if (board.gameOver()) System.out.println("Win!");
        System.out.printf("\t Path: %s\n", board.getPath());
    }

    public static boolean isValidPosition(Position position, int size) {
        return (position.getX() >= 1 && position.getX() <= size && position.getY() >= 1 && position.getY() <= size);
    }

    public static Board tiltBoard(Board board, Direction direction) {
        return board.tilt(direction);
    }
}
