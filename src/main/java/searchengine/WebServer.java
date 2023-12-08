package searchengine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * The {@code WebServer} class represents a simple web server that can handle
 * HTTP requests.
 * It provides methods for starting and stopping the server, as well as handling
 * incoming requests.
 *
 * @author Jiali Zhang
 * @version 1.0
 */

public class WebServer {
  static final int PORT = 8080;
  static final int BACKLOG = 0;
  static final Charset CHARSET = StandardCharsets.UTF_8;
  FileManager fileManager;
  HttpServer server;

  /**
   * read file, and put them into pages.
   * 
   * @param filename
   * @throws IOException
   */
  public WebServer(String filename) throws IOException {
    fileManager = new FileManager(filename);
    server = HttpServer.create(new InetSocketAddress(PORT), BACKLOG);

    server.createContext("/", io -> respond(io, 200, "text/html", fileManager.getFile("web/index.html")));
    server.createContext("/search", io -> search(io));
    server.createContext(
        "/favicon.ico", io -> respond(io, 200, "image/x-icon", fileManager.getFile("web/favicon.ico")));
    server.createContext(
        "/code.js", io -> respond(io, 200, "application/javascript", fileManager.getFile("web/code.js")));
    server.createContext(
        "/style.css", io -> respond(io, 200, "text/css", fileManager.getFile("web/style.css")));
    server.start();
    String msg = " WebServer running on http://localhost:" + PORT + " ";
    System.out.println("╭" + "─".repeat(msg.length()) + "╮");
    System.out.println("│" + msg + "│");
    System.out.println("╰" + "─".repeat(msg.length()) + "╯");
  }

  /**
   * search method is to get the searchTerm, then filter the result by searchTerm.
   */
  void search(HttpExchange io) {
    var searchTerm = io.getRequestURI().getRawQuery().split("=")[1];

    var bytes = new SearchService()
        .handleRequest(searchTerm, fileManager.getInvertedIndex(), fileManager.getQuantityOfPages()).toString()
        .getBytes(CHARSET);
    respond(io, 200, "application/json", bytes);
  }

  void respond(HttpExchange io, int code, String mime, byte[] response) {
    try {
      io.getResponseHeaders()
          .set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
      io.sendResponseHeaders(200, response.length);
      io.getResponseBody().write(response);
    } catch (Exception e) {
    } finally {
      io.close();
    }
  }
}