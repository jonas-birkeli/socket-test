package client;

import static config.ConnConf.HOST;
import static config.ConnConf.PORT;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * This class represents the client side of the application.
 * It connects to the server and starts the read and write handlers.
 *
 * @version 1.0
 * @author Jonas Birkeli
 * @since 3.06.2024
 */
public class Client {

  /**
   * Connects to the server and starts the read and write handlers.
   *
   * @since 1.0
   */
  public void connect() throws UnknownHostException {

    try (Socket socket = new Socket(HOST, PORT)) {
      new Thread(new ReadHandler(socket)).start();
      new Thread(new WriteHandler(socket)).start();

    } catch (IOException e) {
      Logger.getLogger(Client.class.getName()).severe("Could not connect to server." + e);
      Logger.getLogger(Client.class.getName()).severe("ip: " + HOST + " port: " + PORT);
      throw new UnknownHostException("Could not find host." + e);
    }
  }


}
