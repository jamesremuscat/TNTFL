package com.corefiling.tntfl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import junit.framework.TestCase;

import com.corefiling.tntfl.SubmittedGame.SubmittedGameDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class TestSubmittedGame extends TestCase {

  public void testDeserialise() throws Exception {
    final String jsonString = JsonDataTestUtils.sampleDataAsString(getClass(), "submittedGame.json");

    final SubmittedGame game = SubmittedGame.fromJsonString(jsonString);

    assertEquals("jrem", game.getRedPlayer());
    assertEquals(6, game.getRedScore());
    assertEquals("cjm", game.getBluePlayer());
    assertEquals(4, game.getBlueScore());
    assertEquals(0.138448118581f, game.getSkillChange(), 0.000001);
    assertEquals(Player.BLUE, game.getSkillChangeDirection());
  }

  public void testDeserialiseVersion2() throws Exception {
    final String jsonString = JsonDataTestUtils.sampleDataAsString(getClass(), "submittedGameV2.json");

    final SubmittedGame game = SubmittedGame.fromJsonString(jsonString);

    assertEquals("jrem", game.getRedPlayer());
    assertEquals(6, game.getRedScore());
    assertEquals("cjm", game.getBluePlayer());
    assertEquals(4, game.getBlueScore());
    assertEquals(0.138448118581f, game.getSkillChange(), 0.000001);
    assertEquals(Player.BLUE, game.getSkillChangeDirection());
  }

  public void testDeserialiseWithGson() throws Exception {
    final JsonObject jsonString = JsonDataTestUtils.sampleDataAsJson(getClass(), "submittedGameV2.json");

    final GsonBuilder gb = new GsonBuilder();
    gb.registerTypeAdapter(SubmittedGame.class, new SubmittedGameDeserializer());
    final Gson gson = gb.create();

    final SubmittedGame game = gson.fromJson(jsonString, SubmittedGame.class);

    assertEquals("jrem", game.getRedPlayer());
    assertEquals(6, game.getRedScore());
    assertEquals("cjm", game.getBluePlayer());
    assertEquals(4, game.getBlueScore());
    assertEquals(0.138448118581f, game.getSkillChange(), 0.000001);
    assertEquals(Player.BLUE, game.getSkillChangeDirection());

    final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    assertEquals("2014-02-20 17:05:57", df.format(game.getDateTime()));
  }

}
