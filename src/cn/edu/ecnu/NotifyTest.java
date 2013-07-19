package cn.edu.ecnu;

import java.util.concurrent.atomic.AtomicInteger;

public class NotifyTest {
    //private String flag[] = { "true" };

    AtomicInteger count = new AtomicInteger(5);
    
    class NotifyThread extends Thread {
        public NotifyThread(String name) {
            super(name);
        }

        public void run() {
            try {
                sleep(3000);
                
                System.out.println(getName()+" is running");
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (count) {
                /*flag[0] = "false";
                flag.notifyAll();*/
                
                count.decrementAndGet();
                count.notify();
            }
        }
    };

    /*class WaitThread extends Thread {
        public WaitThread(String name) {
            super(name);
        }

        
    }*/
    
    public void run() {
        for (int i = 0; i < 5; i++)
        {
            NotifyThread notifyThread = new NotifyThread("notify"+i);
            notifyThread.start();
        }
        
        
        synchronized (count) {
            long waitTime = System.currentTimeMillis();
            while (count.get() != 0) {
                //System.out.println(getName() + " begin waiting!");
                
                try {
                    count.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
            }
            waitTime = System.currentTimeMillis() - waitTime;
            System.out.println("wait time :" + waitTime);
            //System.out.println(getName() + " end waiting!");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main Thread Run!");
        NotifyTest test = new NotifyTest();
        
        test.run();
        /*NotifyThread notifyThread = test.new NotifyThread("notify01");
        WaitThread waitThread01 = test.new WaitThread("waiter01");
        WaitThread waitThread02 = test.new WaitThread("waiter02");
        WaitThread waitThread03 = test.new WaitThread("waiter03");
        notifyThread.start();
        waitThread01.start();
        waitThread02.start();
        waitThread03.start();*/
    }

}