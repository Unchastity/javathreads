package javathreads.example.ch02.example07;

import javathreads.example.ch02.CharacterDisplayCanvas;
import javathreads.example.ch02.CharacterEvent;
import javathreads.example.ch02.CharacterSource;

import java.awt.*;

public class AnimatedCharacterDisplayCanvas extends CharacterDisplayCanvas implements Runnable {

    private volatile boolean done = false;
    private int offsetX = -10;
    private int centerX = 0;

    public AnimatedCharacterDisplayCanvas() {
    }

    public AnimatedCharacterDisplayCanvas(CharacterSource cs) {
        super(cs);
    }

    @Override
    public synchronized void newCharacter(CharacterEvent ec) {
        tmpChar[0] = (char)ec.character;
        centerX = (getSize().width - fm.charWidth(tmpChar[0])) / 2;
        offsetX = -10;
        repaint();
    }

    @Override
    protected synchronized void paintComponent(Graphics g) {
        Dimension d = getSize();
        g.clearRect(0, 0, d.width, d.height);
        if (tmpChar[0] == 0) {
            return;
        }
        g.drawChars(tmpChar, 0, 1, centerX + offsetX++, fontHeight);
    }

    @Override
    public void run() {
        while (!done) {
            repaint();
            try{
                Thread.sleep(100);
            }catch (InterruptedException e) {
                return;
            }
        }
    }

    public void setDone(boolean b) {
        this.done = b;
    }
}
