package com.corefiling.tntfl.network;

import com.corefiling.tntfl.Game;
import com.corefiling.tntfl.SubmittedGame;
import com.corefiling.tntfl.TableFootballLadder.SubmissionException;


public class FakeHttpAccessStrategy implements HttpAccessStrategy {

  @Override
  public String get(final String url) {
    if (url.contains("/game/")) {
      return "{\"red\":{\"name\":\"jrem\",\"score\":10},\"blue\":{\"name\":\"aks\",\"score\":0},\"skillChange\":{\"change\":11.38,\"towards\":\"red\"}}";
    }
    else if (url.contains("recent/json")) {
      return "[{\"red\":{\"name\":\"jrem\",\"score\":7,\"skillChange\":2.14038192979},\"blue\":{\"name\":\"esm\",\"score\":3,\"skillChange\":-2.14038192979},\"date\":1392928777},{\"red\":{\"name\":\"jrem\",\"score\":5,\"skillChange\":-0.138448118581},\"blue\":{\"name\":\"cjm\",\"score\":5,\"skillChange\":0.138448118581},\"date\":1392915957}]";
    }
    else if (url.contains("ladder/json")) {
      return "[{ \"name\":\"plega\",\"total\":{ \"for\":6990.0, \"against\":6800.0, \"games\":1399.0 },\"gamesToday\":0.0,\"skill\":75.4809053873,\"weaselFactor\":695.847760159},{ \"name\":\"neg\",\"total\":{ \"for\":2000.0, \"against\":2350.0, \"games\":435.0 },\"gamesToday\":0.0,\"skill\":64.7577655753,\"weaselFactor\":20.9640058033},{ \"name\":\"njlgad\",\"total\":{ \"for\":2151.0, \"against\":1719.0, \"games\":387.0 },\"gamesToday\":1.0,\"skill\":58.7145370014,\"weaselFactor\":229.999140506}]";
    }
    else {
      return "{}";
    }
  }

  @Override
  public SubmittedGame postGame(final String url, final Game game) throws SubmissionException {
    return SubmittedGame.fromJsonString(" { \"red\" : { \"name\" : \"tmm\", \"href\" : \"../../player/tmm/json\", \"score\" : 2, \"skillChange\" : -3.3271082683, \"rankChange\" : 0, \"newRank\" : 10 }, \"blue\" : { \"name\" : \"njlgad\", \"href\" : \"../../player/njlgad/json\", \"score\" : 8, \"skillChange\" : 3.3271082683, \"rankChange\" : 0, \"newRank\" : 2 }, \"positionSwap\" : false, \"date\" : 1430821687}");
  }

}