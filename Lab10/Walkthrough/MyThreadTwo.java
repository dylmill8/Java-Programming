public class MyThreadTwo extends Thread {
    public void Run() {
        MyThreadTwo toRun = new MyThreadTwo();
        toRun.start();
        System.out.println("Running!");

        try {
            toRun.join();
        } catch (InterruptedException ie) { ie.printStackTrace(); }
    }
}
