package server;

import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import request.EventRequest;
import result.EventIDResult;
import result.EventResult;
import service.EventIDService;
import service.EventService;

public class eventHandler implements HttpHandler{
  public void handle(HttpExchange exchange) throws IOException{
    try{
      if(exchange.getRequestMethod().toLowerCase().equals("get")){
        Headers reqHeaders = exchange.getRequestHeaders();
        if(reqHeaders.containsKey("Authorization")){
          String authToken = reqHeaders.getFirst("Authorization");
          // get the URI string: /event/eventID or /event
          String[] params = exchange.getRequestURI().toString().split("/");


          if(params.length == 2){

            //event
            EventService service = new EventService();
            EventRequest request = new EventRequest(authToken);
            EventResult result = service.event(request);
            if(result.isSuccess()){
              exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }else{
              exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
           OutputStream resBody = exchange.getResponseBody();
            Gson gson = new Gson();
            String resData = gson.toJson(result);
            writeString(resData, resBody);
           resBody.close();


          }
          if(params.length == 3){
            //event/eventID
            String eventID = params[2];
            EventIDService service = new EventIDService();
            EventRequest request = new EventRequest(authToken, eventID);
            EventIDResult result = service.eventID(request);
            if(result.isSuccess()){
              exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }else{
              exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }

            OutputStream resBody = exchange.getResponseBody();
            Gson gson = new Gson();
            String resData = gson.toJson(result);
            writeString(resData, resBody);
            resBody.close();

          }

        }
      }
      else{
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        // not sending any response body
        exchange.getResponseBody().close();
      }


    }
    catch(Exception e){
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      exchange.getResponseBody().close();
      e.printStackTrace();

    }


  }
  private String readString(InputStream is) throws IOException {
    StringBuilder sb = new StringBuilder();
    InputStreamReader sr = new InputStreamReader(is);
    char[] buf = new char[1024];
    int len;
    while ((len = sr.read(buf)) > 0) {
      sb.append(buf, 0, len);
    }
    return sb.toString();
  }
  private void writeString(String str, OutputStream os) throws IOException {
    OutputStreamWriter sw = new OutputStreamWriter(os);
    sw.write(str);
    sw.flush();
  }
}
