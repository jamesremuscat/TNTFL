package com.corefiling.tntfl;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class LadderEntry {

  private String _name;
  private double _skill;

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

  public static class LadderEntryDeserializer implements JsonDeserializer<LadderEntry> {

    @Override
    public LadderEntry deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {

      final LadderEntry p = new LadderEntry();

      final JsonObject po = json.getAsJsonObject();

      p.setName(po.get("name").getAsString());
      p.setSkill(po.get("skill").getAsDouble());

      return p;
    }

  }

}
