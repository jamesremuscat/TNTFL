package com.corefiling.tntfl.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.corefiling.tntfl.Game;
import com.corefiling.tntfl.R;
import com.corefiling.tntfl.ui.fragment.ScoreSubmissionFragment;
import com.corefiling.tntfl.ui.fragment.ScoreSubmissionFragment.Dismissable;

public class ScoreSubmissionActivity extends FragmentActivity implements Dismissable {

  public static final String BUNDLE_GAME_KEY = "game";

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_score_submit);

    final Bundle extras = getIntent().getExtras();
    final Game game = extras.getParcelable(BUNDLE_GAME_KEY);

    final FragmentManager fm = getSupportFragmentManager();
    final FragmentTransaction transaction = fm.beginTransaction();

    transaction.add(R.id.fragmentHolder, ScoreSubmissionFragment.getInstance(game));

    transaction.commit();

  }

  @Override
  public void dismiss() {
    finish();
  }

}
