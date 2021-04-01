package com.game.wantingting;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * 水管：
 *      水管尺寸 pipeSize
 *      硬币尺寸 coinSize
 *      水管x坐标 x
 *      上水管y坐标 up_y
 *      下水管y坐标 down_y
 *      硬币x坐标 coin_x
 *      硬币y坐标 coin_y
 *      水管颜色 color
 *      水管间隙 gap
 *      当前水管皮肤  couplePipe
 *      当前水管中间是否有硬币 haveCoin
 */
public class Hinder {
    public static Dimension pipeSize;
    public static Dimension coinSize;
    protected int x;
    protected int up_y;
    protected int down_y;
    protected int coin_x;
    protected int coin_y;
    protected int color ;
    protected int gap;
    BufferedImage[] couplePipe ;
    private static  BufferedImage coin ;
    private  static BufferedImage[] pipeClothOne = new BufferedImage[2];
    private static BufferedImage[] pipeClothTwo = new BufferedImage[2];
    Random random = new Random();
    public static final boolean OUT = true;
    public static final boolean STEAL = false;
    private boolean haveCoin;

    /**
     * 获取图片资源，指定水管、硬币尺寸
     */
    static {
        try {
            pipeClothOne[0] = ImageIO.read(new File("images/pipe_up.png"));
            pipeClothOne[1] = ImageIO.read(new File("images/pipe_down.png"));
            pipeClothTwo[0] = ImageIO.read(new File("images/pipe2_up.png"));
            pipeClothTwo[1] = ImageIO.read(new File("images/pipe2_down.png"));
            coin = ImageIO.read(new File("images/medals_0.png"));
        } catch (IOException e) {
            System.out.println("找不到水管图片");
        }
        pipeSize = new Dimension(pipeClothOne[0].getWidth(),pipeClothOne[0].getHeight());
        coinSize = new Dimension(coin.getWidth(),coin.getHeight());
    }

    /**
     * 初始化水管颜色，位置，硬币位置，间隙大小
     */
    public Hinder() {
        color = random.nextInt(2)+1;
        couplePipe = (color == 1?pipeClothOne:pipeClothTwo);
        x = Background.screenSize.width;
        up_y = - random.nextInt(pipeSize.height);
        gap = random.nextInt(Background.screenSize.height-pipeSize.height-up_y-Land.landSize.height/2-Bird.birdSize.height*3)+Bird.birdSize.height*3;
        down_y = pipeSize.height+gap+up_y;
        if (down_y+pipeSize.height < Background.screenSize.height)
        {
            down_y = Background.screenSize.height-pipeSize.height;
            gap = down_y - pipeSize.height-up_y;
        }
        coin_y = (int)(down_y - coinSize.height*0.5 -gap*0.5);
        coin_x = (int)(x + pipeSize.width*0.5 - coinSize.width*0.5);
        haveCoin = (random.nextInt(2)==0?true:false);
    }

    /**
     * 指定位置的水管
     * @param x 水管x坐标
     */
    public Hinder(int x)
    {
        this();
        this.x = x;
        coin_x = (int)(x + pipeSize.width*0.5 - coinSize.width*0.5);
    }

    /**
     * 水管移动
     */
    public void move()
    {
        x--;
        coin_x--;
    }

    /**
     * 水管出界面
     * @return OUT 出界面，STEAL 未出界面
     */
    public boolean isOut()
    {
        if (x < -pipeSize.width)
            return OUT;
        return STEAL;
    }
    public int getX() {
        return x;
    }

    public int getUp_y() {
        return up_y;
    }

    public int getDown_y() {
        return down_y;
    }

    public BufferedImage[] getCouplePipe() {
        return couplePipe;
    }

    public boolean isHaveCoin() {
        return haveCoin;
    }

    public static BufferedImage getCoin() {
        return coin;
    }

    public int getCoin_y() {
        return coin_y;
    }

    public int getCoin_x() {
        return coin_x;
    }
}
