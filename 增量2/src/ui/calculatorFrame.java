package ui;

import constVector.constValue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;


public class calculatorFrame extends JFrame implements ActionListener {
    private final JPanel jp_north = new JPanel();
    private final JPanel jp_center = new JPanel();
    private final JPanel jp_south = new JPanel();
    private final JTextField textField = new JTextField();   // 上方输入的文本框
    private final JButton jb_clear = new JButton();    // clear按钮
    private final JButton jb_equal = new JButton();   // =按钮

    Image calImg=(new ImageIcon("src/background/calImg.png")).getImage();

    Font sciFont_center = new Font("Times New Roman", Font.BOLD,23);
    Font sciFont_south = new Font("Times New Roman", Font.BOLD,27);



    public calculatorFrame()  {
        this.init();
    }

    public void init(){
        // 窗口的初始化
        // this.getContentPane().setBackground(Color.pink);
        this.setIconImage(calImg);
        this.setTitle("科学计算器");
        this.setSize(constValue.Frame_W, constValue.Frame_H);  // 设置窗口的宽和高
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 设置窗口关闭
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(constValue.Frame_X, constValue.Frame_Y);
        this.setResizable(false); // 设置窗口大小不可改变
        this.setVisible(true);
        this.addNorthFrame();
        this.addSouthFrame();
        this.addCenterFrame();
    }

    public void addNorthFrame(){
        // 增加北边的组件 (包括上方的文本框 和 清空按钮 和 等于按钮)
        jp_north.setLayout(new FlowLayout(FlowLayout.LEFT));
        jp_north.setPreferredSize(new Dimension(800, 120));
        this.textField.setPreferredSize(new Dimension(595,120));
        this.textField.setHorizontalAlignment(JTextField.RIGHT);
        this.textField.setEditable(false);
        this.textField.setBackground(Color.WHITE);
        this.textField.setFont(new Font("Times New Roman",Font.BOLD,24));

        jb_clear.setText("clear");
        jb_clear.setFont(new Font("Times New Roman", Font.BOLD,22));
        jb_clear.setPreferredSize(new Dimension(90, 120));
        jb_clear.setBackground(Color.WHITE);
        jb_clear.setOpaque(false);
        jb_clear.setFocusPainted(false);

        jb_equal.setText("=");
        jb_equal.setFont(new Font("Times New Roman", Font.BOLD,26));
        jb_equal.setPreferredSize(new Dimension(90, 120));
        jb_equal.setFocusPainted(false);

        jp_north.add(textField);
        jp_north.add(jb_clear);
        jp_north.add(jb_equal);

        // clear按钮事件
        jb_clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
            }
        });

        // =按钮事件
        jb_equal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str_wait_cal = textField.getText();
                if(str_wait_cal.length() == 0) return;
                try{
                    Calculator_s sci_cal = new Calculator_s();
                    double res = sci_cal.calculate(str_wait_cal);
                    textField.setText(str_wait_cal + " = " + res);
                }catch(Exception e1){
                    textField.setText("Illegal Expression! Please ReEnter");
                }
            }
        });
        this.add(jp_north, BorderLayout.NORTH);
    }


    /**
     * 增加中间的按钮
     */
    public void addCenterFrame(){

        jp_center.setPreferredSize(new Dimension(800,160));
        GridLayout ly_center = new GridLayout(2, 5);
        this.jp_center.setLayout(ly_center);
        /*
        pow(,)代表幂次 squ代表平方 abs代表绝对值 sqt代表平方根 sbt代表立方根
         */

        String[] button_label = {"Music","pow",",","(",")","squ","sqt","sin","cos","tan"};
        for(int i = 0; i < 10; i++){
            JButton button_center = new JButton();
            button_center.setText(button_label[i]);
            button_center.setFocusPainted(false);
            button_center.setFont(sciFont_center);
            button_center.setBackground(Color.WHITE);
            button_center.setOpaque(false);
            jp_center.add(button_center);
            button_center.addActionListener(this);
        }
        this.add(jp_center, BorderLayout.CENTER);
    }

    /**
     * 增加底部的按钮
     */
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
            button.setFont(sciFont_south);
            jp_south.add(button);
            button.addActionListener(this);
        }
        this.add(jp_south, BorderLayout.SOUTH);
    }

    /**
     * @param click 鼠标点击的按钮
     * @return 按钮的类型
     * 返回1 代表直接加上
     * 返回2 代表是功能函数 直接加上
     * 返回3 代表是< 清除一个格字
     */
    public int helpEvent(String click){
        String str = ".0123456789()+-*/,";
        HashMap<String, Integer> mp = new HashMap<>();
        mp.put("pow",0);
        mp.put("squ",0);
        mp.put("sqt",0);
        mp.put("sin",0);
        mp.put("cos",0);
        mp.put("tan",0);
        if(str.contains(click)) return 1;
        else if(mp.containsKey(click)) return 2;
        else if(click.equals("<")) return 3;
        return -1;
    }

    /**
     * @param e 事件
     * 用来处理按钮的事件
     */
    @Override
    public void actionPerformed(ActionEvent e){
        String click_str = e.getActionCommand();
        if(click_str.equals("Music")){
            musicFrame sci_mf = new musicFrame();
        }

        int op = helpEvent(click_str);
        if(op == 1){
            this.textField.setText(this.textField.getText() + click_str);
        }else if(op == 3){  // backspace button
            String pre = this.textField.getText();
            if(pre.length() == 0){
                this.textField.setText("");
            }else{
                pre = pre.substring(0, pre.length() - 1);
                this.textField.setText(pre);
            }
        }else if(op == 2){
            this.textField.setText(this.textField.getText() + click_str);
        }
    }



}
