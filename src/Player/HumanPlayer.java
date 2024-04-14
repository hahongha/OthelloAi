package Player;

import model.GamePlayer;
import model.Piece;

import java.awt.*;

public class HumanPlayer extends GamePlayer {

    public HumanPlayer(int player) {
        super(player);
    }

    @Override
    public boolean isUserPlayer() {
        return true;
    }

    @Override
    public String playerName() {
        return "User" ;
    }

    @Override
    public Point play(Piece[][] board) {
        return null;
    }

}
