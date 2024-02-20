import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class TCPServer {

  public static void main(String[] args) {
    // Thread pool of 10
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    try {

      ServerSocket serverSocket = new ServerSocket(12345);
      System.out.println("Server Socket opened");
      //
      while (true) {
        Socket socket = serverSocket.accept();

        System.out.println("New connection");
        // execute on new thread
        executorService.submit(new ClientHandler(socket));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      executorService.shutdown();
    }
  }

  private static class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
      this.socket = socket;
    }

    @Override
    public void run() {
      try {

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

        // Read message
        String input = reader.readLine();

        // Sleep
        Thread.sleep(1000);

        // Reverse message
        String reversed = new StringBuilder(input).reverse().toString();

        // Send message
        writer.println("Reversed: " + reversed);

      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      } finally {
        try {


          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

    }
  }

}
