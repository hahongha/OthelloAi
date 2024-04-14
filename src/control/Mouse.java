package control;

import main.Main;
import model.Board;
import view.MainPanel;
import model.Piece;
import view.startScreen;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
    public int mx, my;
    public boolean pressed = false;

    public startScreen startScreen;

    public Board board;
    public MainPanel gamePanel;

    public Main main;

    public static int testClick =0;

    public Mouse(Board board1, MainPanel game, Main jf){
        main = jf;
        board= board1;
        gamePanel = game;
       // startScreen = new startScreen(main);
    }
    @Override
    public void mouseClicked(MouseEvent e) {//nhan tha
        pressed=true;
        mx= e.getX();
        my= e.getY();
        if(board.isGameOn()&& board.isGameContinue()){
            if(mx> MainPanel.BOARD_WIDTH+80 && mx<MainPanel.BOARD_WIDTH+80+220){
                //pause
                if(my>400&& my<470){
                        board.turn=0;
                        gamePanel.timeBlack.timeRun(false);
                        gamePanel.timeWhite.timeRun(false);
                    board.setGameContinue(false);
                }
                //exit
                if(my>500&&my<570){
                    startScreen = new startScreen(main);
                    main.movePanels(gamePanel, startScreen);
//                    board.setGameOn(false);
                }
            }
        }
        else if (!board.isGameOn()) {
            if(mx>450&&mx<600){
                if (my>450&&my<500) {
                    startScreen = new startScreen(main);
                    main.movePanels(gamePanel, startScreen);
                }
            }
        } else if (!board.isGameContinue()) {
            //sound
            //Main.WIDTH/2-40, 150, 80, 70,
            if(mx > Main.WIDTH/2-40&&mx< Main.WIDTH/2+40){
                if (my> 150&&my< 220){
                    main.getSound().change();
                    Main.isMusic= !Main.isMusic;
                }
            }
            if(mx>400&&mx<700){
                //exit
                if (my>520&&my<600) {
                    //JOptionPane.showMessageDialog(null, "Click exit");
                    board.reset();
                    gamePanel.timeBlack.reset();
                    gamePanel.timeWhite.reset();
                    startScreen = new startScreen(main);
                    main.movePanels(gamePanel, startScreen);
                }
                //replay
                if (my>390&&my<470) {
                    board.reset();
                    gamePanel.timeBlack.reset();
                    gamePanel.timeWhite.reset();
                    gamePanel.timeBlack.timeRun(true);
                    gamePanel.newPiece = new Piece();
                }
                //resume
                if (my>260&&my<330) {
                    board.resume();
                    if(board.turn==1){
                        gamePanel.timeBlack.setStartTime(System.nanoTime());
                        gamePanel.timeBlack.timeRun(true);
                    }else{
                        gamePanel.timeWhite.setStartTime(System.nanoTime());
                        gamePanel.timeWhite.timeRun(true);
                    }
                }
            }
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed= true;
        mx= e.getX();
        my= e.getY();
//        board.printBoard();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed= false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    @Override
    public void mouseMoved(MouseEvent e) {
    }
    @Override
    public void mouseDragged(MouseEvent e) {//nhan keo
    }



}
