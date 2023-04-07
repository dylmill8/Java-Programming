public class MyThread implements Runnable {
    public void run() {
        Thread toRun = new Thread(new MyThread()); //passing our class to the Java-defined Thread Object
        toRun.start();
        System.out.println("Running!");

        try { // MUST encapsulate in a try-catch block for InterruptedExceptions.
            toRun.join();
        } catch (InterruptedException ie) { ie.printStackTrace(); }
    }
}
