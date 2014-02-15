package com.corefiling.tntfl.ui.fragment;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.corefiling.tntfl.Game;
import com.corefiling.tntfl.Player;
import com.corefiling.tntfl.R;
import com.corefiling.tntfl.SubmittedGame;
import com.corefiling.tntfl.TableFootballLadder;
import com.corefiling.tntfl.ui.activity.ScoreSubmissionActivity;

public class ScoreSubmissionFragment extends SingleLoaderAsyncFragment<SubmittedGame> {

  public static ScoreSubmissionFragment getInstance(final Game game) {
    final ScoreSubmissionFragment f = new ScoreSubmissionFragment();

    final Bundle args = new Bundle();
    args.putParcelable(ScoreSubmissionActivity.BUNDLE_GAME_KEY, game);
    f.setArguments(args);

    return f;
  }

  private Dismissable _parent;

  @Override
  public Loader<SubmittedGame> onCreateLoader(final int arg0, final Bundle arg1) {
    return new ScoreSubmitter((Game) getArguments().getParcelable(ScoreSubmissionActivity.BUNDLE_GAME_KEY), getActivity());
  }

  @Override
  public void onLoadFinished(final Loader<SubmittedGame> arg0, final SubmittedGame game) {
    ((TextView) getActivity().findViewById(R.id.txtBlueName)).setText(game.getBluePlayer());
    ((TextView) getActivity().findViewById(R.id.txtBlueScore)).setText(Integer.toString(game.getBlueScore()));
    ((TextView) getActivity().findViewById(R.id.txtRedName)).setText(game.getRedPlayer());
    ((TextView) getActivity().findViewById(R.id.txtRedScore)).setText(Integer.toString(game.getRedScore()));
    final TextView txtSkillChange = (TextView) getActivity().findViewById(R.id.txtSkillChange);
    txtSkillChange.setText(Double.toString(game.getSkillChange()));

    if (game.getSkillChangeDirection() == Player.RED) {
      txtSkillChange.setBackgroundResource(R.drawable.red_gradient);
    }
    else {
      txtSkillChange.setBackgroundResource(R.drawable.blue_gradient);
    }

    TableFootballLadder.addRecentPlayer(getActivity(), game.getRedPlayer());
    TableFootballLadder.addRecentPlayer(getActivity(), game.getBluePlayer());

    setContentShown(true);
  }

  @Override
  protected View onCreateViewInternal(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_score_submit, container, false);

    ((Button) view.findViewById(R.id.btnOK)).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(final View v) {
        _parent.dismiss();
      }
    });

    return view;
  }

  @Override
  public void onAttach(final Activity activity) {
    super.onAttach(activity);
    try {
      _parent = (Dismissable) activity;
    }
    catch (final ClassCastException e) {
      throw new ClassCastException(activity.toString() + " must implement Dismissable");
    }
  }

  private static class ScoreSubmitter extends AsyncTaskLoader<SubmittedGame> {

    private final Game _game;

    public ScoreSubmitter(final Game game, final Context context) {
      super(context);
      _game = game;
    }

    @Override
    public SubmittedGame loadInBackground() {
      // here we need to use _game to make the GET request to the ladder, then parse the JSON response.
      // TODO actually do that

      final SubmittedGame g = new SubmittedGame();
      g.setRedPlayer(_game.getRedPlayer());
      g.setRedScore(_game.getRedScore());
      g.setBluePlayer(_game.getBluePlayer());
      g.setBlueScore(_game.getBlueScore());
      g.setDateTime(new Date());
      g.setSkillChange(1138);
      g.setSkillChangeDirection(Player.BLUE);

      return g;
    }

    @Override
    protected void onStartLoading() {
      forceLoad();
    }

  }

  public static interface Dismissable {
    public void dismiss();
  }

}