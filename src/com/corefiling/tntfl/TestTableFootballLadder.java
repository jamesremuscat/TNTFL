package com.corefiling.tntfl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.mockito.Mockito;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.test.InstrumentationTestCase;

import com.corefiling.tntfl.network.FakeHttpAccessStrategy;
import com.corefiling.tntfl.network.FullHttpAccessStrategy;
import com.corefiling.tntfl.network.HttpAccessStrategy;

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

  public void testGetHttpConnections() throws Exception {
    final Context mockContext = mock(Context.class);
    final WifiManager mockWM = mock(WifiManager.class);
    final WifiInfo mockWI = mock(WifiInfo.class);

    when(mockContext.getSystemService(Context.WIFI_SERVICE)).thenReturn(mockWM);
    when(mockWM.getConnectionInfo()).thenReturn(mockWI);

    when(mockWI.getSSID()).thenReturn("cfl_staff", "some_other_network", "cfl_staff_bmr");
    final HttpAccessStrategy cflStaffStrategy = TableFootballLadder.getHttpAccessStrategy(mockContext);
    assertEquals(FullHttpAccessStrategy.class, cflStaffStrategy.getClass());
    TableFootballLadder.setHttpAccessStrategy(null);

    final HttpAccessStrategy someOtherStrategy = TableFootballLadder.getHttpAccessStrategy(mockContext);
    assertEquals(FakeHttpAccessStrategy.class, someOtherStrategy.getClass());
    TableFootballLadder.setHttpAccessStrategy(null);

    final HttpAccessStrategy cflStaffBMRStrategy = TableFootballLadder.getHttpAccessStrategy(mockContext);
    assertEquals(FullHttpAccessStrategy.class, cflStaffBMRStrategy.getClass());
  }

  public void testSubmitGame() throws Exception {
    final Context mockContext = mock(Context.class);
    final HttpAccessStrategy http = mock(HttpAccessStrategy.class);
    TableFootballLadder.setHttpAccessStrategy(http);

    when(http.get(anyString())).thenReturn(JsonDataTestUtils.sampleDataAsString(getClass(), "submittedGame.json"));

    final Game g = new Game();
    g.setBluePlayer("bluePlayer");
    g.setBlueScore(3);
    g.setRedPlayer("redPlayer");
    g.setRedScore(7);

    TableFootballLadder.submitGame(mockContext, g);

    verify(http).get("http://www.int.corefiling.com/~aks/football/football.cgi?jsonResponse=true&redplayer=redPlayer&redscore=7&blueplayer=bluePlayer&bluescore=3");
    Mockito.verifyZeroInteractions(mockContext);
  }

  public void testGetRecentGames() throws Exception {
    final Context mockContext = mock(Context.class);
    final HttpAccessStrategy http = mock(HttpAccessStrategy.class);
    TableFootballLadder.setHttpAccessStrategy(http);

    when(http.get(anyString())).thenReturn(JsonDataTestUtils.sampleDataAsString(getClass(), "recentGames.json"));

    final List<SubmittedGame> recentGames = TableFootballLadder.getRecentGames(mockContext);

    verify(http).get("http://www.int.corefiling.com/~aks/football/rest.cgi?mode=recent");
    Mockito.verifyZeroInteractions(mockContext);

    assertEquals(5, recentGames.size());

    final SubmittedGame g = recentGames.get(3);

    assertEquals("gjhw", g.getRedPlayer());
    assertEquals(3, g.getRedScore());
    assertEquals("eu", g.getBluePlayer());
    assertEquals(7, g.getBlueScore());
    assertEquals(1.4787261566, g.getSkillChange(), 0.000001);
    assertEquals(Player.BLUE, g.getSkillChangeDirection());
  }

  public void testGetLadder() throws Exception {
    final Context mockContext = mock(Context.class);
    final HttpAccessStrategy http = mock(HttpAccessStrategy.class);
    TableFootballLadder.setHttpAccessStrategy(http);

    when(http.get(anyString())).thenReturn(JsonDataTestUtils.sampleDataAsString(getClass(), "ladder.json"));

    final List<LadderEntry> ladder = TableFootballLadder.getLadder(mockContext);

    verify(http).get("http://www.int.corefiling.com/~aks/football/rest.cgi?mode=ladder");
    Mockito.verifyZeroInteractions(mockContext);

    assertEquals(31, ladder.size());

    final LadderEntry topPlayer = ladder.get(0);
    assertEquals("plega", topPlayer.getName()); // If this invariant is ever violated the company may cease trading
    assertEquals(75.4809053873, topPlayer.getSkill(), 0.0000000001);

    final LadderEntry jrem = ladder.get(3);
    assertEquals("jrem", jrem.getName());

  }

}