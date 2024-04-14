package view;

//import AI.Minimax;
import Player.AIPlayerDynamic;
import Player.HumanPlayer;
import control.Mouse;
import control.TimeControl;
import main.Main;
import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
        import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainPanel extends JPanel implements Runnable{

    public static String saveDataPath = System.getProperty("user.dir") +File.separator
            + "lib" + File.separator+"Pic"+File.separator;
    public static final int SQUARE_SIZE= 80;
    public static final int HALF_SQUARE_SIZE = SQUARE_SIZE/2;
    public static final int SPACING = SQUARE_SIZE/10;// khoảng cách giữa các ô
    public static final int ARC = 10;
    public static int BOARD_WIDTH = (Board.COLS + 1) * SPACING + Board.COLS * SQUARE_SIZE;
    public static int BOARD_HEIGHT = (Board.ROWS + 1) * SPACING + Board.ROWS * SQUARE_SIZE;
    public static Font fontString = new Font("Monospaced", Font.BOLD, 40);
    public static Font fontScore = new Font("Monospaced", Font.BOLD, 80);

    private BufferedImage clock= null;
    private BufferedImage imgMusicOn = null;
    private BufferedImage imgMusicOff = null;
    public boolean isMusic= true;

    public Board board;

    Mouse mouse;
    Thread game;

    int FPS=60;
    public TimeControl timeWhite;
    public TimeControl timeBlack;

    public ArrayList<Piece> dudoan;
    Main main;

    public Piece newPiece = new Piece();


    GamePlayer player1 = new HumanPlayer(1);
    GamePlayer player2 = new HumanPlayer(2);

    Sound sound;


    public MainPanel(Main jf){
        main= jf;
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(new Color(160,160,160));
        board = new Board();
        dudoan = new ArrayList<>();
        dudoan = BoardHelper.getPointMove(board.getBoard(), 1);
        mouse= new Mouse(board, this, main);
        timeWhite = new TimeControl();
        timeBlack = new TimeControl();
        addMouseMotionListener(mouse);
        addMouseListener(mouse);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderplay(g);
        if (!board.isGameContinue()) renderContinue(g);
        if(!board.isGameOn()) renderEnd(g);
       g.dispose();
    }

    public void renderplay(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,MainPanel.BOARD_WIDTH, MainPanel.BOARD_HEIGHT);

        //ve bang
        for (int j = 0; j < Board.COLS; j++) {
            for (int i = 0; i < Board.ROWS; i++) {
                g.setColor(new Color(0x3C6255));
                g.drawRoundRect(getTileX(j), getTileY(i), SQUARE_SIZE, SQUARE_SIZE, ARC, ARC);
                g.fillRoundRect(getTileX(j), getTileY(i), SQUARE_SIZE, SQUARE_SIZE, ARC, ARC);
            }
        }
        //ve quan co vua dat xuong

        if(newPiece.getValue()!=8) {
            g.setColor(Color.red);
            g.fillRoundRect(getTileX(newPiece.getCol()), getTileY(newPiece.getRow()), SQUARE_SIZE, SQUARE_SIZE, ARC, ARC);
        }


        for (int j = 0; j < Board.COLS; j++) {
            for (int i = 0; i < Board.ROWS; i++) {
                Piece current = board.getBoard()[i][j];
                g.setColor(current.setBackGround());
                g.drawOval(getTileX(j)+2, getTileY(i)+2, SQUARE_SIZE-5, SQUARE_SIZE-5);
                g.fillOval(getTileX(j)+2, getTileY(i)+2, SQUARE_SIZE-5, SQUARE_SIZE-5);
            }
        }
        //ve du doan
        for (Piece piece: dudoan){
            g.setColor(Color.BLUE);
            g.fillRoundRect(getTileX(piece.getCol()), getTileY(piece.getRow()), SQUARE_SIZE, SQUARE_SIZE, ARC, ARC);
        }


        //ve thoi gian
        try {
            clock = ImageIO.read(new File(startScreen.saveDataPath + "/alarm.png"));
        }catch (Exception e){
            e.printStackTrace();
        }

        //thoi gian cua mau trang
        g.drawImage(clock,MainPanel.BOARD_WIDTH+30, 20, 50,50,null);

        g.setColor(Color.BLACK);
        g.setFont(fontString);
        g.drawString(timeWhite.getFormatedTime(), MainPanel.BOARD_WIDTH+90, 60);


        //thoi gian cua mau den
        g.drawImage(clock,Main.WIDTH-100, Main.HEIGHT-120, 50,50,null);
        g.setColor(Color.BLACK);
        g.setFont(fontString);
        g.drawString(timeBlack.getFormatedTime(), Main.WIDTH-230, Main.HEIGHT-80);


        //ve bang diem
        g.setColor(new Color(0x3C6255));
        g.fillRoundRect(MainPanel.BOARD_WIDTH+50, 120, 280,250,MainPanel.ARC, MainPanel.ARC);
            //ve 2 o diem
        g.setColor(Color.BLACK);
        g.fillRoundRect(MainPanel.BOARD_WIDTH+70, 140, 100,100,MainPanel.ARC, MainPanel.ARC);
        g.fillRoundRect(MainPanel.BOARD_WIDTH+210, 140, 100,100,MainPanel.ARC, MainPanel.ARC);
            //ve 2 quan co
        g.fillOval(MainPanel.BOARD_WIDTH+80, 260, 80,80);
        g.setColor(Color.white);
        g.fillOval(MainPanel.BOARD_WIDTH+220, 260, 80,80);

        //ve nut Pause
        g.setColor(new Color(0x3C6255));
        g.fillRect(MainPanel.BOARD_WIDTH+80, 400, 220,70);
        g.setFont(fontString);
        g.setColor(Color.white);
        g.drawString("Pause", MainPanel.BOARD_WIDTH+120, 450);

        //ve nut Exit
        g.setColor(Color.red);
        g.fillRect(MainPanel.BOARD_WIDTH+80, 500, 220,70);
        g.setFont(fontString);
        g.setColor(Color.white);
        g.drawString("Exit", MainPanel.BOARD_WIDTH+120, 550);

        // ve diem
        g.setFont(fontScore);
        g.setColor(Color.WHITE);
        g.drawString(""+board.getScore(1),MainPanel.BOARD_WIDTH+80, 220);
        g.drawString(""+board.getScore(2),MainPanel.BOARD_WIDTH+220, 220);




    };

    public void renderEnd(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.BLACK);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); // Adjust transparency here (0.0f - 1.0f)
        g2d.fillRect(0,0,Main.WIDTH, Main.HEIGHT);

        g.setColor(new Color(0x3C6255));
        g.fillRoundRect(350,100,400,550,100,100);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 50));
        String s="";
        int score=0;
        if(board.getScore(1) > board.getScore(2)){
            s= "BLACK";
            score= board.getScore(1);
        }else{
            s= "WHITE";
            score= board.getScore(2);
        }
        s+= " WIN";
        g.drawString(s,420,200);
        g.setFont(fontString);
        g.drawString("SCORE", 490,275);

        g.setColor(Color.BLACK);
        g.fillRoundRect(500, 300, 100,100,20,20);

        g.setFont(new Font("Monospaced", Font.BOLD, 45));
        g.setColor(Color.WHITE);
        if (score < 10){
            g.drawString(  String.valueOf(score) , 535,365);}
        else g.drawString(  String.valueOf(score) , 520,365);

        g.setFont(new Font("Monospaced", Font.BOLD, 50));

        g.setColor(new  Color(0xFDF9BC));
        g.fillRoundRect(450,450,200,50,20,20);
        g.setColor(Color.BLACK);

        g.drawString("Exit", 485,485);


        g2d.dispose();

    };

    public void renderContinue(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(178,255,0));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); // Adjust transparency here (0.0f - 1.0f)
        g2d.fillRect(0,0,Main.WIDTH, Main.HEIGHT);

        g.setColor(new Color(0x3C6255));
        g.fillRoundRect(350,100,400,550,20,20);

        g.setColor(new Color(0xFDF9BC));
        g.fillRoundRect(400,520, 300,80,20,20);
        g.fillRoundRect(400,390, 300,80,20,20);
        g.fillRoundRect(400,260, 300,80,20,20);

        g.setColor(Color.BLACK);
        g.setFont(fontString);
        g.drawString("EXIT", 500, 565);
        g.drawString("REPLAY", 490, 435);
        g.drawString("RESUME", 490, 305);

        try {
            imgMusicOn = ImageIO.read(new File(saveDataPath + "/MusicOn.png"));
            imgMusicOff = ImageIO.read(new File(saveDataPath + "/MusicOff.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        if(Main.isMusic) {
            g.drawImage(imgMusicOn, Main.WIDTH/2-40, 150, 80, 70, null);
        }else g.drawImage(imgMusicOff, Main.WIDTH/2-40, 150, 80, 70, null);
        g2d.dispose();
    }
    private int getTileY(int row) {
        return SPACING + row * SQUARE_SIZE+ row * SPACING;
    }
    private int getTileX(int col) {
        return SPACING + col * SQUARE_SIZE + col * SPACING;
    }

    private int getPoint(int x){
        return (int)((x-MainPanel.SPACING)/(MainPanel.SQUARE_SIZE+ MainPanel.SPACING));
    }
    public Board getBoard() {
        return board;
    }


    public void lauchGame(){
        game = new Thread(this);
        game.start();
    }

    @Override
    public void run() {
        //game loop
        double drawInterval = 1000000000/FPS;
        double delta =0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (game!= null){
            currentTime = System.nanoTime();
            delta += (currentTime- lastTime)/drawInterval;
            lastTime= currentTime;

            if(delta>= -1){
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update(){
        if(!board.isGameOn()) board.setGameOn(validTime());
        if(board.isGameOn()&& board.isGameContinue()) {
            int col = getPoint(mouse.mx);
            int row = getPoint(mouse.my);
            if (row <= Board.ROWS && col <= Board.COLS) {
                manageTurn(row,col);
            }
        }
    }

    public void manageTurn(int row, int col){
        if(BoardHelper.hasAnyMoves(board.getBoard(),1) || BoardHelper.hasAnyMoves(board.getBoard(),2)) {
            dudoan = BoardHelper.getPointMove(board.getBoard(), board.turn);
            if (board.turn == 1) {
                timeBlack.timeRun(true);
                timeWhite.timeRun(false);
                if(BoardHelper.hasAnyMoves(board.getBoard(),1)) {
                    if (player1.isUserPlayer()) {
                        handleClick(row,col);
                        //after click this function should be call backed
                    } else {
                        handleAI(player1);
                    }
                }else{
                    //forfeit this move and pass the turn
                    System.out.println("Player 1 has no legal moves !");
                    board.turn = 2;
                    manageTurn(row,col);
                }
            } else if (board.turn == 2) {
                timeWhite.timeRun(true);
                timeBlack.timeRun(false);
                if(BoardHelper.hasAnyMoves(board.getBoard(),2)) {
                    if (player2.isUserPlayer()) {
                        handleClick(row,col);
                        //after click this function should be call backed
                    } else {
                        handleAI(player2);
                    }
                }else{
                    //forfeit this move and pass the turn
                    System.out.println("Player 2 has no legal moves !");
                    board.turn = 1;
                    manageTurn(row,col);
                }
            }
        }else{
            //game finished
            System.out.println("Game Finished !");
            board.printBoard();
            board.setGameOn(false);
        }
    }

    public void handleClick(int i,int j){
        if(mouse.pressed && validPiece(i,j)){
            if(BoardHelper.canPlay(i,j,board.turn,board.getBoard())){
            System.out.println("User Played in : "+ i + " , " + j);
            newPiece = new Piece(i,j,board.turn);

            //update board
            board.setBoard(BoardHelper.getNewBoardAfterMove(board.getBoard(), i,j,board.turn));

            //advance turn
            board.turn = (board.turn == 1) ? 2 : 1;

            System.out.println(""+board.turn);

            mouse.pressed=false;

            //callback
            manageTurn(i,j);
        }
        }
    }

    public void handleAI(GamePlayer ai){
        Piece[][] newboard = board.saveBoard();
        Point aiPlayPoint = ai.play(board.getBoard());
        int i = aiPlayPoint.x;
        int j = aiPlayPoint.y;
        board.setBoard(newboard);
        newPiece = new Piece(i,j,board.turn);
        if(!BoardHelper.canPlay(i,j,board.turn, board.getBoard())) System.err.println("FATAL : AI Invalid Move !");
        System.out.println(ai.playerName() + " Played in : "+ i + " , " + j);
        //update board
        board.setBoard(BoardHelper.getNewBoardAfterMove(board.getBoard(),i,j,board.turn));

        //advance turn
        board.turn = (board.turn == 1) ? 2 : 1;
    }

    public boolean validPiece(int row, int col){
        if(row<0||col<0||row>Board.ROWS-1||col>Board.COLS-1) return false;
        return true;
    }
    public boolean validTime(){
        return timeBlack.getMinutes() <= 5 || timeWhite.getMinutes() <= 5;
    }

    public void setAIplayer(){
        player2= new AIPlayerDynamic(2, 6);
    }

}
