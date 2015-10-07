package com.corefiling.tntfl.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.corefiling.tntfl.R;
import com.corefiling.tntfl.ui.fragment.LadderFragment;
import com.corefiling.tntfl.ui.fragment.RecentGamesFragment;

public class RecentGamesActivity extends FragmentActivity {

  @Override
  protected void onCreate(final Bundle bundle) {
    super.onCreate(bundle);
    setContentView(R.layout.activity_recent_games);

    showRecentGames();

    final Button btnEnterGame = (Button) findViewById(R.id.btnEnterGame);
    btnEnterGame.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(final View v) {
        final Intent i = new Intent(RecentGamesActivity.this, ScoreEntryActivity.class);
        startActivityForResult(i, 1139);
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    showRecentGames();
  }

  protected void showRecentGames() {
    final FragmentManager fm = getSupportFragmentManager();
    final FragmentTransaction transaction = fm.beginTransaction();

    transaction.replace(R.id.fragmentHolderLeft, new LadderFragment());
    transaction.replace(R.id.fragmentHolderRight, new RecentGamesFragment());

    transaction.commit();
  }

}
