package com.corefiling.tntfl;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class SubmittedGame extends Game {

  private float _skillChange;
  private Player _skillChangeDirection;
  private Date _dateTime;

  private int _redRankChange = 0;
  private int _blueRankChange = 0;

  public int getRedRankChange() {
    return _redRankChange;
  }
  public void setRedRankChange(final int redRankChange) {
    _redRankChange = redRankChange;
  }
  public int getBlueRankChange() {
    return _blueRankChange;
  }
  public void setBlueRankChange(final int blueRankChange) {
    _blueRankChange = blueRankChange;
  }
  public float getSkillChange() {
    return _skillChange;
  }
  public void setSkillChange(final float skillChange) {
    _skillChange = skillChange;
  }
  public Player getSkillChangeDirection() {
    return _skillChangeDirection;
  }
  public void setSkillChangeDirection(final Player skillChangeDirection) {
    _skillChangeDirection = skillChangeDirection;
  }
  public Date getDateTime() {
    return _dateTime;
  }
  public void setDateTime(final Date dateTime) {
    _dateTime = dateTime;
  }

  public static SubmittedGame fromJsonString(final String json) {

    final JsonParser p = new JsonParser();
    final JsonObject o = p.parse(json).getAsJsonObject();

    return fromJsonObject(o);
  }

  protected static SubmittedGame fromJsonObject(final JsonObject o) {
    final SubmittedGame g = new SubmittedGame();
    final JsonObject redPlayer = o.get("red").getAsJsonObject();
    g.setRedPlayer(redPlayer.get("name").getAsString());
    g.setRedScore(redPlayer.get("score").getAsInt());

    if (redPlayer.has("rankChange")) {
      g.setRedRankChange(redPlayer.get("rankChange").getAsInt());
    }

    final JsonObject bluePlayer = o.get("blue").getAsJsonObject();
    g.setBluePlayer(bluePlayer.get("name").getAsString());
    g.setBlueScore(bluePlayer.get("score").getAsInt());

    if (bluePlayer.has("rankChange")) {
      g.setBlueRankChange(bluePlayer.get("rankChange").getAsInt());
    }

    if (o.has("skillChange")) { // nicer version of JSON
      final JsonObject skill = o.get("skillChange").getAsJsonObject();
      g.setSkillChange(skill.get("change").getAsFloat());

      final String towards = skill.get("towards").getAsString();
      if ("red".equalsIgnoreCase(towards)) {
        g.setSkillChangeDirection(Player.RED);
      }
      else if ("blue".equalsIgnoreCase(towards)) {
        g.setSkillChangeDirection(Player.BLUE);
      }
    }
    else if (redPlayer.has("skillChange")) { // older version of JSON
      final float redSkill = redPlayer.get("skillChange").getAsFloat();
      final float blueSkill = bluePlayer.get("skillChange").getAsFloat();
      if (redSkill > 0) {
        g.setSkillChangeDirection(Player.RED);
        g.setSkillChange(redSkill);
      }
      else if (blueSkill > 0) {
        g.setSkillChangeDirection(Player.BLUE);
        g.setSkillChange(blueSkill);
      }

    }

    if (o.has("date")) {
      g.setDateTime(new Date(o.get("date").getAsLong() * 1000));
    }
    else {
      g.setDateTime(new Date());
    }


    return g;
  }

  public static class SubmittedGameDeserializer implements JsonDeserializer<SubmittedGame> {
    @Override
    public SubmittedGame deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
      return SubmittedGame.fromJsonObject(json.getAsJsonObject());
    }

  }

}
