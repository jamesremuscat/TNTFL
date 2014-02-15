package com.corefiling.tntfl.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.corefiling.tntfl.Game;
import com.corefiling.tntfl.R;
import com.corefiling.tntfl.ui.fragment.NameSelectionFragment;
import com.corefiling.tntfl.ui.fragment.NameSelectionFragment.NameReceiver;

public class ScoreEntryActivity extends FragmentActivity implements NameReceiver {

  private static final String FRAGMENT_TAG = "fragment";
  private State _state = State.NEED_RED_NAME;
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

    switch (_state) {
      case NEED_RED_NAME:
      case NEED_BLUE_NAME:
        transaction.replace(R.id.fragmentHolder, new NameSelectionFragment(), FRAGMENT_TAG);
        break;
      default:
        transaction.remove(fm.findFragmentByTag(FRAGMENT_TAG));
    }

    transaction.commit();
  }

  private static enum State {
    NEED_RED_NAME,
    NEED_RED_SCORE,
    NEED_BLUE_NAME,
    NEED_BLUE_SCORE,
    READY_TO_SUBMIT
  }

  @Override
  public void setName(final String name) {
    switch (_state) {
      case NEED_RED_NAME:
        _game.setRedPlayer(name);
        _state = State.NEED_RED_SCORE;
        break;
      case NEED_BLUE_NAME:
        _game.setBluePlayer(name);
        _state = State.NEED_BLUE_SCORE;
        break;
      default:
        throw new IllegalStateException("Tried to add a name while we were in " + _state);
    }
    layoutAsPerState();
  }

}
