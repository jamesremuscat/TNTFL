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
import android.widget.ListView;

import com.corefiling.tntfl.LadderEntry;
import com.corefiling.tntfl.TableFootballLadder;
import com.corefiling.tntfl.TableFootballLadder.SubmissionException;
import com.corefiling.tntfl.ui.view.PlayerStatsView;

public class LadderFragment extends SingleLoaderAsyncFragment<List<LadderEntry>> {

  private ListView _listView;

  @Override
  public Loader<List<LadderEntry>> onCreateLoader(final int arg0, final Bundle arg1) {
    return new LadderLoader(getActivity());
  }

  @Override
  public void onLoadFinished(final Loader<List<LadderEntry>> arg0, final List<LadderEntry> games) {
    _listView.setAdapter(new RecentGamesListAdapter(getActivity(), games));
    setContentShown(true);
  }

  @Override
  protected View onCreateViewInternal(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    _listView = new ListView(getActivity());
    return _listView;
  }

  private static class RecentGamesListAdapter extends ArrayAdapter<LadderEntry> {

    public RecentGamesListAdapter(final Context context, final List<LadderEntry> objects) {
      super(context, -1, objects);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
      final LadderEntry ps = getItem(position);
      if (convertView instanceof PlayerStatsView) {
        final PlayerStatsView psv = (PlayerStatsView) convertView;
        psv.setPlayerStats(ps);
        psv.setRanking(position + 1);
        return psv;
      }
      return new PlayerStatsView(getContext(), position + 1, ps);
    }

  }

  private static class LadderLoader extends AsyncTaskLoader<List<LadderEntry>> {

    public LadderLoader(final Context context) {
      super(context);
    }

    @Override
    protected void onStartLoading() {
      forceLoad();
    }

    @Override
    public List<LadderEntry> loadInBackground() {
      try {
        return TableFootballLadder.getLadder(getContext());
      }
      catch (final SubmissionException e) {
        return Collections.emptyList();
      }
    }


  }


}
