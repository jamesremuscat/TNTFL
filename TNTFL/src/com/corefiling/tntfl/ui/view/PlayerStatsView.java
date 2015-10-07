package com.corefiling.tntfl.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corefiling.tntfl.LadderEntry;
import com.corefiling.tntfl.R;

public class PlayerStatsView extends LinearLayout {

  public PlayerStatsView(final Context context) {
    this(context, (AttributeSet) null);
  }

  public PlayerStatsView(final Context context, final int ranking, final LadderEntry playerStats) {
    this(context, (AttributeSet) null);
    setRanking(ranking);
    setPlayerStats(playerStats);
  }

  public PlayerStatsView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    inflater.inflate(R.layout.view_player_stats, this, true);
  }

  @SuppressWarnings("deprecation")
  public void setRanking(final int ranking) {
    final TextView txtRanking = (TextView) findViewById(R.id.txtRanking);
    txtRanking.setText(Integer.toString(ranking));
    if (ranking == 1) {
      txtRanking.setBackgroundDrawable(getResources().getDrawable(R.drawable.ladderposition_first));
    }
    else {
      txtRanking.setBackgroundDrawable(getResources().getDrawable(R.drawable.ladderposition));
    }
  }

  public void setPlayerStats(final LadderEntry playerStats) {
    final TextView txtPlayerName = (TextView) findViewById(R.id.txtPlayerName);
    txtPlayerName.setText(playerStats.getName());

    final TextView txtSkill = (TextView) findViewById(R.id.txtSkill);
    txtSkill.setText(String.format("%.3f", playerStats.getSkill()));

  }

}
