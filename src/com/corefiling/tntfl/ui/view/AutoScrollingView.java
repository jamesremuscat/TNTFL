package com.corefiling.tntfl.ui.view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class AutoScrollingView extends ScrollView {

  private static final int SCROLL_AMOUNT = 5;
  private static final int SCROLL_INTERVAL = 50;
  private static final int SCROLL_DELAY = 2500;
  private Timer _timer;

  public AutoScrollingView(final Context context) {
    this(context, null);
  }

  public AutoScrollingView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
  }

  public void startScrolling() {
    _timer = new Timer();
    final TimerTask tt = new ScrollTimerTask();
    _timer.scheduleAtFixedRate(tt, SCROLL_DELAY, SCROLL_INTERVAL);
  }

  public void stopScrolling() {
    _timer.cancel();
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    stopScrolling();
  }


  private final class ScrollTimerTask extends TimerTask {

    private int direction = 1;

    @Override
    public void run() {
      post(new Runnable() {
        @Override
        public void run() {
          smoothScrollBy(0, SCROLL_AMOUNT * direction);
          if (getScrollY() + getHeight() == getChildAt(0).getHeight()) {
            direction = -1;
          }
          else if (getScrollY() == 0) {
            direction = 1;
          }
        }
      });
    }
  }


}
