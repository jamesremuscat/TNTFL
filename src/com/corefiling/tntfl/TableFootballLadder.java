package com.corefiling.tntfl;

import java.lang.reflect.Type;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.corefiling.tntfl.network.FakeHttpAccessStrategy;
import com.corefiling.tntfl.network.FullHttpAccessStrategy;
import com.corefiling.tntfl.network.HttpAccessStrategy;
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

  private static final String COREFILING_INTERNAL_WIFI_SSID = "cfl_staff";

  private static final String LADDER_BASE_URL = "http://www.int.corefiling.com/~jrem/tntfl/";

  private static final String LADDER_LADDER_URL = LADDER_BASE_URL + "ladder/json";
  private static final String LADDER_RECENT_URL = LADDER_BASE_URL + "recent/json";
  private static final String LADDER_SUBMIT_URL = LADDER_BASE_URL + "game/add/json";


  private static HttpAccessStrategy _http = null;

  public static void setHttpAccessStrategy(final HttpAccessStrategy strategy) {
    _http = strategy;
  }

  static HttpAccessStrategy getHttpAccessStrategy(final Context context) {

    if (_http == null) {

      final WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
      final String currentSSID = wm.getConnectionInfo().getSSID(); // Ick, this returns a string in double quotes

      if (currentSSID != null && currentSSID.contains(COREFILING_INTERNAL_WIFI_SSID)) {
        _http = new FullHttpAccessStrategy();
      }
      else {
        _http = new FakeHttpAccessStrategy();
        Log.i("TableFootballLadder", "Not connected to a CoreFiling internal network, so cannot communicate with ladder. Using dummy HTTP access.");
      }


    }

    return _http;
  }

  public static SubmittedGame submitGame(final Context context, final Game game) throws SubmissionException {
    return getHttpAccessStrategy(context).postGame(LADDER_SUBMIT_URL, game);
  }

  public static List<SubmittedGame> getRecentGames(final Context context) throws SubmissionException {
    final String jsonResponse = getHttpAccessStrategy(context).get(LADDER_RECENT_URL);

    final GsonBuilder gb = new GsonBuilder();
    gb.registerTypeAdapter(SubmittedGame.class, new SubmittedGame.SubmittedGameDeserializer());

    final Gson g = gb.create();

    return g.fromJson(jsonResponse, new TypeToken<List<SubmittedGame>>(){ /* */ }.getType());
  }

  public static List<LadderEntry> getLadder(final Context context) throws SubmissionException {
    final String jsonResponse = getHttpAccessStrategy(context).get(LADDER_LADDER_URL);
    final GsonBuilder gb = new GsonBuilder();
    gb.registerTypeAdapter(LadderEntry.class, new LadderEntry.LadderEntryDeserializer());
    final Gson g = gb.create();

    return g.fromJson(jsonResponse, new TypeToken<List<LadderEntry>>() { /* */ }.getType());
  }

  public static class SubmissionException extends Exception {

    private static final long serialVersionUID = 2244020040887984376L;

    public SubmissionException(final Exception e) {
      super(e);
    }
    // blah

    public SubmissionException(final String reasonPhrase) {
      super(reasonPhrase);
    }
  }

}
