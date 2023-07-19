package server;


import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import request.RegisterRequest;
import result.RegisterResult;
import service.RegisterService;

class userRegisterHandler implements HttpHandler {

  // Handles HTTP requests containing the "/games/list" URL path.
  // The "exchange" parameter is an HttpExchange object, which is
  // defined by Java.
  // In this context, an "exchange" is an HTTP request/response pair
  // (i.e., the client and server exchange a request and response).
  // The HttpExchange object gives the handler access to all of the
  // details of the HTTP request (Request type [GET or POST],
  // request headers, request body, etc.).
  // The HttpExchange object also gives the handler the ability
  // to construct an HTTP response and send it back to the client
  // (Status code, headers, response body, etc.).
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    boolean success = false;

    try {
      if (exchange.getRequestMethod().toLowerCase().equals("post")) {
        Headers reqHeaders  = exchange.getRequestHeaders();
        InputStream reqBody = exchange.getRequestBody();

        String reqData = readString(reqBody);

        Gson gson = new Gson();
        RegisterRequest request  = (RegisterRequest)gson.fromJson(reqData, RegisterRequest.class);
        RegisterService service = new RegisterService();
        assert request != null : "request is null";
        RegisterResult result = service.register(request);

        success = result.isSuccess();
        if(success){
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        }
        else{
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        }

        OutputStream resBody = exchange.getResponseBody();
        String resData = gson.toJson(result);
        writeString(resData, resBody);
        resBody.close();

      }
      else{
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        exchange.getResponseBody().close();

      }

    }
    catch (Exception e) {
      // Some kind of internal error has occurred inside the server (not the
      // client's fault), so we return an "internal server error" status code
      // to the client.
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      // Since the server is unable to complete the request, the client will
      // not receive the list of games, so we close the response body output stream,
      // indicating that the response is complete.
      exchange.getResponseBody().close();

      // Display/log the stack trace
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