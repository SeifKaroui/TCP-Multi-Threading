import java.io.*;
import java.net.*;

public class TCPClient {

  public static void main(String[] args) {
    try {
      // Connect 
      Socket socket = new Socket("localhost", 12345);
      PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      // Send message
      String input = "Hello, world!";
      writer.println(input);

      // Receive response 
      String response = reader.readLine();
      System.out.println("Server response: " + response);

    } catch (IOException e) {

      e.printStackTrace();
    }
  }

}
