package Player;

import model.Board;
import model.BoardHelper;
import model.GamePlayer;
import model.Piece;

import java.awt.*;
import java.util.ArrayList;

public class GreedyPlayer extends GamePlayer {

    public GreedyPlayer(int player) {
        super(player);
    }

    @Override
    public boolean isUserPlayer() {
        return false;
    }

    @Override
    public String playerName() {
        return "Greedy Player";
    }

    @Override
    public Point play(Piece[][] board) {

        ArrayList<Piece> myPossibleMoves = BoardHelper.getPointMove(board,myMark);

        Point bestMove = null;
        int bestValue = 0;

        for(Piece move : myPossibleMoves) {
            int val = BoardHelper.getReversePoints(board, myMark, move.getRow(),move.getCol()).size();
            if(val > bestValue){
                bestValue = val;
                bestMove = new Point(move.getRow(), move.getCol());
            }
        }

        return bestMove;

    }

}