package server;

import io.Output;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * This class represents the connection handler for the server.
 * It handles the communication between the server and the client.
 *
 * @version 1.0
 * @author Jonas Birkeli
 * @since 3.06.2024
 */
public class ConnectionHandler implements Runnable {
  private Socket socket;
  private BufferedReader reader;
  private PrintWriter writer;

  /**
   * Constructor for the connection handler.
   * It initializes the input and output streams.
   *
   * @since 1.0
   * @param socket The socket to handle the connection.
   * @author Jonas Birkeli
   * @since 4.06.2024
   */
  public ConnectionHandler(Socket socket) {
    this.socket = socket;

    try {
      InputStream input = socket.getInputStream();
      reader = new BufferedReader(new InputStreamReader(input));
      OutputStream output = socket.getOutputStream();
      writer = new PrintWriter(output, true);
    } catch (IOException e) {
      Logger.getLogger(ConnectionHandler.class.getName()).severe("Could not get input stream.");
    }
  }

  /**
   * Runs this operation.
   * It reads messages from the client and sends them back.
   * If the message is "exit", the connection is closed.
   *
   * @since 1.0
   */
  @Override
  public void run() {
    String message;

    try {
      while ((message = reader.readLine()) != null) {
        writer.println("Server: " + message);
        Output.println("Client: " + message);

        // Exit condition
        if (message.equals("exit")) {
          break;
        }
      }
    } catch (IOException e) {
      Logger.getLogger(ConnectionHandler.class.getName()).severe("Could not read from socket.");
    } finally {
      try {
        socket.close();
      } catch (IOException e) {
        Logger.getLogger(ConnectionHandler.class.getName()).severe("Could not close socket.");
      }
    }
  }
}
