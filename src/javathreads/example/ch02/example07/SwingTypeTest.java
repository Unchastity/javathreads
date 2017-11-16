package javathreads.example.ch02.example07;

import javathreads.example.ch02.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//@SuppressWarnings("not")
public class SwingTypeTest extends JFrame implements CharacterSource {


    protected RandomCharacterGenerator producer;
    private AnimatedCharacterDisplayCanvas robotCanvas;
    private CharacterDisplayCanvas typeCanvas;
    private JButton quitBtn;
    private JButton startBtn;
    private CharacterEventHandler ceHandler;

    public SwingTypeTest() {
        initComponents();
    }
    private void initComponents() {
        ceHandler = new CharacterEventHandler();
        typeCanvas = new CharacterDisplayCanvas(this);
        robotCanvas = new AnimatedCharacterDisplayCanvas();
        startBtn = new JButton("start");
        quitBtn = new JButton("quit");
        add(robotCanvas, BorderLayout.NORTH);
        add(typeCanvas, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.add(startBtn);
        panel.add(quitBtn);
        add(panel, BorderLayout.SOUTH);

        typeCanvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char c = e.getKeyChar();
                if (c != KeyEvent.CHAR_UNDEFINED) {
                    nextCharacter((int)c);
                }
            }
        });

        quitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                producer.setDone(true);
                robotCanvas.setDone(true);
                typeCanvas.setEnabled(false);
                startBtn.setEnabled(true);
            }
        });

        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //每次开始都新建一个线程
                producer = new RandomCharacterGenerator();
                Thread t = new Thread(producer);
                t.start();
                robotCanvas.setCharacterSource(producer);
                robotCanvas.setDone(false);
                Thread animatedT = new Thread(robotCanvas);
                animatedT.start();
                startBtn.setEnabled(false);
                typeCanvas.setEnabled(true);
                typeCanvas.requestFocus();
            }
        });

        typeCanvas.requestFocus();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void addCharacterListener(CharacterListener cl) {
        ceHandler.addCharacterListener(cl);
    }

    @Override
    public void removeCharacterListener(CharacterListener cl) {
        ceHandler.removeCharacterListener(cl);
    }

    public void nextCharacter(int c) {
        ceHandler.fireNewCharacter(this, c);
    }

    @Override
    public void nextCharacter() {
//        throw new IllegalStateException("We don't produce a char");
    }

    public static void main(String[] args) {

        new SwingTypeTest();
    }

}
