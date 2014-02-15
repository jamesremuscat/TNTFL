package com.corefiling.tntfl.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.corefiling.tntfl.Game;
import com.corefiling.tntfl.Player;
import com.corefiling.tntfl.R;
import com.corefiling.tntfl.ui.fragment.NameAndScoreFragment;
import com.corefiling.tntfl.ui.fragment.NameAndScoreFragment.RetryReceiver;
import com.corefiling.tntfl.ui.fragment.NameSelectionFragment;
import com.corefiling.tntfl.ui.fragment.NameSelectionFragment.NameReceiver;
import com.corefiling.tntfl.ui.fragment.ScoreSelectionFragment;
import com.corefiling.tntfl.ui.fragment.ScoreSelectionFragment.ScoreReceiver;

public class ScoreEntryActivity extends FragmentActivity implements NameReceiver, ScoreReceiver, RetryReceiver {

  private static final String RED_FRAGMENT_TAG = "redFragment";
  private static final String BLUE_FRAGMENT_TAG = "blueFragment";
  private static final int SCORE_SUBMISSION_REQUEST_CODE = 1138;

  private State _redState = State.NEED_NAME;
  private State _blueState = State.NEED_NAME;
  private Game _game;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_score_entry);

    _game = new Game();

    ((Button) findViewById(R.id.btnSubmit)).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(final View v) {
        final Intent i = new Intent(getApplicationContext(), ScoreSubmissionActivity.class);
        final Bundle b = new Bundle();
        b.putParcelable(ScoreSubmissionActivity.BUNDLE_GAME_KEY, _game);
        i.putExtras(b);
        startActivityForResult(i, SCORE_SUBMISSION_REQUEST_CODE);
      }
    });

    layoutAsPerState();

    final View decorView = getWindow().getDecorView();
    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
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
        ssf.setPlayerName(_game.getRedPlayer());
        transaction.replace(R.id.redFragmentHolder, ssf, RED_FRAGMENT_TAG);
        break;
      case READY_TO_SUBMIT:
        final NameAndScoreFragment f = NameAndScoreFragment.getInstance(Player.RED, _game.getRedPlayer(), _game.getRedScore());
        transaction.replace(R.id.redFragmentHolder, f, RED_FRAGMENT_TAG);
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
        ssf.setPlayerName(_game.getBluePlayer());
        transaction.replace(R.id.blueFragmentHolder, ssf, BLUE_FRAGMENT_TAG);
        break;
      case READY_TO_SUBMIT:
        final NameAndScoreFragment f = NameAndScoreFragment.getInstance(Player.BLUE, _game.getBluePlayer(), _game.getBlueScore());
        transaction.replace(R.id.blueFragmentHolder, f, BLUE_FRAGMENT_TAG);
        break;
    }

    transaction.commit();

    if (_blueState == State.READY_TO_SUBMIT && _redState == State.READY_TO_SUBMIT) {
      findViewById(R.id.btnSubmit).setVisibility(View.VISIBLE);
    } else {
      findViewById(R.id.btnSubmit).setVisibility(View.INVISIBLE);
    }
  }

  @Override
  protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
    if (requestCode == SCORE_SUBMISSION_REQUEST_CODE && resultCode == RESULT_OK) {
      recreate();
    }
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

  @Override
  public void retry(final Player player) {
    switch(player) {
      case RED:
        _redState = State.NEED_NAME;
        break;
      case BLUE:
        _blueState = State.NEED_NAME;
        break;
    }
    layoutAsPerState();
  }

}
