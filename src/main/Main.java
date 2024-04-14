package main;

import model.Sound;
import view.startScreen;
import view.ruleScreen;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static int WIDTH= 1100;
    public static int HEIGHT= 750;
    private startScreen start;
    private  JFrame window;
    private Sound sound;

    public static boolean isMusic = true;

    private ruleScreen ruleScreen;


    public Main(){
        window = new JFrame("Othello");
        window.setSize(1100,750);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);// chan khong cho thay doi kich thuoc
        window.setBackground(Color.GRAY);

        //add game
        //game = new MainPanel(this);
        setSound(new Sound());
        getSound().play("piano.wav", Clip.LOOP_CONTINUOUSLY);
        start = new startScreen(this);
        //ruleScreen = new ruleScreen(this);

        window.add(start);
        //window.add(game);
        //window.pack();//chinh kich thuoc vua voi bang

        window.setLocationRelativeTo(null);// can giua
        window.setVisible(true);// hien thi

        //game.lauchGame();
    }


        public void movePanels(JPanel panel1, JPanel panel2) {
        window.remove(panel1);
        window.add(panel2, BorderLayout.CENTER);
        panel2.requestFocusInWindow();
        window.validate();
    }
    public static void main(String[] args) {
        new Main();
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }
}
