package server;

import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import request.LoadRequest;
import result.LoadResult;
import service.LoadService;

public class loadHandler implements HttpHandler{
  @Override
  public void handle(HttpExchange exchange)throws IOException{
    try{
      if(exchange.getRequestMethod().toLowerCase().equals("post")){
        InputStream reqBody = exchange.getRequestBody();

        // Read JSON string from the input stream
        String reqData = readString(reqBody);


        Gson gson = new Gson();
        LoadRequest request = (LoadRequest)gson.fromJson(reqData, LoadRequest.class);
        // Display/log the request JSON data
        LoadService service = new LoadService();
        assert request != null : "request is null";
        LoadResult result = service.load(request);

        if(result.isSuccess()){
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        }
        else{
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        }

        OutputStream resBody = exchange.getResponseBody();

        String gsonFormResult = gson.toJson(result);


        //put result string in resp body
        writeString(gsonFormResult, resBody);

        resBody.close();
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
