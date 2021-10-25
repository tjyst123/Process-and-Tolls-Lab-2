package constVector;

import java.awt.*;

public class constValue {
    public static final int Frame_W = 800; // 窗口宽度
    public static final int Frame_H = 600; // 窗口高度
    public static final int Screen_W = Toolkit.getDefaultToolkit().getScreenSize().width;  // 获取屏幕的宽度
    public static final int Screen_H = Toolkit.getDefaultToolkit().getScreenSize().height; // 获取屏幕的高度
    // 设置窗口在中间的位置
    public static final int Frame_X = (Screen_W - Frame_W)/2;
    public static final int Frame_Y = (Screen_H - Frame_H)/2;
}
