package com.corefiling.tntfl.network;


public class FakeHttpAccessStrategy implements HttpAccessStrategy {

  @Override
  public String get(final String url) {
    if (url.contains("football.cgi")) {
      return "{\"red\":{\"name\":\"jrem\",\"score\":10},\"blue\":{\"name\":\"aks\",\"score\":0},\"skillChange\":{\"change\":11.38,\"towards\":\"red\"}}";
    }
    else if (url.contains("mode=recent")) {
      return "[{\"red\":{\"name\":\"jrem\",\"score\":7,\"skillChange\":2.14038192979},\"blue\":{\"name\":\"esm\",\"score\":3,\"skillChange\":-2.14038192979},\"date\":1392928777},{\"red\":{\"name\":\"jrem\",\"score\":5,\"skillChange\":-0.138448118581},\"blue\":{\"name\":\"cjm\",\"score\":5,\"skillChange\":0.138448118581},\"date\":1392915957}]";
    }
    else {
      return "{}";
    }
  }

}