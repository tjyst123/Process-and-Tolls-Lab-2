package ui;

import javax.swing.*;
import constVector.constValue;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class musicFrame extends JFrame {

    private final JPanel jp_north = new JPanel();
    private final JPanel jp_center = new JPanel();
    private final JPanel jp_south = new JPanel();
    private final JButton jb_1 = new JButton();
    private final JButton jb_2 = new JButton();
    private final JButton jb_3 = new JButton();

    ImageIcon icon1 = new ImageIcon("src/background/m1.png");
    ImageIcon icon2 = new ImageIcon("src/background/m2.png");
    ImageIcon icon3 = new ImageIcon("src/background/m3.png");
    Image img_music=(new ImageIcon("src/background/music.jpg")).getImage();



    public musicFrame(){
        this.init();
    }

    public void init(){

        this.setTitle("Music");
        this.setIconImage(img_music);

//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 设置窗口关闭
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        this.setSize(constValue.Frame_W, constValue.Frame_H);  // 设置窗口的宽和高

        this.setLocation(constValue.Frame_X, constValue.Frame_Y);
        this.setResizable(false); // 设置窗口大小不可改变
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        Dimension button_dimension = new Dimension(200,185);

        jb_1.setPreferredSize(button_dimension);
        jb_1.setBackground(Color.WHITE);
        jb_1.setOpaque(false);
        jb_1.setIcon(icon1);
        jb_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                easyFrame.flag1++;
                if(easyFrame.flag1%2 == 1) easyFrame.aau1.play();
                else easyFrame.aau1.stop();
            }
        });
        jp_north.setLayout(new FlowLayout(FlowLayout.CENTER));
        jp_north.add(jb_1);


        jb_2.setPreferredSize(button_dimension);
        jb_2.setBackground(Color.WHITE);
        jb_2.setOpaque(false);
        jb_2.setIcon(icon2);
        jb_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                easyFrame.flag2++;
                if(easyFrame.flag2%2 == 1) easyFrame.aau2.play();
                else easyFrame.aau2.stop();
            }
        });
        jp_center.setLayout(new FlowLayout(FlowLayout.CENTER));
        jp_center.add(jb_2);

        jb_3.setPreferredSize(button_dimension);
        jb_3.setBackground(Color.WHITE);
        jb_3.setOpaque(false);
        jb_3.setIcon(icon3);
        jb_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                easyFrame.flag3++;
                if(easyFrame.flag3%2 == 1) easyFrame.aau3.play();
                else easyFrame.aau3.stop();
            }
        });
        jp_south.setLayout(new FlowLayout(FlowLayout.CENTER));
        jp_south.add(jb_3);

        this.add(jp_north, BorderLayout.NORTH);
        this.add(jp_center, BorderLayout.CENTER);
        this.add(jp_south, BorderLayout.SOUTH);
    }

}

