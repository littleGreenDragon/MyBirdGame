package com.game.wantingting;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * ������
 *    �����ߴ� screenSize
 *    ������㣨x,y��
 *    ��ǰ����ͼƬ background
 *    ���챳��ͼƬ dark
 *    ���ϱ���ͼƬ night
 */
public class Background {
    public static  Dimension screenSize;
    private int x;
    private int y;
    private BufferedImage background;
    private static  BufferedImage dark ;
    private static  BufferedImage night;

    /**
     * ��ȡͼƬ��Դ�����ñ�����С
     */
    static {
        try {
            dark = ImageIO.read(new File("images/bg_dark.png"));
            night = ImageIO.read(new File("images/bg_night.png"));
        } catch (IOException e) {
            System.out.println("�Ҳ�������ͼƬ");
        }
        screenSize = new Dimension(dark.getWidth(),dark.getHeight());
    }

    /**
     * ָ��������ʽ�ͱ������
     */
    public Background() {
        background = dark;
        x = 0;
        y = 0;
    }

    /**
     * ������Ϸʱ������������������
     * @param score �û�ͨ���ĸֹ���
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
