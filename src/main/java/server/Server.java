package server;

import config.ConnConf;
import config.ServerConf;
import io.Output;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * This class represents the server side of the application.
 * It listens for incoming connections and handles them.
 *
 * @version 1.0
 * @author Jonas Birkeli
 * @since 3.06.2024
 */
public class Server {
  private final ExecutorService threadPool;

  /**
   * Private to prevent instantiation outside the singleton pattern.
   *
   * @since 1.0
   */
  public Server() {
    this.threadPool = Executors.newFixedThreadPool(ServerConf.THREAD_POOL_SIZE);
  }

  /**
   * Starts the server and listens for incoming connections.
   *
   * @since 1.0
   */
  public void start() {
    Output.println("Server started on port " + ConnConf.PORT + ".");

    while (true) {
      try (ServerSocket serverSocket = new ServerSocket(ConnConf.PORT)) {
        Output.println("Waiting for connection...");
        Socket sock = serverSocket.accept();
        Output.println("Connection established to " + sock.getInetAddress() + ".");
        threadPool.execute(new ConnectionHandler(sock));

      } catch (IOException e) {
        Logger.getLogger(Server.class.getName()).severe("Could not accept connection.");
      }
    }

  }

}
