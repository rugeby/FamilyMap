package server;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import request.LoginRequest;
import result.LoginResult;
import service.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dataAccess.DataAccessException;


class userLoginHandler implements HttpHandler{
  @Override
  public void handle(HttpExchange exchange) throws IOException{
    boolean success = false;

    try{
      if(exchange.getRequestMethod().toLowerCase().equals("post")){

            InputStream reqBody = exchange.getRequestBody();

            // Read JSON string from the input stream
            String reqData = readString(reqBody);

            // Display/log the request JSON data
            System.out.println(reqData);


            Gson gson = new Gson();
			LoginRequest request = (LoginRequest)gson.fromJson(reqData, LoginRequest.class);

			LoginService service = new LoginService();
			LoginResult result = service.login(request);
            if(result.isSuccess()){
              exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            else{
              exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }

			OutputStream resBody = exchange.getResponseBody();
			String gsonFormResult = gson.toJson(result);
            writeString(gsonFormResult, resBody);
			resBody.close();

            // Start sending the HTTP response to the client, starting with
            // the status code and any defined headers.

            // We are not sending a response body, so close the response body
            // output stream, indicating that the response is complete.

      }
      else{
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        // not sending any response body
        exchange.getResponseBody().close();
      }
    }
    catch (Exception e){
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
