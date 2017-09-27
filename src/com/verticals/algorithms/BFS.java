package com.verticals.algorithms;

import com.verticals.managers.BoardManager;
import com.verticals.models.Board;
import com.verticals.models.Direction;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by kenneth on 26.09.17.
 */
public class BFS {

    public List<Board> run(Board board){
        Queue<Board> boards = new ArrayDeque<>();
        List<Board> solutions = new ArrayList<>();
        Board newBoard;
        boards.add(board);
        while (!boards.isEmpty()) {
            board = boards.remove();
            int length = board.getPath().length();
            Direction parent = board.getPath().length() > 0 ? Direction.valueOf(board.getPath().substring(length-1, length)) : null;
            for (Direction direction : Direction.values()) {
                if (!direction.equals(parent)) {
                    newBoard = BoardManager.tiltBoard(board, direction);
                    if(newBoard == null)continue;
                    if(newBoard.gameOver()) {
                        if(solutions.isEmpty() || newBoard.getPath().length() <= solutions.get(0).getPath().length()){
                            solutions.add(newBoard);
                        }
                        continue;
                    }
                    if (solutions.isEmpty() || solutions.get(0).getPath().length() > newBoard.getPath().length()){
                        boards.add(newBoard);
                    }
                }
            }
        }
        return solutions;
    }
}
