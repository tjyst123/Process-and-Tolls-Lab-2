package ui;
import constVector.constValue;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;


public class easyFrame extends JFrame implements ActionListener{

    Image calImg=(new ImageIcon("src/background/calImg.png")).getImage();


    private final JPanel jp_north = new JPanel();
    private final JPanel jp_center = new JPanel();
    private final JPanel jp_south = new JPanel();
    private final JTextField textField = new JTextField();   // 显示的文本框
    private final JButton jb_Music = new JButton();    // Music按钮
    private final JButton jb_left = new JButton();  // 左括号
    private final JButton jb_right = new JButton(); // 右括号
    private final JButton jb_clear = new JButton(); // clear按钮
    private final JButton jb_scientific = new JButton(); // 转换按钮
    private final JButton jb_equal = new JButton(); // =按钮

    public static int flag1 = 0;  // 用来判断背景音乐的播放和停止
    public static int flag2 = 0;
    public static int flag3 = 0;

    // 用来实现背景音乐的播放
    File f1 = new File("src/background/bgmusic.wav");
    File f2 = new File("src/background/安和桥间奏.wav");
    File f3 = new File("src/background/月笠.wav");

    public static AudioClip aau1,aau2,aau3;
    {
        try {
            aau1 = Applet.newAudioClip(f1.toURI().toURL());
            aau2= Applet.newAudioClip(f2.toURI().toURL());
            aau3= Applet.newAudioClip(f3.toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }



    public easyFrame(){
        this.init();
    }

    /**
     *  初始化窗口
     */
    public void init(){
        this.setIconImage(calImg);
        this.setTitle("简单计算器");
        this.setSize(constValue.Frame_W, constValue.Frame_H);  // 设置窗口的宽和高
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 设置窗口关闭
        this.setLayout(new BorderLayout()); // 设置窗口布局
        this.setLocation(constValue.Frame_X, constValue.Frame_Y);
        this.setResizable(false); // 设置窗口大小不可改变
        this.setVisible(true);
        this.addNorthFrame();
        this.addSouthFrame();
        this.addCenterFrame();
    }


    public void addNorthFrame() {
        jp_north.setLayout(new FlowLayout(FlowLayout.LEFT));
        jp_north.setPreferredSize(new Dimension(800, 140));
        this.textField.setPreferredSize(new Dimension(784,140));
        this.textField.setHorizontalAlignment(JTextField.RIGHT);
        this.textField.setFont(new Font("Times New Roman",Font.BOLD,26));
        this.textField.setEditable(false);
        this.textField.setBackground(Color.WHITE);
        jp_north.add(textField);
        this.add(jp_north, BorderLayout.NORTH);
    }

    public void addCenterFrame(){
        jp_center.setPreferredSize(new Dimension(800,140));
        GridLayout ly_center = new GridLayout(1, 6);
        this.jp_center.setLayout(ly_center);

        Font font_center = new Font("Times New Roman", Font.BOLD,23);

        jb_Music.setText("Music");
        jb_Music.setFont(font_center);
        jb_Music.setBackground(Color.WHITE);
        jb_Music.setOpaque(false);
        jb_Music.setFocusPainted(false);
        jb_Music.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musicFrame mf = new musicFrame();
            }
        });
        jp_center.add(jb_Music);


        jb_left.setText("(");
        jb_left.setFont(font_center);
        jb_left.setBackground(Color.WHITE);
        jb_left.setOpaque(false);
        jb_left.setFocusPainted(false);
        jb_left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText(textField.getText() + "(");
            }
        });
        jp_center.add(jb_left);

        jb_right.setText(")");
        jb_right.setFont(font_center);
        jb_right.setBackground(Color.WHITE);
        jb_right.setOpaque(false);
        jb_right.setFocusPainted(false);
        jb_right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText(textField.getText() + ")");
            }
        });
        jp_center.add(jb_right);

        jb_clear.setText("clear");
        jb_clear.setFont(font_center);
        jb_clear.setBackground(Color.WHITE);
        jb_clear.setOpaque(false);
        jb_clear.setFocusPainted(false);
        jb_clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
            }
        });
        jp_center.add(jb_clear);

        jb_scientific.setText("Scientific");
        jb_scientific.setFont(font_center);
        jb_scientific.setBackground(Color.WHITE);
        jb_scientific.setOpaque(false);
        jb_scientific.setFocusPainted(false);
        jb_scientific.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculatorFrame sci_calc = new calculatorFrame();
            }
        });
        jp_center.add(jb_scientific);

        jb_equal.setText("=");
        jb_equal.setFont(font_center);
        jb_equal.setFocusPainted(false);
        jb_equal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String wait_cal = textField.getText();
                if(wait_cal.length() == 0) return;
                try{
                    Calculator_f cal = new Calculator_f();
                    double res = cal.calculate(wait_cal);
                    textField.setText(wait_cal + " = " + res);
                }catch(Exception e1){
                    textField.setText("Illegal Expression! Please ReEnter");
                }
            }
        });
        jp_center.add(jb_equal);

        this.add(jp_center, BorderLayout.CENTER);

    }

    public void addSouthFrame(){
        jp_south.setPreferredSize(new Dimension(800, 320));
        GridLayout layout = new GridLayout(4, 4);
        this.jp_south.setLayout(layout);
        String str = "789+456-123*.0</";
        for(int i = 0; i < 16; i++){
            JButton button = new JButton();
            String temp = str.substring(i, i+1);
            if(temp.charAt(0)>='0' && temp.charAt(0) <= '9') button.setBackground(Color.WHITE);
            else{
                button.setBackground(Color.WHITE);
                button.setOpaque(false);
            }
            button.setText(temp);
            button.setFocusPainted(false);
            button.setFont(new Font("Times New Roman", Font.BOLD,25));
            jp_south.add(button);
            button.addActionListener(this);
        }
        this.add(jp_south, BorderLayout.SOUTH);
    }


    /**
     * @param str 按钮的字符
     * @return 按钮的类型
     * 返回1 : .0123456789()+-/*
     * 返回2 : < 回退
     * 功能函数 Music clear Scientific = 这里不作处理
     */
    public int help(String str){
        String number = ".0123456789()+-*/";
        if(number.contains(str)) return 1;
        if(str.equals("<")) return 2;
        return 0;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String click_str = e.getActionCommand();
        int op = help(click_str);
        if (op == 1) {
            this.textField.setText(this.textField.getText() + click_str);
        } else if (op == 2) {
            String pre = this.textField.getText();
            if (pre.length() == 0) {
                this.textField.setText("");
            } else {
                pre = pre.substring(0, pre.length() - 1);
                this.textField.setText(pre);
            }
        }

    }

}



