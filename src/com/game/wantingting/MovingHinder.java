package com.game.wantingting;

import java.util.Random;

/**
 * 会移动的水管
 *      水管移动的方向 orientation
 */
public class MovingHinder extends Hinder {
    public  int orientation;

    /**
     * 初始化水管移动方向
     */
    public MovingHinder() {
        Random random = new Random();
        orientation = random.nextInt(2);
    }

    /**
     * 水管移动
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
     * 修改水管移动方向
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
