package com.game.wantingting;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * ����
 *      �����㣨x,y��
 *      ����ٶ� speed
 *      ��ļ��ٶ� g
 *      ����˶����� s
 *      ����˶�ʱ�� t
 *      ���ͷ���Ƕ� ratation
 *      ������ apprance
 *      ��ķ������� fly_action
 *      ��ĳߴ� birdSize
 */
public class Bird {
    private int x;
    private int y;
    double speed = 20; // �ٶ�
    double g = 4; // ���ٶ�
    double s; // �˶�����
    double t = 0.3; // �˶�ʱ��
    private double ratation;
    private BufferedImage apprance ;
    private static BufferedImage[] clothesOne = new BufferedImage[3];
    private static BufferedImage[] clothesTwo = new BufferedImage[3];
    private static BufferedImage[] clothesThree = new BufferedImage[3];
    private int fly_action = 0;
    public static Dimension birdSize;

    /**
     * ��ȡͼƬ��Դ
     * ������ĳߴ�
     */
    static {
        for (int i = 0; i < 3 ; i++)
        {
            try {
                clothesOne[i] = ImageIO.read(new File("images/bird0_"+i+".png"));
                clothesTwo[i] = ImageIO.read(new File("images/bird1_"+i+".png"));
                clothesThree[i] = ImageIO.read(new File("images/bird2_"+i+".png"));
            } catch (IOException e) {
                System.out.println("�Ҳ���С��ͼƬ");
            }
        }
        birdSize = new Dimension(clothesOne[0].getWidth(),clothesOne[0].getHeight());
    }

    /**
     * ��ʼ�������㣬�Ƕȣ����
     */
    public Bird() {
        x = Background.screenSize.width/2-100;
        y = Background.screenSize.height/2;
        ratation = 0;
        apprance = clothesOne[0];
    }

    /**
     * ����״̬
     * ��ʼ״̬V0�����ϣ�s>0,��С�����Ϸ�
     * ��ʱ�����ƣ�V0<0��s<0,��С�����µ�
     */
    public void rest()
    {
        double v0 = speed;
        s = v0 * t - 0.5 * g * t * t;
        double vt = v0 - g * t;
        speed = vt;
        y = y - (int) s;
        ratation = s / 16;
        if (y <=  0) {
            y = 0;
        }
    }

    /**
     * ���
     * �޸�С��ĳ��ٶ�
     */
    public void fly()
    {
        speed = 20;
    }

    /**
     * С��ı���ۡ�������
     * @param level С��ĵȼ�
     */
    public void changeClothes(int level)
    {
        fly_action ++;
        BufferedImage[] clothes = (level == 0?clothesOne:(level == 1?clothesTwo:clothesThree));
        apprance = clothes[fly_action%3];
    }

    /**
     * С���Ƿ�ײ��ˮ��
     * @param pipe_x ˮ�ܵ�x����
     * @param high ����ˮ�ܵ�λ��
     * @param low ����ˮ�ܵ�λ��
     * @return trueײ��ˮ�ܣ�falseδײ��ˮ��
     */
    public boolean isImpact(int pipe_x,int high,int low)
    {
        if (pipe_x<birdSize.width+x
            &&pipe_x+ Hinder.pipeSize.width > x) {
            if (y < high || y + birdSize.height > low) {
                return true;
            }
        }
        return false;
    }

    /**
     * С���Ƿ�ͨ��ˮ��
     * @param pipe_x ˮ�ܵ�x
     * @return trueͨ��ˮ�ܣ�falseδͨ��ˮ��
     */
    public boolean pass(int pipe_x)
    {
        if (pipe_x+ Hinder.pipeSize.width == x )
        {
            return true;
        }
        return false;
    }

    /**
     * С���Ƿ��������
     * @return true ���䣬false δ����
     */
    public boolean isFall()
    {
        if (y+birdSize.height >= Background.screenSize.height - Land.landSize.height/2)
        {
            return true;
        }
        return false;
    }

    /**
     * С���Ƿ��ܳԵ����
     * @param coin_x ��ҵ�x
     * @param coin_y ��ҵ�y
     * @return true С��Ե���false С��Բ���
     */
    public boolean canGatherCoin(int coin_x,int coin_y)
    {
        Dimension[] fourCorner = new Dimension[4];
        fourCorner[0] = new Dimension(x,y);
        fourCorner[1] = new Dimension(x+birdSize.width,y);
        fourCorner[2] = new Dimension(x,y+birdSize.height);
        fourCorner[3] = new Dimension(x+birdSize.width,y+birdSize.height);
        for (int i = 0;i<3;i++)
        {
            if (((fourCorner[i].width>=coin_x&&fourCorner[i].width<=coin_x+ Hinder.coinSize.width)
                    &&(fourCorner[i].height>=coin_y&&fourCorner[i].height<=coin_y+ Hinder.coinSize.height)))
            {
                return true;
            }
        }
        return false;
    }
    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }


    public double getRatation() {
        return ratation;
    }


    public BufferedImage getApprance() {
        return apprance;
    }

}
