import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This program runs a search server. When a client connects, the server will read in their search term and check the
 * file database for the term, returning the results to the client.
 *
 * PORT #4242
 *
 * @author Dylan Miller
 * @version November 4, 2022
 */

public class SearchServer {
    public static void main(String[] args) {
        File file = new File("searchDatabase.txt");
        try {
            ServerSocket serverSocket = new ServerSocket(4242);

            while (true) {
                System.out.println("CLOSED");
                Socket socket = serverSocket.accept();
                while (true) {
                    System.out.println("OPEN");
                    PrintWriter writer = new PrintWriter(socket.getOutputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    if (reader.read() == -1) {
                        System.out.println("TRUE");
                        break;
                    }

                    String search = reader.readLine();

                    if (search != null) {
                        BufferedReader fileReader = new BufferedReader(new FileReader(file));
                        String line = fileReader.readLine();
                        ArrayList<String> titles = new ArrayList<>();

                        while (line != null) {
                            String[] item = line.split(";");
                            if (item[2].contains(search) || item[1].contains(search)) {
                                titles.add(item[1]);
                            }
                            line = fileReader.readLine();
                        }
                        fileReader.close();

                        for (String i : titles) {
                            writer.write(i + ";");
                        }
                        writer.println();
                        writer.flush();

                        String selection = reader.readLine();
                        if (selection != null && !selection.isEmpty()) {
                            fileReader = new BufferedReader(new FileReader(file));
                            line = fileReader.readLine();
                            while (line != null) {
                                String[] item = line.split(";");
                                if (item[1].equalsIgnoreCase(selection)) {
                                    writer.write(item[2]);
                                    writer.println();
                                    writer.flush();
                                }
                                line = fileReader.readLine();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
