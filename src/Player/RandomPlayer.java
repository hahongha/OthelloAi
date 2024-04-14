package Player;

import model.BoardHelper;
import model.GamePlayer;
import model.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends GamePlayer {

    Random rnd = new Random();

    public RandomPlayer(int player) {
        super(player);
    }

    @Override
    public boolean isUserPlayer() {
        return false;
    }

    @Override
    public String playerName() {
        return "Random Player";
    }

    @Override
    public Point play(Piece[][] board) {
        ArrayList<Piece> myPossibleMoves = BoardHelper.getPointMove(board,myMark);

        if(myPossibleMoves.size() > 0){
            Piece piece = myPossibleMoves.get(rnd.nextInt(myPossibleMoves.size()));
            return new Point(piece.getRow(), piece.getCol());
        }else{
            return null;
        }

    }

}
