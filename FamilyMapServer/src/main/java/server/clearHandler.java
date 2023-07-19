package server;

import java.io.*;
import java.net.*;


import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import dataAccess.DataAccessException;
import result.ClearResult;
import service.ClearService;
import dataAccess.DataAccessException;

class clearHandler implements HttpHandler{
  @Override
  public void handle(HttpExchange exchange)throws IOException{
    boolean success = false;
    try{
      if(exchange.getRequestMethod().toLowerCase().equals("post")){
        //step 1; create relative service&result
        //call relative service
        ClearService service  = new ClearService();
        ClearResult result = service.clear();

        //step2, send response back;
        if(result.isSuccess()){
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        }
        else{
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        }
        OutputStream resBody = exchange.getResponseBody();

        //create new gson, conervt gson result to String;
        Gson gson = new Gson();

        //convert result to string
        String gsonFormResult = gson.toJson(result);


        //put result string in resp body
        writeString(gsonFormResult, resBody);

        resBody.close();

      }


    } catch (Exception e){
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
