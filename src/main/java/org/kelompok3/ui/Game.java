package org.kelompok3.ui;

import org.kelompok3.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    private int x = 0;
    private int y = 0;
    private Thread thread;
    private boolean running = false;

    Game() {
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        setSize(new Dimension(600, 400));
        repaint();
        System.out.println("(" + super.getWidth() + "x" + super.getHeight() + ")");
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                render();
            }
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS : " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        // left to right
        //x++;
        // up to bottom
        //y++;
    }

    private void render() {
        var bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        var g = bs.getDrawGraphics();
        var img = new BufferedImage(Utils.WIDTH, Utils.HEIGHT, BufferedImage.TYPE_INT_RGB);
        var grap = img.createGraphics();
        // grap.setPaint(new TexturePaint(img, ));
        grap.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, Utils.WIDTH, Utils.HEIGHT);
        g.setColor(Color.white);
        g.fillRect(x, y, 32, 32);
        g.setColor(Color.BLACK);
        g.fillOval(50, (getHeight() / 2) - 150, 700, 300);
        var x = 150;
        for (int i = 0; i < 7; i++) {
            g.setColor(Color.blue);
            g.fillArc(x, 200, 60, 60, 0, 360);
            x += 70;
        }
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Game();
    }
}
