package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * This class represents the write handler for the client.
 * It reads messages from the user and sends them to the server.
 *
 * @version 1.0
 * @author Jonas Birkeli
 * @since 3.06.2024
 */
public class WriteHandler implements Runnable {
  private final Socket socket;
  private PrintWriter writer;
  private BufferedReader reader;

  /**
   * Constructor for the write handler.
   * It initializes the input and output streams.
   *
   * @param socket The socket to handle the connection.
   * @since 1.0
   */
  public WriteHandler(Socket socket) {
    this.socket = socket;

    try {
      writer = new PrintWriter(socket.getOutputStream(), true);
      reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    } catch (Exception e) {
      Logger.getLogger(WriteHandler.class.getName()).severe("Could not get input stream." + e);
    }
  }

  /**
   * Runs this operation.
   * It reads messages from the user and sends them to the server.
   *
   * @since 1.0
   */
  @Override
  public void run() {
    String message;

    try {
      while ((message = reader.readLine()) != null) {
        writer.println(message);

        if (message.equals("exit")) {
          break;
        }
      }
    } catch (Exception e) {
      Logger.getLogger(WriteHandler.class.getName()).severe("Could not read message." + e);
    }
  }
}
