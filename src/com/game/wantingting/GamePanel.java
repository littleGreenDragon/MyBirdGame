package com.game.wantingting;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * 游戏容器
 *  1）游戏组件
 *      鸟 bird
 *      水管两根 pipes
 *      地面 land
 *      背景 background
 *      水管间隙 pipeGap
 *  2）玩家参数
 *      获取硬币数 coin
 *      玩家当前分数 score
 *      玩家最佳分数 best
 *      玩家等级 level
 *  3）界面、游戏参数
 *      是否开始 isStart
 *      是否结束 isOver
 *      是否捡到第一个钢管的硬币 getOneCoin
 *      是否捡到第二个钢管的硬币 getTwoCoin
 *      开始按钮x坐标 start_x
 *      开始按钮y坐标 start_y
 *      结束按钮x坐标 new_x
 *      结束按钮y坐标 new_y
 *  4）界面资源
 *      各种图片
 */
public class GamePanel extends JPanel {
    private  Bird bird = new Bird();
    private Hinder[] hinders = new Hinder[2];
    Background background = new Background();
    Land land = new Land();
    public int pipeGap;

    private static int coin;
    private int score;
    private int level;
    private int best;

    public boolean isStart;
    public boolean isOver;
    public boolean getOneCoin;
    public boolean getTwoCoin;
    public int start_x;
    public int start_y;
    public int new_x;
    public int new_y;

    public static BufferedImage startTipsImage;
    public static BufferedImage readyTextImage;
    public static BufferedImage playButton;
    public static BufferedImage overImage;
    public static BufferedImage newGameImage;
    public static BufferedImage scorePanelImage;
    public static BufferedImage goldMedal;
    public static BufferedImage silverMedal;
    public static BufferedImage bronzeMedal;
    public static BufferedImage[] numberImages = new BufferedImage[10];

    /**
     * 获取各种图片资源
     */
    static
    {
        try {
            startTipsImage = ImageIO.read(new File("images/tutorial.png"));
            playButton = ImageIO.read(new File("images/button_play.png"));
            overImage = ImageIO.read(new File("images/text_game_over.png"));
            readyTextImage = ImageIO.read(new File("images/text_ready.png"));
            newGameImage = ImageIO.read(new File("images/new.png"));
            scorePanelImage = ImageIO.read(new File("images/score_panel.png"));
            goldMedal = ImageIO.read(new File("images/medals_1.png"));
            silverMedal = ImageIO.read(new File("images/medals_2.png"));
            bronzeMedal = ImageIO.read(new File("images/medals_3.png"));
            for (int i = 0;i<10;i++)
            {
                numberImages[i] = ImageIO.read(new File("images/font_0"+(48+i)+".png"));
            }
        } catch (IOException e) {
            System.out.println("找不到界面图片");
        }

    }

    /**
     * 指定钢管间隙、初始化两个钢管
     */
    public GamePanel() {
        pipeGap = Background.screenSize.width/2;
        hinders[0] = new Hinder(Background.screenSize.width/2);
        hinders[1] = new Hinder(Background.screenSize.width/2+pipeGap);
    }

    /**
     * 游戏启动方法
     */
    public void start()
    {
        addListener();
        while(true)
        {
            if (isStart&&!isOver)//判断游戏是否开始
            {
                //判断小鸟是否死亡（撞击管道和掉地上）
                if (bird.isImpact(hinders[0].getX(), hinders[0].up_y + Hinder.pipeSize.height, hinders[0].down_y)
                        || bird.isImpact(hinders[1].getX(), hinders[1].up_y + Hinder.pipeSize.height, hinders[1].down_y)
                        || bird.isFall()) {
                    changeBest();
                    isOver = true;
                }
                //判断小鸟吃没吃到硬币
                if (hinders[0].isHaveCoin() && bird.canGatherCoin(hinders[0].getCoin_x(), hinders[0].getCoin_y())&&getOneCoin == false)
                {
                    coin ++;
                    getOneCoin = true;
                }
                if (hinders[1].isHaveCoin() && bird.canGatherCoin(hinders[1].getCoin_x(), hinders[1].getCoin_y())&&getTwoCoin == false)
                {
                    coin ++;
                    getTwoCoin = true;
                }
                //判断小鸟是否通过钢管
                if (bird.pass(hinders[0].getX())||bird.pass(hinders[1].getX()))
                {
                    upLevel();
                    background.changeBackage(score);
                }
                //钢管运动
                hinders[0].move();
                hinders[1].move();
                //小鸟换衣服和挥动翅膀
                bird.changeClothes(level);
                //不点击就休息
                bird.rest();
                //中间间隔一下
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Random random = new Random();
                //判断钢管是否移出界面，重新创建
                if (hinders[0].isOut())
                {
                    hinders[0] = random.nextInt(2)==0?new Hinder():new MovingHinder();
                    getOneCoin = false;
                }
                if (hinders[1].isOut())
                {
                    hinders[1] = random.nextInt(2)==0?new Hinder():new MovingHinder();
                    getTwoCoin = false;
                }
            }
            repaint();
        }
    }

