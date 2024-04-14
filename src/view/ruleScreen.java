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

public class ruleScreen extends JPanel{

    Main main;

    JTextArea jTextArea;

    JButton jButton;
//    public static String saveDataPath = System.getProperty("user.dir") +File.separator
//            + "lib" + File.separator+"Pic"+File.separator;

    String text = "\tTrong trò chơi này, người đi trước sẽ được mặc định là người chơi quân đen." +
            "Đặt 4 quân cờ: hai quân mặt đen, hai mặt trắng ở 4 ô trung tâm của bàn cờ, sao cho " +
            "cùng một hướng bàn cờ thì quân đen ở bên trái, quân trắng ở bên phải. Mỗi người chơi được" +
            " chia 30 quân cờ mỗi người. Người chơi lần lượt chơi xen kẽ nhau theo thứ tự bằng các nước " +
            " đi hợp lệ.\n " +
            "\tMột nước di chuyển được thực hiện bằng cách đặt một quân màu của người chơi lên bảng ở vị trí " +
            "sao cho tạo nên ít nhất một hàng ngang hoặc dọc, hoặc chéo giữa quân mới này với các quân cũ " +
            "và ở giữa các quân này có một hay nhiều quân của đối thủ. Sau khi đặt quân xong, người chơi lật " +
            "tất cả các quân màu của đối thủ nằm trên đường gióng giữa quân mới và quân cũ, biến chúng thành " +
            "quân của mình. Tất cả các quân cờ của đối thủ đều phải được lật dù người chơi có muốn hay không.\n " +
            "\tNếu người chơi không thể đặt và lật quân nào, người chơi sẽ bị mất lượt và chuyển sang lượt của " +
            "đối thủ. \n" +
            "\tTrò chơi kết thúc khi cả hai người chơi đều không thể thực hiện nước cờ hợp lệ nào nữa. \n" +
            "\tĐiều này xảy ra khi các ô trên bàn cờ đã kín hết quân hay khi một bên chơi không còn quân nào " +
            "trên bàn cờ. Khi đó, người chơi nào có nhiều quân trên bàn cờ hơn là người thắng cuộc. \n";
    public ruleScreen(Main jf){
        this.main = jf;
        setLayout(null);
        setSize(Main.WIDTH, Main.HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(new Color(0xccff99));
        g.fillRect(0,0,Main.WIDTH, Main.HEIGHT);
        BufferedImage imgBack = null;
        try {
            imgBack =ImageIO.read(new File(startScreen.saveDataPath+"arrow-left.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        jTextArea = new JTextArea();
        jTextArea.append(text);
        jTextArea.setFont(new Font("Monospaced", Font.BOLD, 20));
        jTextArea.setBackground(new Color(0xccff99));
        jTextArea.setPreferredSize(new Dimension(200, 150));
        jTextArea.setBounds(0,80,Main.WIDTH,Main.HEIGHT-280);
        jTextArea.setLineWrap(true); // Tự động xuống dòng
        jTextArea.setWrapStyleWord(true); // Chỉ xuống dòng tại khoảng trắng gần nhất
        jTextArea.setEditable(false);
        add(jTextArea);

        jButton = new JButton("Back");
        jButton.setBounds(0,0,80,80);
        jButton.setBorderPainted(false);
        jButton.setFocusPainted(false);
        jButton.setContentAreaFilled(false);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.movePanels(ruleScreen.this,new startScreen(main));
            }
        });
        add(jButton);
        g.drawImage(imgBack, 0,0,80,80, null);
    }
}
