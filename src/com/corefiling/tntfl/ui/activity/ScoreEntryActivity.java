package com.corefiling.tntfl.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.corefiling.tntfl.Game;
import com.corefiling.tntfl.Player;
import com.corefiling.tntfl.R;
import com.corefiling.tntfl.ui.fragment.NameSelectionFragment;
import com.corefiling.tntfl.ui.fragment.NameSelectionFragment.NameReceiver;
import com.corefiling.tntfl.ui.fragment.ScoreSelectionFragment;
import com.corefiling.tntfl.ui.fragment.ScoreSelectionFragment.ScoreReceiver;

public class ScoreEntryActivity extends FragmentActivity implements NameReceiver, ScoreReceiver {

  private static final String RED_FRAGMENT_TAG = "redFragment";
  private static final String BLUE_FRAGMENT_TAG = "blueFragment";
  private State _redState = State.NEED_NAME;
  private State _blueState = State.NEED_NAME;
  private Game _game;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_score_entry);

    _game = new Game();

    layoutAsPerState();

  }

  protected void layoutAsPerState() {
    final FragmentManager fm = getSupportFragmentManager();
    final FragmentTransaction transaction = fm.beginTransaction();

    switch (_redState) {
      case NEED_NAME:
        final NameSelectionFragment nsf = new NameSelectionFragment();
        nsf.setPlayer(Player.RED);
        transaction.replace(R.id.redFragmentHolder, nsf, RED_FRAGMENT_TAG);
        break;
      case NEED_SCORE:
        final ScoreSelectionFragment ssf = new ScoreSelectionFragment();
        ssf.setPlayer(Player.RED);
        transaction.replace(R.id.redFragmentHolder, ssf, RED_FRAGMENT_TAG);
        break;
    }
    switch (_blueState) {
      case NEED_NAME:
        final NameSelectionFragment nsf = new NameSelectionFragment();
        nsf.setPlayer(Player.BLUE);
        transaction.replace(R.id.blueFragmentHolder, nsf, BLUE_FRAGMENT_TAG);
        break;
      case NEED_SCORE:
        final ScoreSelectionFragment ssf = new ScoreSelectionFragment();
        ssf.setPlayer(Player.BLUE);
        transaction.replace(R.id.blueFragmentHolder, ssf, BLUE_FRAGMENT_TAG);
        break;
    }

    transaction.commit();
  }

  private static enum State {
    NEED_NAME,
    NEED_SCORE,
    READY_TO_SUBMIT
  }

  @Override
  public void setName(final Player player, final String name) {

    switch (player) {
      case RED:
        _game.setRedPlayer(name);
        _redState = State.NEED_SCORE;
        break;
      case BLUE:
        _game.setBluePlayer(name);
        _blueState = State.NEED_SCORE;
        break;
    }
    layoutAsPerState();
  }

  @Override
  public void setScore(final Player player, final int score) {
    switch (player) {
      case RED:
        _game.setRedScore(score);
        _redState = State.READY_TO_SUBMIT;
        break;
      case BLUE:
        _game.setBlueScore(score);
        _blueState = State.READY_TO_SUBMIT;
    }
    layoutAsPerState();
  }

}
