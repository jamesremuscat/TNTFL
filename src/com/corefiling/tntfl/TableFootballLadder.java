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

}
