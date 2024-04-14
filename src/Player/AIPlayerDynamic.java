package Player;

import AI.DynamicEvaluator;
import AI.Evaluator;
import AI.Minimax;
import model.GamePlayer;
import model.Piece;

import java.awt.*;

public class AIPlayerDynamic extends GamePlayer {

    private int searchDepth;
    private Evaluator evaluator;

    public AIPlayerDynamic(int mark, int depth) {
        super(mark);
        searchDepth = depth;
        evaluator = new DynamicEvaluator();
    }

    @Override
    public boolean isUserPlayer() {
        return false;
    }

    @Override
    public String playerName() {
        return "Dynamic AI (Depth " + searchDepth + ")";
    }

    @Override
    public Point play(Piece[][] board) {
        return Minimax.solve(board,myMark,searchDepth,evaluator);
    }
}
