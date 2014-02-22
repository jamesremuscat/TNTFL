package com.corefiling.tntfl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.test.InstrumentationTestCase;

public class TestTableFootballLadder extends InstrumentationTestCase {

  @Override
  public void setUp() throws Exception {
    // *facepalm* https://code.google.com/p/dexmaker/issues/detail?id=2
    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());
  }

  public void testAddRecentPlayer() throws Exception {
    final Context mockContext = mock(Context.class);
    final SharedPreferences mockPrefs = mock(SharedPreferences.class);
    when(mockContext.getSharedPreferences("ladder", Context.MODE_PRIVATE)).thenReturn(mockPrefs);
    when(mockPrefs.getString(eq("recentPlayers"), anyString())).thenReturn("[]");

    final Editor mockEditor = mock(Editor.class);
    when(mockPrefs.edit()).thenReturn(mockEditor);

    TableFootballLadder.addRecentPlayer(mockContext, "testPlayer");

    verify(mockPrefs).getString(eq("recentPlayers"), anyString());
    verify(mockPrefs).edit();
    verify(mockEditor).putString("recentPlayers", "[\"testPlayer\"]");
    verify(mockEditor).commit();
  }

  public void testGetRecentPlayers() throws Exception {
    final Context mockContext = mock(Context.class);
    final SharedPreferences mockPrefs = mock(SharedPreferences.class);
    when(mockContext.getSharedPreferences("ladder", Context.MODE_PRIVATE)).thenReturn(mockPrefs);
    when(mockPrefs.getString(eq("recentPlayers"), anyString())).thenReturn("[\"testPlayer1\",\"testPlayer2\"]");

    final List<String> recentPlayers = TableFootballLadder.getRecentPlayers(mockContext);

    verify(mockPrefs).getString(eq("recentPlayers"), anyString());
    assertEquals(2, recentPlayers.size());
    assertEquals("testPlayer1", recentPlayers.get(0));
    assertEquals("testPlayer2", recentPlayers.get(1));
  }

}
