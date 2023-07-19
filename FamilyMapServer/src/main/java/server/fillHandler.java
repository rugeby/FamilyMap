package server;

import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import dataAccess.DataAccessException;
import request.FillRequest;
import result.FillResult;
import service.FillService;

public class fillHandler implements HttpHandler {
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    boolean success = false;

    ///fill/susan
    try {
      if (exchange.getRequestMethod().toLowerCase().equals("post")) {
        String[] params = exchange.getRequestURI().toString().split("/");
        assert params.length >= 3;
        //get username
        ///fill/susan/3(length = 4)
        String username = params[2];
        int generation = 4;
        //grab generation and set it
        if (params.length > 3) {
          generation = Integer.parseInt(params[3]);
        }

        //call fill service
        FillRequest request = new FillRequest(username, generation);
        FillService service = new FillService();
        FillResult result = service.fill(request);

        //send response back
        Gson gson = new Gson();
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream resBody = exchange.getResponseBody();
        String resData = gson.toJson(result);
        writeString(resData, resBody);
        resBody.close();

        success = true;

      }
      if (!success) {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        // not sending any response body
        exchange.getResponseBody().close();
      }
    } catch (Exception e) {
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
