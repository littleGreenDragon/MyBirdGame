package com.game.wantingting;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * ������
 *  ������㣨x,y��
 *  ����ߴ� landSize
 *  ����ͼƬ landImage
 */
public class Land {
    public static Dimension landSize ;
    private int x ;
    private int y;
    private static BufferedImage landImage ;

    /**
     * ��ȡͼƬ��Դ��ָ������ߴ�
     */
    static {
        try {
            landImage = ImageIO.read(new File("images/land.png"));
        } catch (IOException e) {
            System.out.println("�Ҳ�������ͼƬ");
        }
        landSize = new Dimension(landImage.getWidth(),landImage.getHeight());
    }

    /**
     * ��ʼ���������
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
