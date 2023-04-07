import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.BrokenBarrierException;

public class ThreadSpawner {

    public static void main(String[] args) {
        try (
                var socket = new ServerSocket(10101);
        ) {
            while (true) {
                Server.barrier.reset();
                var listenerThread = new Thread(new Server(socket));
                listenerThread.start();

                try {
                    Server.barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}