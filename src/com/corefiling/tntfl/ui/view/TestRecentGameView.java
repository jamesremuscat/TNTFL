package com.corefiling.tntfl.ui.view;

import java.util.Calendar;

import junit.framework.TestCase;

import com.corefiling.tntfl.ui.view.RecentGameView.GameDateFormatter;

public class TestRecentGameView extends TestCase {

  public void testDateFormatting() {
    final Calendar c = Calendar.getInstance();

    assertTrue(GameDateFormatter.format(c.getTime()).matches("[0-9]{2}:[0-9]{2}"));

    c.add(Calendar.DATE, -1);
    assertTrue(GameDateFormatter.format(c.getTime()).matches("[A-Za-z]{3} [0-9]{2}:[0-9]{2}"));

    c.add(Calendar.DATE, -5); // 6 days ago
    assertTrue(GameDateFormatter.format(c.getTime()).matches("[A-Za-z]{3} [0-9]{2}:[0-9]{2}"));
    c.add(Calendar.DATE, -1); // 7 days ago
    assertTrue(GameDateFormatter.format(c.getTime()).matches("[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}"));
    c.add(Calendar.DATE, -1); // 8 days ago
    assertTrue(GameDateFormatter.format(c.getTime()).matches("[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}"));
  }

}