    /**
     * 给当前容器添加监听器
     */
    public void addListener()
    {

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //如果点击了开始按钮，游戏开始
                if (!isStart
                        &&(e.getX()>=start_x &&e.getX()<=playButton.getWidth()+start_x)
                        &&(e.getY()>=start_y&&e.getY()<=playButton.getHeight()+start_y))
                {
                    isStart = true;
                }
                //如果游戏开始了，点击屏幕，小鸟飞翔
                if (isStart)
                {
                    bird.fly();
                }
                //如果点击新游戏按钮，游戏重新开始
                if (isOver
                        &&(e.getX()>=new_x&&e.getX()<=newGameImage.getWidth()+new_x)
                        &&(e.getY()>=new_y&&e.getY()<=newGameImage.getHeight()+new_y))
                {
                    hinders[0] = new Hinder(Background.screenSize.width/2);
                    hinders[1] = new Hinder(Background.screenSize.width/2+pipeGap);
                    bird = new Bird();
                    background = new Background();
                    isOver = false;
                    isStart = true;
                    score = 0;
                }
            }
        });
    }
    @Override
    public void paint(Graphics g) {
        //画背景
        g.drawImage(background.getBackground(),background.getX(),background.getY(),null);
        Graphics2D g2d = (Graphics2D)g;
        g2d.rotate(bird.getRatation(),bird.getX()+Bird.birdSize.width/2,bird.getY()+Bird.birdSize.height/2);
        //画小鸟
        g.drawImage(bird.getApprance(),bird.getX(),bird.getY(),null);
        g2d.rotate(-bird.getRatation(),bird.getX()+Bird.birdSize.width/2,bird.getY()+Bird.birdSize.height/2);
        //画钢管
        g.drawImage(hinders[0].getCouplePipe()[0], hinders[0].getX(), hinders[0].getUp_y(),null);
        g.drawImage(hinders[0].getCouplePipe()[1], hinders[0].getX(), hinders[0].getDown_y(),null);
        g.drawImage(hinders[1].getCouplePipe()[0], hinders[1].getX(), hinders[1].getUp_y(),null);
        g.drawImage(hinders[1].getCouplePipe()[1], hinders[1].getX(), hinders[1].getDown_y(),null);
        //画硬币
        if (hinders[0].isHaveCoin()
                &&!getOneCoin)
        {
            g.drawImage(Hinder.getCoin(), hinders[0].getCoin_x(), hinders[0].getCoin_y(),null);
        }
        if (hinders[1].isHaveCoin()
                &&!getTwoCoin)
        {
            g.drawImage(Hinder.getCoin(), hinders[1].getCoin_x(), hinders[1].getCoin_y(),null);
        }
        //画得分
        if (!isOver)
        {
            g.setFont(new Font("TimesRoman",Font.BOLD,20));
            g.setColor(Color.blue);
            g.drawString("coin:",0,30);
            g.drawString("score:",0,70);
            String scoreString = String.valueOf(score);
            String coinString = String.valueOf(coin);
            for (int i = 0;i<coinString.length();i++)
            {
                g.drawImage(numberImages[Character.getNumericValue(coinString.charAt(i))],50+25*i,0,null);
            }
            for (int i = 0;i<scoreString.length();i++)
            {
                g.drawImage(numberImages[Character.getNumericValue(scoreString.charAt(i))],65+25*i,40,null);
            }
        }
        //画地面
        g.drawImage(land.getLandImage(),land.getX(),land.getY(),null);
        //画开始引导界面
        if (!isStart)
        {
            int start_tips_x = (Background.screenSize.width- startTipsImage.getWidth())/2;
            int start_tips_y = (Background.screenSize.height- startTipsImage.getHeight())/2;
            g.drawImage(startTipsImage,start_tips_x, start_tips_y,null);
            start_x = (Background.screenSize.width-playButton.getWidth())/2;
            start_y = start_tips_y+ startTipsImage.getHeight();
            g.drawImage(playButton,start_x,start_y,null);

            g.drawImage(readyTextImage,(Background.screenSize.width- readyTextImage.getWidth())/2,
                    start_tips_y-readyTextImage.getHeight(),null);
        }
        //画结束引导界面
        if (isOver)
        {
            g.drawImage(overImage,(Background.screenSize.width-overImage.getWidth())/2,(Background.screenSize.height-overImage.getHeight())/2,null);
            new_x = (Background.screenSize.width-newGameImage.getWidth())/2;
            new_y = Background.screenSize.height/2+overImage.getHeight()/2;
            g.drawImage(newGameImage,new_x,new_y,null);
            int scorePanel_x = (Background.screenSize.width-scorePanelImage.getWidth())/2;
            int scorePanel_y = (Background.screenSize.height-overImage.getHeight())/2-scorePanelImage.getHeight();
            g.drawImage(scorePanelImage,scorePanel_x,scorePanel_y,null);
            g.setFont(new Font("TimesRoman",Font.BOLD,15));
            g.setColor(Color.blue);
            g.drawString(String.valueOf(score),scorePanel_x+scorePanelImage.getWidth()-60,scorePanel_y+50);
            g.drawString(String.valueOf(best),scorePanel_x+scorePanelImage.getWidth()-60,scorePanel_y+90);
            if (level == 0)
            {
                g.drawImage(bronzeMedal,scorePanel_x+33,scorePanel_y+43,null);
            }
            else if (level == 1)
            {
                g.drawImage(silverMedal,scorePanel_x+33,scorePanel_y+43,null);
            }
            else
            {
                g.drawImage(goldMedal,scorePanel_x+33,scorePanel_y+43,null);
            }
        }
    }

    /**
     * 玩家通过了一定量的钢管，给玩家升级
     */
    public void upLevel()
    {
        score++;
        if (score == 20)
        {
            level ++;
        }
        if (score == 100)
        {
            level ++;
        }
    }

    /**
     * 修改玩家最优成绩
     */
    public void changeBest()
    {
        if (score >best)
        {
            best = score;
        }
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(background.getBackground().getWidth(),background.getBackground().getHeight());
    }
}
