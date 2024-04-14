package AI;

import model.Board;
import model.BoardHelper;
import model.Piece;

import java.awt.*;
import java.util.ArrayList;

public class Minimax {
    static int nodesExplored = 0;

    //returns max score move
    public static Point solve(Piece[][] board, int player,int depth,Evaluator e){
        Piece[][] newboard = setBoard(board);
        nodesExplored = 0;
        print(board);
        int bestScore = Integer.MIN_VALUE;
        Point bestMove = null;
        Point point = null;
        for(Piece move : BoardHelper.getPointMove(board,player)){
            //create new node
            Piece[][] newNode = BoardHelper.getNewBoardAfterMove(board,move.getRow(), move.getCol(),player);
            //recursive call
            int childScore = MMAB(newNode,player,depth-1,false,Integer.MIN_VALUE,Integer.MAX_VALUE,e);
            if(childScore > bestScore) {
                bestScore = childScore;
                bestMove = new Point(move.getRow(), move.getCol());
            }
        }
        System.out.println("Nodes Explored : " + nodesExplored);
        return bestMove;
    }

    //returns minimax value for a given node with A/B pruning
    private static int MMAB(Piece[][] node,int player,int depth,boolean max,int alpha,int beta,Evaluator e){
        nodesExplored++;
        //if terminal reached or depth limit reached evaluate
        if(depth == 0 || BoardHelper.isGameFinish(node)){
            return e.eval(node,player);//su dung ham danh gia
        }
        int oplayer = (player==1) ? 2 : 1;
        //if no moves available then forfeit turn
        if((max && !BoardHelper.hasAnyMoves(node,player)) || (!max && !BoardHelper.hasAnyMoves(node,oplayer))){
            //System.out.println("Forfeit State Reached !");
            return MMAB(node,player,depth-1,!max,alpha,beta,e);
        }
        int score;
        if(max){
            //maximizing
            score = Integer.MIN_VALUE;
            for(Piece move : BoardHelper.getPointMove(node,player)){ //my turn
                //create new node
                Piece[][] newNode = BoardHelper.getNewBoardAfterMove(node,move.getRow(), move.getCol(),player);
                //recursive call
                int childScore = MMAB(newNode,player,depth-1,false,alpha,beta,e);
                if(childScore > score) score = childScore;
                //update alpha
                if(score > alpha) alpha = score;
                if(beta <= alpha) break; //Beta Cutoff
            }
        }else{
            //minimizing
            score = Integer.MAX_VALUE;
            for(Piece move : BoardHelper.getPointMove(node,oplayer)){ //opponent turn
                //create new node
                Piece[][] newNode = BoardHelper.getNewBoardAfterMove(node,move.getRow(), move.getCol(),oplayer);
                //recursive call
                int childScore = MMAB(newNode,player,depth-1,true,alpha,beta,e);
                if(childScore < score) score = childScore;
                //update beta
                if(score < beta) beta = score;
                if(beta <= alpha) break; //Alpha Cutoff
            }
        }
        return score;
    }

    public static void print(Piece[][] board){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(" "+board[i][j].getValue()+" ");
            }
            System.out.println();
        }
    }

    public static Piece[][] setBoard(Piece[][] board){
        Piece[][] newB = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = new Piece(i,j,board[i][j].getValue());
                newB[i][j] = piece;
            }
        }
        return newB;
    }

}
