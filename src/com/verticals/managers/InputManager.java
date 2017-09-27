package com.verticals.managers;

import com.verticals.models.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;



public class InputManager {
    private static int[] params;
    private static ArrayList<String> results = new ArrayList<>();

    public static ArrayList<String> getResults() {
        return results;
    }

    public static int[] getParams() {
        return params;
    }

    public static void load(String filename) throws IOException {
        Path path = FileSystems.getDefault().getPath(filename);
        String resultLine;
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String inputLine = reader.readLine();

            params = Arrays.stream(inputLine.split(" ")).mapToInt(Integer::parseInt).toArray();
            if (!isCorrectParams()) {
                throw new IOException("Illegal arguments..");
            }

            while ((resultLine = reader.readLine()) != null) {
                results.add(resultLine);
            }
        }
    }

    private static boolean isCorrectParams() {
        if (params.length != 3 + 4 * params[1] + 4 * params[2]) return false;           // too few parameters
        if (getNumHolesNBalls() > getBoardSize() * getBoardSize()) return false;        // too many balls

        for (int i = 1; i <= getNumHolesNBalls(); i++) {
            // incorrect positions of Balls or Holes
            if (!BoardManager.isValidPosition(getBall(i), getBoardSize())) return false;
            if (!BoardManager.isValidPosition(getHole(i), getBoardSize())) return false;
        }

        for (int i = 1; i <= getNumWalls(); i++) {
            // incorrect positions of Walls
            Position[] cells = getWall(i);
            if (cells[0].equals(cells[1])) return false;
            if (!BoardManager.isValidPosition(cells[0], getBoardSize())) return false;
            if (!BoardManager.isValidPosition(cells[1], getBoardSize())) return false;
            if (cells[0].getX() != cells[1].getX() && cells[0].getY() != cells[1].getY()) return false;
        }

        return true;
    }

    public static int getBoardSize() {
        return params[0];
    }

    public static int getNumHolesNBalls() {
        return params[1];
    }

    public static int getNumWalls() {
        return params[2];
    }

    public static Position getBall(int i) {
        int offset = 3 + (i - 1) * 2;
        return new Position(params[offset], params[offset + 1]);
    }

    public static Position getHole(int i) {
        int offset = 3 + 2 * getNumHolesNBalls() + (i - 1) * 2;
        return new Position(params[offset], params[offset + 1]);
    }

    public static Position[] getWall(int i) {
        int offset = 3 + 4 * getNumHolesNBalls() + (i - 1) * 4;
        Position[] positions = new Position[2];
        positions[0] = new Position(params[offset], params[offset + 1]);
        positions[1] = new Position(params[offset + 2], params[offset + 3]);
        return positions;
    }

}
