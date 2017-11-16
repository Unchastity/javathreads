package javathreads.example.ch02.example02;

import javathreads.example.ch02.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SwingTypeTest extends JFrame implements CharacterSource {

    protected RandomCharacterGenerator producer;
    private CharacterDisplayCanvas robotCanvas;
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
        robotCanvas = new CharacterDisplayCanvas();
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
            @Override
            public void actionPerformed(ActionEvent e) {
//                quit();
                if (producer.isAlive()) {
//                    producer.setDone(true);
                    producer.interrupt();
                }
                if (producer.isInterrupted()) {
                    //!produce.isAlive()
                    producer = null;
                    typeCanvas.setEnabled(false);
                    startBtn.setEnabled(true);
                }
            }
        });
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //每次开始都新建一个线程
                producer = new RandomCharacterGenerator();
                robotCanvas.setCharacterSource(producer);
                producer.start();
                startBtn.setEnabled(false);
                typeCanvas.setEnabled(true);
                typeCanvas.requestFocus();
            }
        });

//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                quit();
//            }
//        });

        typeCanvas.requestFocus();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void quit() {
        System.exit(0);
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
