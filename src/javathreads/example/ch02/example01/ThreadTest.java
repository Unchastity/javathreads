package javathreads.example.ch02.example01;

public class ThreadTest {

    public static void main(String[] args) {

        /*
        Thread thread1 = new InheritThread("thread1");
        Thread thread2 = new InheritThread("thread2");
        Thread thread3 = new InheritThread("thread3");
        thread1.start();
        thread2.start();
        thread3.start();
        */

        /*
        ImplRunable runnable1 = new ImplRunable();
        ImplRunable runnable2 = new ImplRunable();
        ImplRunable runnable3 = new ImplRunable();
        Thread thread1 = new Thread(runnable1, "thread1");
        Thread thread2 = new Thread(runnable2, "thread2");
        Thread thread3 = new Thread(runnable3, "thread3");
        thread1.start();
        thread2.start();
        thread3.start();

        try {
            Thread.sleep(10000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        runnable1.setDone(true);
        try {
            Thread.sleep(10000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        runnable2.setDone(true);
        try {
            Thread.sleep(10000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        runnable3.setDone(true);
        */

        ImplRunable runnable1 = new ImplRunable();
        Thread thread1 = new Thread(runnable1, "thread1");
        Thread thread2 = new Thread(runnable1, "thread2");
        Thread thread3 = new Thread(runnable1, "thread3");
        thread1.start();
        thread2.start();
        thread3.start();

        try {
            Thread.sleep(10000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        runnable1.setDone(true);
        try {
            Thread.sleep(10000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        runnable1.setDone(true);
        try {
            Thread.sleep(10000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        runnable1.setDone(true);
    }
}

class InheritThread extends Thread{
    private boolean done = false;
    private int cnt = 0;

    public InheritThread(String name) {
        super(name);
    }

    public void setDone(boolean b) {
        done = b;
    }

    @Override
    public void run() {
        while (!done) {
            System.out.println(Thread.currentThread().getName() + ": " + cnt++);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ImplRunable implements Runnable{

    private boolean done = false;
    private int cnt = 0;

    public void setDone(boolean b) {
        done = b;
    }

    @Override
    public void run() {
        while (!done) {
            System.out.println(Thread.currentThread().getName() + ": " + cnt++);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}