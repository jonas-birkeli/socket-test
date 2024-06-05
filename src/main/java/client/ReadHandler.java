package client;

import io.Output;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * This class represents the read handler for the client.
 * It reads messages from the server and prints them to the console.
 *
 * @version 1.0
 * @author Jonas Birkeli
 * @since 3.06.2024
 */
public class ReadHandler implements Runnable {
  private final Socket socket;
  private BufferedReader reader;

  /**
   * Constructor for the read handler.
   * It initializes the input stream.
   *
   * @param socket The socket to handle the connection.
   * @since 1.0
   */
  public ReadHandler(Socket socket) {
    this.socket = socket;


  }

  /**
   * Runs this operation.
   *
   * @since 1.0
   */
  @Override
  public void run() {
    try {
      InputStream input = socket.getInputStream();
      reader = new BufferedReader(new InputStreamReader(input));
    } catch (IOException e) {
      Logger.getLogger(ReadHandler.class.getName()).severe("Could not get input stream.");
    }

    String message;
    try {
      while ((message = reader.readLine()) != null) {
        Output.println(message);

        if (message.equals("exit")) {
          break;
        }
      }
    } catch (IOException e) {
      Logger.getLogger(ReadHandler.class.getName()).severe("Could not read message." + e);
    }
  }
}
