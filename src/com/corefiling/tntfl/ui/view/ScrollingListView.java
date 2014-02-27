package com.corefiling.tntfl.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public class ScrollingListView extends ListView {

  private static final int SCROLL_START_DELAY = 5000;
  private static final int SCROLL_TIME_IN_MILLIS_PER_ITEM = 1500;

  public ScrollingListView(final Context context) {
    super(context);

    setOnScrollListener(new OnScrollListener() {

      @Override
      public void onScrollStateChanged(final AbsListView view, final int scrollState) {
        // Nothing to do here
      }

      @Override
      public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
        if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 1) {
          final View lastVisibleItem = view.getChildAt(visibleItemCount - 1);
          //          Log.d("Scrolly", "Last item top is " + lastVisibleItem.getTop());
          //          Log.d("Scrolly", "Last item bottom is " + lastVisibleItem.getBottom());
          //          Log.d("Scrolly", "View height is " + view.getHeight());
          if (lastVisibleItem.getBottom() == view.getHeight()) {
            postDelayed(new Runnable() {
              @Override
              public void run() {
                smoothScrollToPositionFromTop(0, 0, SCROLL_TIME_IN_MILLIS_PER_ITEM * totalItemCount);
              }
            }, SCROLL_START_DELAY);
          }
        } else if (firstVisibleItem == 0  && totalItemCount > 1) {
          final int currentOffset = view.getChildAt(0).getTop();
          if (currentOffset == 0) {
            startScrolling();
          }
        }
      }
    });

  }

  public void startScrolling() {
    postDelayed(new Runnable() {
      @Override
      public void run() {
        final int count = getAdapter().getCount();
        smoothScrollToPositionFromTop(count - 1, -500, SCROLL_TIME_IN_MILLIS_PER_ITEM * count);
      }
    }, SCROLL_START_DELAY);
  }

}