package com.corefiling.tntfl.ui.activity;

import org.mockito.Mockito;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TextView;

import com.corefiling.tntfl.Game;
import com.corefiling.tntfl.JsonDataTestUtils;
import com.corefiling.tntfl.R;
import com.corefiling.tntfl.TableFootballLadder;
import com.corefiling.tntfl.network.HttpAccessStrategy;
import com.corefiling.tntfl.ui.fragment.AsyncFragment;

public class TestScoreSubmissionActivity extends ActivityInstrumentationTestCase2<ScoreSubmissionActivity> {

  public TestScoreSubmissionActivity() {
    super(ScoreSubmissionActivity.class);
  }

  @Override
  protected void tearDown() throws Exception {
    getActivity().finish();
  }

  public void testDisplaysGame() throws Exception {

    final Game game = new Game();

    game.setRedPlayer("redPlayer");
    game.setRedScore(7);
    game.setBluePlayer("bluePlayer");
    game.setBlueScore(3);

    final Intent i = new Intent(getInstrumentation().getContext(), ScoreSubmissionActivity.class);
    final Bundle b = new Bundle();
    b.putParcelable(ScoreSubmissionActivity.BUNDLE_GAME_KEY, game);
    i.putExtras(b);
    setActivityIntent(i);

    final HttpAccessStrategy http = Mockito.mock(HttpAccessStrategy.class);
    TableFootballLadder.setHttpAccessStrategy(http);

    Mockito.when(http.get(Mockito.anyString())).thenReturn(JsonDataTestUtils.sampleDataAsString(getClass(), "submittedGame.json"));

    final ScoreSubmissionActivity activity = getActivity();
    waitForLoad(activity);

    assertEquals("redPlayer", ((TextView) activity.findViewById(R.id.txtRedName)).getText());
    assertEquals("bluePlayer", ((TextView) activity.findViewById(R.id.txtBlueName)).getText());
    assertEquals("7", ((TextView) activity.findViewById(R.id.txtRedScore)).getText());
    assertEquals("3", ((TextView) activity.findViewById(R.id.txtBlueScore)).getText());
    final TextView txtSkillChange = (TextView) activity.findViewById(R.id.txtSkillChangeRed);
    assertEquals("+13.124", txtSkillChange.getText());
    final TextView txtSkillChangeBlue = (TextView) activity.findViewById(R.id.txtSkillChangeBlue);
    assertEquals(View.INVISIBLE, txtSkillChangeBlue.getVisibility());
  }

  protected void waitForLoad(final Activity activity) throws InterruptedException {
    if (activity != null) {
      while (activity.findViewById(AsyncFragment.INTERNAL_PROGRESS_CONTAINER_ID) == null) {
        Thread.sleep(100);
      }
      while (activity.findViewById(AsyncFragment.INTERNAL_PROGRESS_CONTAINER_ID).getVisibility() == View.VISIBLE) {
        Thread.sleep(100);
      }
    }
    Thread.sleep(500); // Pause for rendering
  }

}
