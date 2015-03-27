package com.corefiling.tntfl.ui.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corefiling.tntfl.Player;
import com.corefiling.tntfl.R;
import com.corefiling.tntfl.SubmittedGame;

public class RecentGameView extends LinearLayout {

  private Date _dateTime = null;

  private final Runnable _runner = new Runnable() {
    // This bit lifted from Android's TextClock
    @Override
    public void run() {
      updateTime();

      final long now = SystemClock.uptimeMillis();
      final long next = now + (1000 - now % 1000);

      getHandler().postAtTime(_runner, next);
    }
  };

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

  public void setGame(final SubmittedGame game) {
    ((TextView) findViewById(R.id.txtBlueName)).setText(game.getBluePlayer());
    ((TextView) findViewById(R.id.txtBlueScore)).setText(Integer.toString(game.getBlueScore()));
    ((TextView) findViewById(R.id.txtRedName)).setText(game.getRedPlayer());
    ((TextView) findViewById(R.id.txtRedScore)).setText(Integer.toString(game.getRedScore()));

    _dateTime = game.getDateTime();
    updateTime();

    final TextView txtSkillChangeRed = (TextView) findViewById(R.id.txtSkillChangeRed);
    final TextView txtSkillChangeBlue = (TextView) findViewById(R.id.txtSkillChangeBlue);



    if (game.getSkillChangeDirection() == Player.RED) {
      txtSkillChangeRed.setText(String.format("+%.3f", game.getSkillChange()));
      txtSkillChangeRed.setVisibility(VISIBLE);
      txtSkillChangeBlue.setVisibility(INVISIBLE);
    }
    else {
      txtSkillChangeBlue.setText(String.format("+%.3f", game.getSkillChange()));
      txtSkillChangeBlue.setVisibility(VISIBLE);
      txtSkillChangeRed.setVisibility(INVISIBLE);
    }

    //    if (game.getRedScore() == 10 && game.getBlueScore() == 0 || game.getRedScore() == 0 && game.getBlueScore() == 10) {
    //      final Drawable yellowStripe = new GradientDrawable((game.getRedScore() == 10) ? Orientation.LEFT_RIGHT : Orientation.RIGHT_LEFT, new int[] {Color.YELLOW, Color.BLACK});
    //      findViewById(R.id.scoresBox).setBackgroundDrawable(yellowStripe);
    //    }
    //    else {
    //      findViewById(R.id.scoresBox).setBackgroundDrawable(getResources().getDrawable(R.drawable.red_blue_gradient));
    //    }
  }

  protected void updateTime() {
    if (_dateTime != null) {
      ((TextView) findViewById(R.id.txtDate)).setText(GameDateFormatter.format(_dateTime));
    }
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    _runner.run();
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    getHandler().removeCallbacks(_runner);
  }

  static class GameDateFormatter {

    private static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private static final SimpleDateFormat THIS_WEEK_DATE_FORMAT = new SimpleDateFormat("E HH:mm", Locale.ENGLISH);
    private static final SimpleDateFormat LONG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

    private static final int MILLIS_PER_MINUTE = 1000 * 60;

    public static String format(final Date date) {

      final Calendar c = Calendar.getInstance();

      c.add(Calendar.MINUTE, -1);
      if (date.after(c.getTime())) {
        return "Just now";
      }

      c.add(Calendar.MINUTE, -59);
      if (date.after(c.getTime())) {
        // within the last hour. If anyone's in the office at 2am for a DST switch where this goes wrong, they deserve what they get.
        final long diff = System.currentTimeMillis() - date.getTime();
        final int minutes = Math.round(diff / MILLIS_PER_MINUTE);
        if (minutes == 1) {
          return "1 minute ago";
        }
        else {
          return String.format("%s minutes ago", minutes);
        }

      }

      // set the calendar to start of today
      c.set(Calendar.HOUR_OF_DAY, 0);
      c.set(Calendar.MINUTE, 0);
      c.set(Calendar.SECOND, 0);

      if (date.after(c.getTime())) {
        // today
        return SHORT_DATE_FORMAT.format(date);
      }
      else {
        c.add(Calendar.DATE, -6);
        if (date.after(c.getTime())) {
          // in the last week
          return THIS_WEEK_DATE_FORMAT.format(date);
        }
      }
      return LONG_DATE_FORMAT.format(date);

    }

  }

}
