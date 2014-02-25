package com.corefiling.tntfl.ui.fragment;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.corefiling.tntfl.SubmittedGame;
import com.corefiling.tntfl.TableFootballLadder;
import com.corefiling.tntfl.TableFootballLadder.SubmissionException;
import com.corefiling.tntfl.ui.view.RecentGameView;

public class RecentGamesFragment extends SingleLoaderAsyncFragment<List<SubmittedGame>> {

  private ScrollingListView _listView;

  @Override
  public Loader<List<SubmittedGame>> onCreateLoader(final int arg0, final Bundle arg1) {
    return new RecentGamesLoader(getActivity());
  }

  @Override
  public void onLoadFinished(final Loader<List<SubmittedGame>> arg0, final List<SubmittedGame> games) {
    _listView.setAdapter(new RecentGamesListAdapter(getActivity(), games));
    setContentShown(true);
    _listView.startScrolling();
  }

  @Override
  protected View onCreateViewInternal(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    _listView = new ScrollingListView(getActivity());
    return _listView;
  }

  private static class RecentGamesListAdapter extends ArrayAdapter<SubmittedGame> {

    public RecentGamesListAdapter(final Context context, final List<SubmittedGame> objects) {
      super(context, -1, objects);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
      final SubmittedGame game = getItem(position);
      return new RecentGameView(getContext(), game);
    }

  }

  private static class RecentGamesLoader extends AsyncTaskLoader<List<SubmittedGame>> {

    public RecentGamesLoader(final Context context) {
      super(context);
    }

    @Override
    protected void onStartLoading() {
      forceLoad();
    }

    @Override
    public List<SubmittedGame> loadInBackground() {
      try {
        return TableFootballLadder.getRecentGames(getContext());
      }
      catch (final SubmissionException e) {
        return Collections.emptyList();
      }
    }


  }

  private static class ScrollingListView extends ListView {

    private static final int SCROLL_TIME_IN_MILLIS = 15000;

    public ScrollingListView(final Context context) {
      super(context);

      setOnScrollListener(new OnScrollListener() {

        @Override
        public void onScrollStateChanged(final AbsListView view, final int scrollState) {
          // Nothing to do here
        }

        @Override
        public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
          if (firstVisibleItem + visibleItemCount == totalItemCount) {
            post(new Runnable() {
              @Override
              public void run() {
                smoothScrollToPositionFromTop(0, 0, SCROLL_TIME_IN_MILLIS);
              }
            });
          } else if (firstVisibleItem == 0) {
            final int currentOffset   = view.getChildAt(0).getTop();
            if (currentOffset == 0) {
              startScrolling();
            }
          }
        }
      });

    }

    public void startScrolling() {
      post(new Runnable() {
        @Override
        public void run() {
          smoothScrollToPositionFromTop(getAdapter().getCount() - 1, -100, SCROLL_TIME_IN_MILLIS);
        }
      });
    }

  }


}
