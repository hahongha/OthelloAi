package model;

import model.Board;
import model.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class BoardHelper {
    static int i,j=0;

    //lay ra cac vi tri co the danh
    public static ArrayList<Piece> getPointMove(Piece[][] afterboard, int value){
        Piece[][] board = afterboard;
        ArrayList<Piece> dudoan = new ArrayList<>();
        for (int i = 0; i < Board.ROWS*Board.COLS; i++) {
            int row = i / Board.ROWS;
            int col = i % Board.COLS;
            if (canPlay(row,col,value,board)) {
                dudoan.add(new Piece(row, col, value));
            }
        }
        return dudoan;
    }

    //kiem tra xem vi tri do co the danh hay khong
    public static boolean canPlay(int row, int col, int value, Piece[][] board) {
        if(board[row][col].getValue() !=0) return false;
        //top
        for (i = row - 1; i >= 0; i--) {
            if (board[i][col].getValue() == 0) {//neu gap phai vi tri chua dat co thi sai-> khong dat dc co
                break;
            }
            if (board[i][col].getValue() == value) {//neu
                if (Math.abs(i-row) > 1) return true;
                break;
            }
        }
        //bottom
        for (i = row+1; i <Board.ROWS ; i++) {
            if (board[i][col].getValue() == 0) {
                break;
            }
            if (board[i][col].getValue() == value) {
                if (Math.abs(i-row) > 1) return true;
                break;
            }
        }
        //left
        for (i = col-1; i >=0 ; i--) {
            if(board[row][i].getValue()==0){
                break;
            }
            if(board[row][i].getValue()==value){
                if (Math.abs(i-col) > 1) return true;
                break;
            }
        }
        //right
        for (i = col+1; i < Board.COLS ; i++) {
            if(board[row][i].getValue()==0){
                break;
            }
            if(board[row][i].getValue()==value){
                if (Math.abs(i-col) > 1) return true;
                break;
            }
        }
        //bottomright
        for (i = row+1,j = col + 1; i <Board.ROWS && j < Board.COLS ; i++,j++) {
            if (board[i][j].getValue() == 0) {
                break;
            }
            if (board[i][j].getValue() == value) {
                if(Math.abs(i-row)>1&& Math.abs(i-col)>1) return true;
                break;
            }
        }
        //bottomleft
        for (i = row+1, j= col-1; i < Board.ROWS && j>=0 ; i++,j--) {
            if (board[i][j].getValue() == 0) {
                break;
            }
            if (board[i][j].getValue() == value) {
                if(Math.abs(i-row)>1&& Math.abs(i-col)>1) return true;

                break;
            }
        }
        //topright
        for (i = row-1, j= col+1; i >=0&&j < Board.COLS ; i--,j++) {
            if(board[i][j].getValue()==0){
                break;
            }
            if(board[i][j].getValue()==value){
                if(Math.abs(i-row)>1&& Math.abs(i-col)>1) return true;
                break;
            }
        }
        //topleft
        for (i = row-1, j = col-1; i >=0 && j>=0 ; i--, j--){
            if (board[i][j].getValue() == 0) {
                break;
            }
            if (board[i][j].getValue() == value) {
                if(Math.abs(i-row)>1&& Math.abs(i-col)>1) return true;
                break;
            }
        }
        return false;
    }

    //kiem tra xem co con vi tri nao de danh khong-> tro choi ket thuc
    public static boolean isGameFinished(Piece[][] board, int value){
        ArrayList<Piece> pieces = new ArrayList<>();
        pieces= getPointMove(board,value);
        if(pieces.isEmpty()) return false;
        return true;
    }

    //dem so luong quan co tren ban co
    public static int getTotalStoneCount(Piece[][] board){
        int stone=0;
        for (int k = 0; k < Board.ROWS; k++) {
            for (int l = 0; l < Board.COLS; l++) {
                if(board[k][l].getValue()!=0) stone++;
            }
        }
        return stone;
    }
    //dem so luong moi loai
    public static int getPlayerStoneCount(Piece[][] board, int player){
        int score=0;
        for (int k = 0; k < Board.ROWS; k++) {
            for (int l = 0; l < Board.COLS; l++) {
                if(board[k][l].getValue()==player) score++;
            }
        }
        return score;
    }

    //kiem tra xem co con vi tri nao co the di chuyen nua khong
    public static boolean hasAnyMoves(Piece[][] board, int value){
        return getPointMove(board,value).size() > 0;
    }
    //neu khong con vi tri nao di chuyen
    public static boolean isGameFinish(Piece[][] board){

        return !(hasAnyMoves(board,1) || hasAnyMoves(board,2));
    }

    public static ArrayList<Point> getReversePoints(Piece[][] board,int player,int i,int j){

        ArrayList<Point> allReversePoints = new ArrayList<>();

        int mi , mj , c;
        int oplayer = ((player == 1) ? 2 : 1);

        //move up
        ArrayList<Point> mupts = new ArrayList<>();
        mi = i - 1;
        mj = j;
        while(mi>0 && board[mi][mj].getValue() == oplayer){
            mupts.add(new Point(mi,mj));
            mi--;
        }
        if(mi>=0 && board[mi][mj].getValue() == player && mupts.size()>0){
            allReversePoints.addAll(mupts);
        }


        //move down
        ArrayList<Point> mdpts = new ArrayList<>();
        mi = i + 1;
        mj = j;
        while(mi<7 && board[mi][mj].getValue() == oplayer){
            mdpts.add(new Point(mi,mj));
            mi++;
        }
        if(mi<=7 && board[mi][mj].getValue() == player && mdpts.size()>0){
            allReversePoints.addAll(mdpts);
        }

        //move left
        ArrayList<Point> mlpts = new ArrayList<>();
        mi = i;
        mj = j - 1;
        while(mj>0 && board[mi][mj].getValue() == oplayer){
            mlpts.add(new Point(mi,mj));
            mj--;
        }
        if(mj>=0 && board[mi][mj].getValue() == player && mlpts.size()>0){
            allReversePoints.addAll(mlpts);
        }

        //move right
        ArrayList<Point> mrpts = new ArrayList<>();
        mi = i;
        mj = j + 1;
        while(mj<7 && board[mi][mj].getValue() == oplayer){
            mrpts.add(new Point(mi,mj));
            mj++;
        }
        if(mj<=7 && board[mi][mj].getValue() == player && mrpts.size()>0){
            allReversePoints.addAll(mrpts);
        }

        //move up left
        ArrayList<Point> mulpts = new ArrayList<>();
        mi = i - 1;
        mj = j - 1;
        while(mi>0 && mj>0 && board[mi][mj].getValue() == oplayer){
            mulpts.add(new Point(mi,mj));
            mi--;
            mj--;
        }
        if(mi>=0 && mj>=0 && board[mi][mj].getValue() == player && mulpts.size()>0){
            allReversePoints.addAll(mulpts);
        }

        //move up right
        ArrayList<Point> murpts = new ArrayList<>();
        mi = i - 1;
        mj = j + 1;
        while(mi>0 && mj<7 && board[mi][mj].getValue() == oplayer){
            murpts.add(new Point(mi,mj));
            mi--;
            mj++;
        }
        if(mi>=0 && mj<=7 && board[mi][mj].getValue() == player && murpts.size()>0){
            allReversePoints.addAll(murpts);
        }

        //move down left
        ArrayList<Point> mdlpts = new ArrayList<>();
        mi = i + 1;
        mj = j - 1;
        while(mi<7 && mj>0 && board[mi][mj].getValue() == oplayer){
            mdlpts.add(new Point(mi,mj));
            mi++;
            mj--;
        }
        if(mi<=7 && mj>=0 && board[mi][mj].getValue() == player && mdlpts.size()>0){
            allReversePoints.addAll(mdlpts);
        }

        //move down right
        ArrayList<Point> mdrpts = new ArrayList<>();
        mi = i + 1;
        mj = j + 1;
        while(mi<7 && mj<7 && board[mi][mj].getValue() == oplayer){
            mdrpts.add(new Point(mi,mj));
            mi++;
            mj++;
        }
        if(mi<=7 && mj<=7 && board[mi][mj].getValue() == player && mdrpts.size()>0){
            allReversePoints.addAll(mdrpts);
        }

        if (allReversePoints.size()>0) allReversePoints.add(new Point(i,j));

        return allReversePoints;
    }

    public static Piece[][] getNewBoardAfterMove(Piece[][] board,int row, int col, int player){
        //get clone of old board
        Piece[][] newboard = new Piece[Board.ROWS][Board.COLS];
        for (int k = 0; k < 8; k++) {
            for (int l = 0; l < 8; l++) {
                newboard[k][l] = board[k][l];
            }
        }

        //place piece
        newboard[row][col].setValue(player);
        //reverse pieces
        ArrayList<Point> rev = BoardHelper.getReversePoints(newboard,player,row,col);
        for(Point pt : rev){
            newboard[pt.x][pt.y].setValue(player);
        }
        return newboard;
    }

}
