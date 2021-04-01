package com.game.wantingting;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 地面类
 *  地面起点（x,y）
 *  地面尺寸 landSize
 *  地面图片 landImage
 */
public class Land {
    public static Dimension landSize ;
    private int x ;
    private int y;
    private static BufferedImage landImage ;

    /**
     * 获取图片资源，指定地面尺寸
     */
    static {
        try {
            landImage = ImageIO.read(new File("images/land.png"));
        } catch (IOException e) {
            System.out.println("找不到地面图片");
        }
        landSize = new Dimension(landImage.getWidth(),landImage.getHeight());
    }

    /**
     * 初始化地面起点
     */
    public Land() {
        x = (Background.screenSize.width - landImage.getWidth())/2;
        y = Background.screenSize.height - landImage.getHeight()/2;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public BufferedImage getLandImage() {
        return landImage;
    }
}
