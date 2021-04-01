package com.game.wantingting;

import javax.swing.*;

public class BirdGame {
    GamePanel gamePanel = new GamePanel();
    public BirdGame() {
        init();
        gamePanel.start();
    }
    public void init()
    {
        JFrame jf = new JFrame("Bird Game");
        jf.add(gamePanel);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);

    }

    public static void main(String[] args) {
        new BirdGame();
    }

}
