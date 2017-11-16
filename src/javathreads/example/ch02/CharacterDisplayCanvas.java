package javathreads.example.ch02;

import javax.swing.*;
import java.awt.*;

public class CharacterDisplayCanvas extends JComponent implements CharacterListener {
    protected char[] tmpChar = new char[1];
    protected FontMetrics fm;
    protected int fontHeight;

    public CharacterDisplayCanvas() {
        setFont(new Font("Monospaced", Font.BOLD, 18));
        fm = getFontMetrics(getFont());
        fontHeight = fm.getHeight();
    }

    public CharacterDisplayCanvas(CharacterSource cs) {
        this();
        setCharacterSource(cs);
    }

    public void setCharacterSource(CharacterSource cs) {
        cs.addCharacterListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        System.out.println(Thread.currentThread().getName() + ": " + fm.getMaxAscent() + " " + fm.getMaxAdvance());
        return new Dimension(fm.getMaxAscent() + 10, fm.getMaxAdvance() + 50);
    }

    /*
    newCharacter()方法会被RandomCharacterGenerator字母产生器的线程调用
    paintComponent（）方法会被时间派发线程调用
    在这两个方法中，tmpChar会被两个线程中被调用，会产生race condition竞态条件，所有添加同步锁
     */

    @Override
    public synchronized void newCharacter(CharacterEvent ec) {
        tmpChar[0] = (char)ec.character;
        repaint();
    }

    @Override
    protected synchronized void paintComponent(Graphics g) {
        Dimension d = getSize();
        System.out.println(Thread.currentThread().getName() + ": " + "d.widht " + d.width + " d.height " + d.height);
        g.clearRect(0, 0, d.width, d.height);
        if (tmpChar[0] == 0) {
            return;
        }
        g.drawChars(tmpChar, 0, 1, (d.width - fm.charWidth((char)tmpChar[0])) / 2, fontHeight);
    }
}
