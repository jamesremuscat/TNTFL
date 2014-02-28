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
import android.widget.ArrayAdapter;

import com.corefiling.tntfl.SubmittedGame;
import com.corefiling.tntfl.TableFootballLadder;
import com.corefiling.tntfl.TableFootballLadder.SubmissionException;
import com.corefiling.tntfl.ui.view.RecentGameView;
import com.corefiling.tntfl.ui.view.ScrollingListView;

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
      if (convertView instanceof RecentGameView) {
        ((RecentGameView) convertView).setGame(game);
        return convertView;
      }
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


}
