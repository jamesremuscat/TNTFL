package com.corefiling.tntfl.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.corefiling.tntfl.TableFootballLadder.SubmissionException;

public class FullHttpAccessStrategy implements HttpAccessStrategy {

  @Override
  public String get(final String url) throws SubmissionException {
    final HttpClient httpclient = new DefaultHttpClient();
    HttpResponse response;
    try {
      response = httpclient.execute(new HttpGet(url));
      final StatusLine statusLine = response.getStatusLine();
      if(statusLine.getStatusCode() == HttpStatus.SC_OK){
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.getEntity().writeTo(out);
        out.close();
        return out.toString();
      } else {
        response.getEntity().getContent().close();
        throw new SubmissionException(statusLine.getReasonPhrase());
      }
    }
    catch (final IOException e) {
      throw new SubmissionException(e);
    }
  }

}