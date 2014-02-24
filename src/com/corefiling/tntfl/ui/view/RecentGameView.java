package com.corefiling.tntfl.ui.view;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corefiling.tntfl.Player;
import com.corefiling.tntfl.R;
import com.corefiling.tntfl.SubmittedGame;

public class RecentGameView extends LinearLayout {

  public RecentGameView(final Context context) {
    this(context, (AttributeSet) null);
  }

  public RecentGameView(final Context context, final SubmittedGame game) {
    this(context, (AttributeSet) null);
    setGame(game);
  }

  public RecentGameView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    inflater.inflate(R.layout.view_recent_game, this, true);
  }

  @SuppressWarnings("deprecation")
  public void setGame(final SubmittedGame game) {
    ((TextView) findViewById(R.id.txtBlueName)).setText(game.getBluePlayer());
    ((TextView) findViewById(R.id.txtBlueScore)).setText(Integer.toString(game.getBlueScore()));
    ((TextView) findViewById(R.id.txtRedName)).setText(game.getRedPlayer());
    ((TextView) findViewById(R.id.txtRedScore)).setText(Integer.toString(game.getRedScore()));

    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm", Locale.ENGLISH);

    ((TextView) findViewById(R.id.txtDate)).setText(dateFormat.format(game.getDateTime()));

    final TextView txtSkillChange = (TextView) findViewById(R.id.txtSkillChange);
    txtSkillChange.setText(String.format("%s: %.3f", getResources().getString(R.string.skill), game.getSkillChange()));

    if (game.getSkillChangeDirection() == Player.RED) {
      txtSkillChange.setBackgroundResource(R.drawable.red_gradient);
    }
    else {
      txtSkillChange.setBackgroundResource(R.drawable.blue_gradient);
    }

    if (game.getRedScore() == 10 && game.getBlueScore() == 0 || game.getRedScore() == 0 && game.getBlueScore() == 10) {
      final Drawable yellowStripe = new GradientDrawable((game.getRedScore() == 10) ? Orientation.LEFT_RIGHT : Orientation.RIGHT_LEFT, new int[] {Color.YELLOW, Color.BLACK});
      findViewById(R.id.scoresBox).setBackgroundDrawable(yellowStripe);
    }
  }

}
