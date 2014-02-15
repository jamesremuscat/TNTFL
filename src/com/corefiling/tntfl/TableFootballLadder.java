package com.corefiling.tntfl;

import java.lang.reflect.Type;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class TableFootballLadder {

  private static final String JSON_EMPTY_LIST = "[]";
  private static final String RECENT_PLAYERS = "recentPlayers";
  private static final String PREFERENCES_KEY = "ladder";

  public static List<String> getRecentPlayers(final Context context) {
    final SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    final String value = prefs.getString(RECENT_PLAYERS, JSON_EMPTY_LIST);

    final GsonBuilder gsonb = new GsonBuilder();
    final Gson gson = gsonb.create();

    final Type listOfString = new TypeToken<List<String>>() { /* la la la */ }.getType();
    final List<String> list = gson.fromJson(value, listOfString);
    return list;
  }

  public static void addRecentPlayer(final Context context, final String player) {
    if (player.isEmpty()) {
      return;
    }
    final List<String> recentPlayers = getRecentPlayers(context);
    recentPlayers.remove(player);
    recentPlayers.add(0, player); // put them at top of list

    final GsonBuilder gsonb = new GsonBuilder();
    final Gson gson = gsonb.create();
    final String value = gson.toJson(recentPlayers);
    final SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    final Editor e = prefs.edit();
    e.putString(RECENT_PLAYERS, value);
    e.commit();
  }

  private static final String LADDER_SUBMIT_URL = "http://www.int.corefiling.com/~aks/football/ladderEdit.cgi?jsonResponse=true&";

  private static HttpAccessStrategy _http = new FakeHttpAccessStrategy();

  public static void setHttpAccessStrategy(final HttpAccessStrategy strategy) {
    _http = strategy;
  }

  public static SubmittedGame submitGame(final Game game) {

    final StringBuilder urlBuilder = new StringBuilder(LADDER_SUBMIT_URL);
    urlBuilder.append("redplayer=");
    urlBuilder.append(game.getRedPlayer());
    urlBuilder.append("&");
    urlBuilder.append("redscore=");
    urlBuilder.append(game.getRedScore());
    urlBuilder.append("&");
    urlBuilder.append("blueplayer=");
    urlBuilder.append(game.getBluePlayer());
    urlBuilder.append("&");
    urlBuilder.append("bluescore=");
    urlBuilder.append(game.getBlueScore());

    final String url = urlBuilder.toString();

    final String jsonResponse = _http.get(url);

    return SubmittedGame.fromJson(jsonResponse);
  }

  public static interface HttpAccessStrategy {
    public String get(final String url);
  }

  public static class FakeHttpAccessStrategy implements HttpAccessStrategy {

    @Override
    public String get(final String url) {
      return "{\"red\":{\"name\":\"jrem\",\"score\":10},\"blue\":{\"name\":\"aks\",\"score\":0},\"skillChange\":{\"change\":11.38,\"towards\":\"red\"}}";
    }

  }

}
