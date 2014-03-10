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
import android.widget.LinearLayout;

import com.corefiling.tntfl.SubmittedGame;
import com.corefiling.tntfl.TableFootballLadder;
import com.corefiling.tntfl.TableFootballLadder.SubmissionException;
import com.corefiling.tntfl.ui.view.AutoScrollingView;
import com.corefiling.tntfl.ui.view.RecentGameView;

public class RecentGamesFragment extends SingleLoaderAsyncFragment<List<SubmittedGame>> {

  private AutoScrollingView _scrollView;

  @Override
  public Loader<List<SubmittedGame>> onCreateLoader(final int arg0, final Bundle arg1) {
    return new RecentGamesLoader(getActivity());
  }

  @Override
  public void onLoadFinished(final Loader<List<SubmittedGame>> arg0, final List<SubmittedGame> games) {

    final LinearLayout ll = new LinearLayout(getActivity());
    ll.setOrientation(LinearLayout.VERTICAL);

    for (final SubmittedGame g : games) {
      ll.addView(new RecentGameView(getActivity(), g));
    }

    _scrollView.addView(ll);
    setContentShown(true);
    _scrollView.startScrolling();
  }

  @Override
  protected View onCreateViewInternal(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    _scrollView = new AutoScrollingView(getActivity());
    return _scrollView;
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


}
