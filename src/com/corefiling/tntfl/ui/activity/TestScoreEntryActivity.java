package com.corefiling.tntfl.ui.activity;

import java.util.Locale;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.corefiling.tntfl.R;
import com.corefiling.tntfl.TableFootballLadder;
import com.corefiling.tntfl.network.FakeHttpAccessStrategy;

public class TestScoreEntryActivity extends ActivityInstrumentationTestCase2<ScoreEntryActivity> {

  public TestScoreEntryActivity() {
    super(ScoreEntryActivity.class);
  }

  @Override
  protected void setUp() throws Exception {
    TableFootballLadder.setHttpAccessStrategy(new FakeHttpAccessStrategy());
  }

  public void testEnterScores() {
    final ScoreEntryActivity activity = getActivity();

    final View redFragmentHolder = activity.findViewById(R.id.redFragmentHolder);
    final View blueFragmentHolder = activity.findViewById(R.id.blueFragmentHolder);

    final EditText txtRedName = (EditText) redFragmentHolder.findViewById(R.id.txtName);
    final EditText txtBlueName = (EditText) blueFragmentHolder.findViewById(R.id.txtName);

    focus(txtRedName);

    sendKeys("J R E M");

    focus(txtBlueName);

    sendKeys("A K S");

    assertEquals("jrem", txtRedName.getText().toString().toLowerCase(Locale.ENGLISH));
    assertEquals("aks", txtBlueName.getText().toString().toLowerCase(Locale.ENGLISH));

    click(redFragmentHolder.findViewById(R.id.btnOK));
    click(blueFragmentHolder.findViewById(R.id.btnOK));

    getInstrumentation().waitForIdleSync();

    assertEquals("jrem", ((TextView) redFragmentHolder.findViewById(R.id.txtPlayerName)).getText());
    assertEquals("aks", ((TextView) blueFragmentHolder.findViewById(R.id.txtPlayerName)).getText());

    click(redFragmentHolder.findViewById(R.id.button4));
    click(blueFragmentHolder.findViewById(R.id.button6));

    getInstrumentation().waitForIdleSync();

    final Button btnSubmit = (Button) activity.findViewById(R.id.btnSubmit);
    assertEquals(View.VISIBLE, btnSubmit.getVisibility());
    click(btnSubmit);

  }

  private void focus(final View v) {
    getActivity().runOnUiThread(new Runnable() {
      @Override
      public void run() {
        v.requestFocus();
      }
    });
    getInstrumentation().waitForIdleSync();
  }

  private void click(final View v) {
    getActivity().runOnUiThread(new Runnable() {
      @Override
      public void run() {
        v.performClick();
      }
    });
  }

}
