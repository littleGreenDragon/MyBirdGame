package com.game.wantingting;

import java.util.Random;

/**
 * ���ƶ���ˮ��
 *      ˮ���ƶ��ķ��� orientation
 */
public class MovingHinder extends Hinder {
    public  int orientation;

    /**
     * ��ʼ��ˮ���ƶ�����
     */
    public MovingHinder() {
        Random random = new Random();
        orientation = random.nextInt(2);
    }

    /**
     * ˮ���ƶ�
     */
    @Override
    public void move() {
        super.move();
        if (orientation == 0)
        {
            up_y--;
            down_y--;
            coin_y--;
        }
        else
        {
            up_y++;
            down_y++;
            coin_y++;
        }
        change();
    }

    /**
     * �޸�ˮ���ƶ�����
     */
    public void change()
    {
        if (up_y==0||down_y == Background.screenSize.height-Land.landSize.height/2- Hinder.pipeSize.height
                ||up_y == -Hinder.pipeSize.height||down_y == Background.screenSize.height-Land.landSize.height/2)
        {
            orientation = (orientation+1)%2;
        }
    }
}
