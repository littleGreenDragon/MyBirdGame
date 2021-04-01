package com.game.wantingting;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 背景类
 *    背景尺寸 screenSize
 *    背景起点（x,y）
 *    当前背景图片 background
 *    白天背景图片 dark
 *    晚上背景图片 night
 */
public class Background {
    public static  Dimension screenSize;
    private int x;
    private int y;
    private BufferedImage background;
    private static  BufferedImage dark ;
    private static  BufferedImage night;

    /**
     * 获取图片资源，设置背景大小
     */
    static {
        try {
            dark = ImageIO.read(new File("images/bg_dark.png"));
            night = ImageIO.read(new File("images/bg_night.png"));
        } catch (IOException e) {
            System.out.println("找不到背景图片");
        }
        screenSize = new Dimension(dark.getWidth(),dark.getHeight());
    }

    /**
     * 指定背景样式和背景起点
     */
    public Background() {
        background = dark;
        x = 0;
        y = 0;
    }

    /**
     * 根据游戏时长才完成天黑天亮操作
     * @param score 用户通过的钢管数
     */
    public void changeBackage(int score)
    {
        if (score%15 ==0)
        {
            background = (background == dark?night:dark);
        }
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getBackground() {
        return background;
    }

}
