package com.game.wantingting;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 鸟类
 *      鸟的起点（x,y）
 *      鸟的速度 speed
 *      鸟的加速度 g
 *      鸟的运动距离 s
 *      鸟的运动时间 t
 *      鸟的头部角度 ratation
 *      鸟的外观 apprance
 *      鸟的飞翔姿势 fly_action
 *      鸟的尺寸 birdSize
 */
public class Bird {
    private int x;
    private int y;
    double speed = 20; // 速度
    double g = 4; // 加速度
    double s; // 运动距离
    double t = 0.3; // 运动时间
    private double ratation;
    private BufferedImage apprance ;
    private static BufferedImage[] clothesOne = new BufferedImage[3];
    private static BufferedImage[] clothesTwo = new BufferedImage[3];
    private static BufferedImage[] clothesThree = new BufferedImage[3];
    private int fly_action = 0;
    public static Dimension birdSize;

    /**
     * 获取图片资源
     * 设置鸟的尺寸
     */
    static {
        for (int i = 0; i < 3 ; i++)
        {
            try {
                clothesOne[i] = ImageIO.read(new File("images/bird0_"+i+".png"));
                clothesTwo[i] = ImageIO.read(new File("images/bird1_"+i+".png"));
                clothesThree[i] = ImageIO.read(new File("images/bird2_"+i+".png"));
            } catch (IOException e) {
                System.out.println("找不到小鸟图片");
            }
        }
        birdSize = new Dimension(clothesOne[0].getWidth(),clothesOne[0].getHeight());
    }

    /**
     * 初始化鸟的起点，角度，外观
     */
    public Bird() {
        x = Background.screenSize.width/2-100;
        y = Background.screenSize.height/2;
        ratation = 0;
        apprance = clothesOne[0];
    }

    /**
     * 正常状态
     * 初始状态V0是向上，s>0,则小鸟往上飞
     * 随时间推移，V0<0，s<0,则小鸟往下掉
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
     * 起飞
     * 修改小鸟的初速度
     */
    public void fly()
    {
        speed = 20;
    }

    /**
     * 小鸟改变外观、挥舞翅膀
     * @param level 小鸟的等级
     */
    public void changeClothes(int level)
    {
        fly_action ++;
        BufferedImage[] clothes = (level == 0?clothesOne:(level == 1?clothesTwo:clothesThree));
        apprance = clothes[fly_action%3];
    }

    /**
     * 小鸟是否撞击水管
     * @param pipe_x 水管的x坐标
     * @param high 上面水管的位置
     * @param low 下面水管的位置
     * @return true撞击水管，false未撞击水管
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
     * 小鸟是否通过水管
     * @param pipe_x 水管的x
     * @return true通过水管，false未通过水管
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
     * 小鸟是否掉落砸死
     * @return true 掉落，false 未掉落
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
     * 小鸟是否能吃到金币
     * @param coin_x 金币的x
     * @param coin_y 金币的y
     * @return true 小鸟吃到，false 小鸟吃不到
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
