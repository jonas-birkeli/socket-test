package app;

import client.Client;
import config.ConnConf;
import io.Output;
import java.net.UnknownHostException;
import server.Server;

public class Main {
  public static void main(String[] args) {

    if (args.length < 1) {
      Output.println("Usage: 'java app.Main <parameter>', where <parameter> is either 'server' or 'client'.");
      System.exit(1);
    }
    Output.println("Starting " + args[0] + "...");

    if (args[0].equals("server")) {
      Server server = new Server();
      server.start();
    } else if (args[0].equals("client")) {
      Client client = new Client();
      try {
        client.connect();
      } catch (UnknownHostException e) {
        Output.println("Could not connect to server with ip: " + ConnConf.HOST + " and port: " + ConnConf.PORT + ".");
      }
    } else {
      Output.println("Usage: 'java app.Main <parameter>', where <parameter> is either 'server' or 'client'.");
      System.exit(1);
    }
  }

}
