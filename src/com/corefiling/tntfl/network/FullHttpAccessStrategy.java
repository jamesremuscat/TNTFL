package com.corefiling.tntfl.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.corefiling.tntfl.Game;
import com.corefiling.tntfl.SubmittedGame;
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

  @Override
  public SubmittedGame postGame(final String url, final Game game) throws SubmissionException {
    final HttpClient c = new DefaultHttpClient();
    final HttpPost post = new HttpPost(url);

    try {

      final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
      nameValuePairs.add(new BasicNameValuePair("redPlayer", game.getRedPlayer()));
      nameValuePairs.add(new BasicNameValuePair("bluePlayer", game.getBluePlayer()));
      nameValuePairs.add(new BasicNameValuePair("redScore", Integer.toString(game.getRedScore())));
      nameValuePairs.add(new BasicNameValuePair("blueScore", Integer.toString(game.getBlueScore())));
      post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

      Log.i("FullHttpAccessStrategy", "Submitting game: " + game.toString());

      final HttpResponse response = c.execute(post);

      return SubmittedGame.fromJsonString(EntityUtils.toString(response.getEntity()));
    }
    catch (final Exception e) {
      throw new SubmissionException(e);
    }
  }

}