package com.verticals;

import com.verticals.algorithms.BFS;
import com.verticals.models.Board;
import com.verticals.models.Direction;
import com.verticals.managers.InputManager;
import com.verticals.managers.BoardManager;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) return;

        InputManager.load(args[0]);
        Board board = BoardManager.CreateBoard();
        BFS bfs = new BFS();
        List<Board> solutions = bfs.run(board);
        for (Board solution : solutions) {
            BoardManager.showBoard(solution);
            System.out.println();
        }
    }
}
