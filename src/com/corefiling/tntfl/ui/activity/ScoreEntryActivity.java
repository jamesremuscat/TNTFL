package com.corefiling.tntfl.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.corefiling.tntfl.R;

public class ScoreEntryActivity extends Activity {

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_score_entry);
  }

  @Override
  public boolean onCreateOptionsMenu(final Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.score_entry, menu);
    return true;
  }

}
