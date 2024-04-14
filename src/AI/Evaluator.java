package AI;

import model.Board;
import model.Piece;

public interface Evaluator {

    int eval(Piece[][] board, int player);

}
