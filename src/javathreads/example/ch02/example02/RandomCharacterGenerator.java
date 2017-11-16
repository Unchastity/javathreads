package javathreads.example.ch02.example02;

import javathreads.example.ch02.CharacterEventHandler;
import javathreads.example.ch02.CharacterListener;
import javathreads.example.ch02.CharacterSource;

import java.util.Random;

public class RandomCharacterGenerator extends Thread implements CharacterSource {

    CharacterEventHandler ceHandler;
    static char[] chars;
    static String charStr = "abcdefghijklmnopqrstuvwxyz0123456789";
    Random random;

    private volatile boolean done = false;

    static {
        chars = charStr.toCharArray();
    }
    //共有两个源，各自管理自己的监听器
    public RandomCharacterGenerator() {
        ceHandler = new CharacterEventHandler();
        random = new Random();
    }

    public int getPauseTime() {
        return (int)(Math.max(1000, 5000 * random.nextDouble()));
    }

    public void setDone(boolean b) {
        this.done = b;
    }

    @Override
    public void addCharacterListener(CharacterListener cl) {
        ceHandler.addCharacterListener(cl);
    }

    @Override
    public void removeCharacterListener(CharacterListener cl) {
        ceHandler.removeCharacterListener(cl);
    }

    @Override
    public void nextCharacter() {
        ceHandler.fireNewCharacter(this, (int)chars[random.nextInt(chars.length)]);
    }

    //使用thread.interrupt()会导致三种情况
    //1. 刚执行完!isInterrupted()检查，且在sleep之前，此时isInterruped返回true，但已检查完，而且没有Interrupt异常，程序会正常
    //执行，sleep后，再次检查!isInterrupted()后结束线程；
    //2. 在sleep期间执行thread.interrupt()，立刻抛出异常，结束线程；
    //3. 刚好sleep完，执行thread.interrupt()，执行!isInterrupted()检查后结束线程。

    @Override
    public void run() {
        //!done
        while (!isInterrupted()) {
            nextCharacter();
            try {
                Thread.sleep(getPauseTime());
            }catch (InterruptedException e) {
                return;
            }
        }
        System.out.println("end");
    }
}
