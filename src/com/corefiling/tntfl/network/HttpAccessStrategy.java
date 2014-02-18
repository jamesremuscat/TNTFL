package com.corefiling.tntfl.network;

import com.corefiling.tntfl.TableFootballLadder.SubmissionException;

public interface HttpAccessStrategy {
  public String get(final String url) throws SubmissionException;
}