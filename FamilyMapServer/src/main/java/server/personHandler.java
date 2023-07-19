package server;

import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import request.PersonRequest;
import result.PersonIDResult;
import result.PersonResult;
import service.PersonIDService;
import service.PersonService;

public class personHandler implements HttpHandler{
  @Override
  public void handle(HttpExchange exchange)throws IOException{
    try{
      if(exchange.getRequestMethod().toLowerCase().equals("get")){
        Headers reqHeaders = exchange.getRequestHeaders();
        if(reqHeaders.containsKey("Authorization")){
          String authToken = reqHeaders.getFirst("Authorization");
          String requestURL = exchange.getRequestURI().toString();
          String[] params = requestURL.split("/");

          OutputStream resBody = exchange.getResponseBody();
          Gson gson = new Gson();

          if(params.length == 2){
            PersonService service = new PersonService();
            PersonRequest request = new PersonRequest(authToken);
            PersonResult result = service.person(request);
            if(result.isSuccess()){
              exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            else{
              exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }

            String gsonFormResult = gson.toJson(result);
            writeString(gsonFormResult, resBody);
            resBody.close();

          }
          if(params.length == 3){
            String personID = params[2];
            PersonIDService service = new PersonIDService();
            PersonRequest request = new PersonRequest(authToken, personID);
            PersonIDResult resultID = service.personID(request);
            if(resultID.isSuccess()){
              exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            else{
              exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
            String gsonFormResult = gson.toJson(resultID);
            writeString(gsonFormResult, resBody);
            resBody.close();

          }



        }
      }
      else{
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        // not sending any response body
        exchange.getResponseBody().close();
      }

    }catch (Exception e){
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
