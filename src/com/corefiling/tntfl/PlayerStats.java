package com.corefiling.tntfl;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class PlayerStats {

  private String _name;
  private double _skill;
  private double _weaselFactor;

  private int _gamesToday;
  private int _totalGames;
  private int _for;
  private int _against;

  public String getName() {
    return _name;
  }
  public void setName(final String name) {
    _name = name;
  }
  public double getSkill() {
    return _skill;
  }
  public void setSkill(final double skill) {
    _skill = skill;
  }
  public double getWeaselFactor() {
    return _weaselFactor;
  }
  public void setWeaselFactor(final double weaselFactor) {
    _weaselFactor = weaselFactor;
  }

  public int getGamesToday() {
    return _gamesToday;
  }

  public void setGamesToday(final int gamesToday) {
    _gamesToday = gamesToday;
  }

  public int getTotalGames() {
    return _totalGames;
  }

  public void setTotalGames(final int totalGames) {
    _totalGames = totalGames;
  }

  public int getFor() {
    return _for;
  }

  public void setFor(final int for1) {
    _for = for1;
  }

  public int getAgainst() {
    return _against;
  }

  public void setAgainst(final int against) {
    _against = against;
  }

  public static class PlayerStatsDeserializer implements JsonDeserializer<PlayerStats> {

    @Override
    public PlayerStats deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {

      final PlayerStats p = new PlayerStats();

      final JsonObject po = json.getAsJsonObject();

      p.setName(po.get("name").getAsString());
      p.setGamesToday(po.get("gamesToday").getAsInt());
      p.setSkill(po.get("skill").getAsDouble());
      p.setWeaselFactor(po.get("weaselFactor").getAsDouble());

      final JsonObject totals = po.get("total").getAsJsonObject();
      p.setFor(totals.get("for").getAsInt());
      p.setAgainst(totals.get("against").getAsInt());
      p.setTotalGames(totals.get("games").getAsInt());

      return p;
    }

  }

}
