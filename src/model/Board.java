package model;

public class Board {
    protected Piece[][] board;
    public static final int ROWS = 8;
    public static final int COLS = 8;
    private int i,j;
    public boolean WhiteContinue= false;

    private boolean gameOn;

    private boolean gameContinue;

    public int turn;

    public Board(){
        board= new Piece[ROWS][COLS];
        gameOn= true;
        gameContinue = true;
        turn=1;
        startBoard();
    }
    public Board(Piece[][] piece){
        board= piece;
        gameOn= true;
        gameContinue = true;
    }

    private void startBoard(){
        for (i = 0; i < ROWS; i++) {
            for (j = 0; j < COLS; j++) {
                    Piece piece = new Piece(i, j, 0);
                    board[i][j] = piece;
            }
        }

        board[ROWS/2-1][COLS/2-1].setValue(2);
        board[ROWS/2][COLS/2].setValue(2);
        board[ROWS/2][COLS/2-1].setValue(1);
        board[ROWS/2-1][COLS/2].setValue(1);
    }

    public int getScore(int value){
        int score=0;
        for (i = 0; i < Board.ROWS; i++) {
            for (j = 0; j < Board.COLS; j++) {
                if(board[i][j].getValue()==value) score++;
            }
        }
        return score;
    }

    //tinh so quan trang trong moi lan lat sau do su dung minimax tinh maxWhite moi lan quan trang chay va minWhite moi lan quan dem=n chay
    //chay het ban co return ra cot va hang co gia tri score tang len thi duoc phep chay
    //su dung 1 map (list) de luu tru hang va cot co the chay
    //phat trien theo huong lua chon max

    public Piece[][] getBoard() {
        return board;
    }
    //neu con cho trong thi setDead
    public boolean setFinish(){
        for (int i = 0; i < Board.ROWS; i++) {
            for (int j = 0; j < Board.COLS; j++) {
                if(board[i][j].getValue() ==0) return true;
            }
        }
        return false;
    }

    public void reset(){
        board= new Piece[ROWS][COLS];
        gameOn= true;
        gameContinue = true;
        turn=1;
        WhiteContinue =false;
        startBoard();

    }
    public void resume(){
        gameOn= true;
        if (WhiteContinue) turn=1; else turn=2;
        gameContinue= true;
    }

    public void printBoard(){
        for (int k = 0; k < Board.COLS; k++) {
            for (int l = 0; l < Board.ROWS; l++) {
                System.out.print("" + board[k][l].getValue()+" ");
            }
            System.out.println();
        }
    }


    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    public boolean isGameOn() {
        return gameOn;
    }

    public void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }

    public boolean isGameContinue() {
        return gameContinue;
    }

    public void setGameContinue(boolean gameContinue) {
        this.gameContinue = gameContinue;
    }

    public Piece[][] saveBoard(){
        Piece[][] newB = new Piece[ROWS][COLS];
        try {
            for (int k = 0; k < ROWS; k++) {
                for (int l = 0; l < COLS; l++) {
                    Piece piece = new Piece(i, j, board[k][l].getValue());
                    newB[k][l] = piece;
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return newB;
    }
}
