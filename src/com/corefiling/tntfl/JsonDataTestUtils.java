package com.corefiling.tntfl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.Assert;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public final class JsonDataTestUtils {

  private JsonDataTestUtils() {
    // Utility class; thou shalt not construct
  }

  public static String sampleDataAsString(final Class<?> clazz, final String name) throws IOException {
    final InputStream stream = clazz.getResourceAsStream("data/" + name);

    if (stream == null) {
      Assert.fail("Could not find resource 'data/" + name + "' for test class " + clazz.getCanonicalName());
    }

    final StringBuffer sb = new StringBuffer();

    final BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
    for (int c = br.read(); c != -1; c = br.read()) {
      sb.append((char)c);
    }

    return sb.toString();
  }

  public static JsonObject sampleDataAsJson(final Class<?> clazz, final String name) throws IOException {
    final String json = sampleDataAsString(clazz, name);

    final JsonParser p = new JsonParser();
    final JsonObject o = p.parse(json).getAsJsonObject();
    return o;
  }

}