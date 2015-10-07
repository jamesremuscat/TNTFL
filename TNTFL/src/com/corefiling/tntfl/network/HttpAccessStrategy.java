package com.corefiling.tntfl.network;

import com.corefiling.tntfl.Game;
import com.corefiling.tntfl.SubmittedGame;
import com.corefiling.tntfl.TableFootballLadder.SubmissionException;

public interface HttpAccessStrategy {
  public String get(final String url) throws SubmissionException;
  public SubmittedGame postGame(final String url, final Game game) throws SubmissionException;
}