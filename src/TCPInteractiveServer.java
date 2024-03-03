// Name : Brandon Vei Liang
// Surname : Lim
// Student id: 1430381

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TCPInteractiveServer {
    private static int counter = 0;
    public static void main(String[] args) {

        ServerSocket listeningSocket = null;
        Socket clientSocket = null;

        // Setting the starting thread pool size to be 5 and allow it to have a maximum of 10 threads
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        executor.setMaximumPoolSize(10);

        try {
            //Create a server socket listening on port 4444
            listeningSocket = new ServerSocket(4444);
            //Listen for incoming connections forever
            while (true) {
                System.out.println("Server listening on port 4444 for a connection");
                //Accept an incoming client connection request
                clientSocket = listeningSocket.accept(); //This method will block until a connection request is received
                counter++;
                System.out.println("Client conection number " + counter + " accepted:");
                System.out.println("Remote Hostname: " + clientSocket.getInetAddress().getHostName());
                System.out.println("Local Port: " + clientSocket.getLocalPort());
                executor.execute(new ServeClient(clientSocket, counter));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(listeningSocket != null)
            {
                try
                {
                    executor.shutdown();
                    listeningSocket.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
