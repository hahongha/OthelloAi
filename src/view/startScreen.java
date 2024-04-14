package view;

import main.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import control.changePanel;

public class startScreen extends JPanel{
    private static final long serialVersionUID = 1L;
    public static String saveDataPath = System.getProperty("user.dir") +File.separator
            + "lib" + File.separator+"Pic"+File.separator;
    public JButton btnAI;
    public JButton btnHuman;

    public JButton btnMusic;

    public JButton btnRule;

    private ImageIcon imageIconOn;
    private ImageIcon imageIconOff;

    BufferedImage imgMusicOn = null;
    BufferedImage imgMusicOff = null;

    BufferedImage ruleScreen = null;

    private MainPanel mainPanel;
    private Main window;

    private changePanel change;

    private startScreen startScreen;

    private boolean rule= false;
    public startScreen(Main jf){
        this.startScreen = this;
        this.window= jf;
        this.mainPanel = new MainPanel(jf);
        this.change = new changePanel(jf, mainPanel, startScreen);
        setBackground(Color.BLACK);
        setLayout(null);
        setSize(Main.WIDTH, Main.HEIGHT);
        rule = false;
    }

    public void renderRule(Graphics g, BufferedImage image){
        g.drawImage(image, 0, 0,Main.WIDTH, Main.HEIGHT, null);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage logo = null;
        BufferedImage imgHuman = null;
        BufferedImage imgAI = null;
        BufferedImage imgRule= null;
        try {
            logo = ImageIO.read(new File(saveDataPath+"/Title.png"));
            imgHuman = ImageIO.read(new File(saveDataPath+"/buttonVsHuman.png"));
            imgAI = ImageIO.read(new File(saveDataPath+"/buttonVsRobot.png"));
            imgMusicOn = ImageIO.read(new File(saveDataPath+"/MusicOn.png"));
            imgMusicOff = ImageIO.read(new File(saveDataPath+"/MusicOff.png"));
            imgRule = ImageIO.read(new File(saveDataPath+"/QuestionMark.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(logo, 0, 0,Main.WIDTH, Main.HEIGHT/2, null);

        btnHuman = new JButton();
        btnHuman.setText("Humans");
        btnHuman.setBounds(Main.WIDTH/3, Main.HEIGHT/2+50,Main.WIDTH/3,110);
        btnHuman.setBorderPainted(false);
        btnHuman.setFocusPainted(false);
        btnHuman.setContentAreaFilled(false);
        g.drawImage(imgHuman,Main.WIDTH/3, Main.HEIGHT/2+50,Main.WIDTH/3,110,null);
        this.add(btnHuman);
        btnHuman.addActionListener(change);



        btnAI = new JButton();
        btnAI.setBounds(Main.WIDTH/3, Main.HEIGHT-200,Main.WIDTH/3,110);
        btnAI.setBorderPainted(false);
        btnAI.setFocusPainted(false);
        btnAI.setContentAreaFilled(false);
        g.drawImage(imgAI,Main.WIDTH/3, Main.HEIGHT-200,Main.WIDTH/3,110,null);
        this.add(btnAI);
        btnAI.setText("Robots");
        btnAI.addActionListener(change);

        imageIconOn = new ImageIcon(imgMusicOn);
        imageIconOff = new ImageIcon(imgMusicOff);

        btnMusic = new JButton("Music");
        btnMusic.setBounds(30, 30,80,70);
        btnMusic.setBorderPainted(false);
        btnMusic.setFocusPainted(false);
        btnMusic.setContentAreaFilled(false);
        this.add(btnMusic);
        btnMusic.addActionListener(change);
        if(Main.isMusic)
            g.drawImage(imgMusicOn, 30,30,80,70,null);
        else g.drawImage(imgMusicOff, 30,30,80,70,null);


        btnRule = new JButton("Rule");
        btnRule.setBounds(150, 30,70,70);
        btnRule.setBorderPainted(false);
        btnRule.setFocusPainted(false);
        btnRule.setContentAreaFilled(false);
        this.add(btnRule);
        g.drawImage(imgRule, 150,30,80,70,null);
        btnRule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.movePanels(startScreen, new ruleScreen(window));
            }
        });

        g.dispose();
    }

    private JPanel getScreen(){
        return startScreen;
    }
}
