package com.corefiling.tntfl.network;


public class FakeHttpAccessStrategy implements HttpAccessStrategy {

  @Override
  public String get(final String url) {
    return "{\"red\":{\"name\":\"jrem\",\"score\":10},\"blue\":{\"name\":\"aks\",\"score\":0},\"skillChange\":{\"change\":11.38,\"towards\":\"red\"}}";
  }

}