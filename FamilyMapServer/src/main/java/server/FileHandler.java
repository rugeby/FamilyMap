package server;

import java.io.*;
import java.net.*;
import java.nio.file.Files;

import com.sun.net.httpserver.*;
public class FileHandler implements HttpHandler{
  @Override
  public void handle(HttpExchange exchange) throws IOException{
    boolean success = false;

    try{
      if(exchange.getRequestMethod().toLowerCase().equals("get")){
        String urlPath = exchange.getRequestURI().toString();

        if(urlPath == null || urlPath.equals("")||urlPath.equals("/")){
          urlPath = "/index.html";
        }
        String filePath = "web" + urlPath;
        System.out.println("The request is: "+ filePath);

        OutputStream resBody = exchange.getResponseBody();
        File file = new File(filePath);
        if(!file.exists()){
          // return 404 if the file does not exist
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
          // define a new file path which contains the 404 page
          filePath = "web/HTML/404.html";
          file = new File(filePath);
          // send the 404 page to the response body
          Files.copy(file.toPath(), resBody);
        }
        else{
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
          // copies a file to an output stream
          Files.copy(file.toPath(), resBody);
        }
        resBody.close();
        success = true;
      }
      if(!success){
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        exchange.getResponseBody().close();
      }

    }catch (IOException e){
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      e.printStackTrace();
      exchange.getResponseBody().close();

    }


  }

//

}
