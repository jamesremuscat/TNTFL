package com.corefiling.tntfl;

import java.util.Date;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SubmittedGame extends Game {

  private float _skillChange;
  private Player _skillChangeDirection;
  private Date _dateTime;

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

  public static SubmittedGame fromJson(final String json) {
    final SubmittedGame g = new SubmittedGame();

    final JsonParser p = new JsonParser();
    final JsonObject o = p.parse(json).getAsJsonObject();

    final JsonObject redPlayer = o.get("red").getAsJsonObject();
    g.setRedPlayer(redPlayer.get("name").getAsString());
    g.setRedScore(redPlayer.get("score").getAsInt());

    final JsonObject bluePlayer = o.get("blue").getAsJsonObject();
    g.setBluePlayer(bluePlayer.get("name").getAsString());
    g.setBlueScore(bluePlayer.get("score").getAsInt());

    final JsonObject skill = o.get("skillChange").getAsJsonObject();
    g.setSkillChange(skill.get("change").getAsFloat());

    final String towards = skill.get("towards").getAsString();
    if ("red".equalsIgnoreCase(towards)) {
      g.setSkillChangeDirection(Player.RED);
    }
    else if ("blue".equalsIgnoreCase(towards)) {
      g.setSkillChangeDirection(Player.BLUE);
    }

    g.setDateTime(new Date());

    return g;
  }

}
