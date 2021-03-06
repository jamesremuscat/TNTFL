package com.corefiling.tntfl.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.corefiling.tntfl.TableFootballLadder.SubmissionException;
import com.corefiling.tntfl.ui.activity.ScoreSubmissionActivity;
import com.corefiling.tntfl.ui.fragment.ScoreSubmissionFragment.SubmissionResult;

public class ScoreSubmissionFragment extends SingleLoaderAsyncFragment<SubmissionResult> {

  public static ScoreSubmissionFragment getInstance(final Game game) {
    final ScoreSubmissionFragment f = new ScoreSubmissionFragment();

    final Bundle args = new Bundle();
    args.putParcelable(ScoreSubmissionActivity.BUNDLE_GAME_KEY, game);
    f.setArguments(args);

    return f;
  }

  private Dismissable _parent;

  @Override
  public Loader<SubmissionResult> onCreateLoader(final int arg0, final Bundle arg1) {
    return new ScoreSubmitter((Game) getArguments().getParcelable(ScoreSubmissionActivity.BUNDLE_GAME_KEY), getActivity());
  }

  @SuppressWarnings("deprecation")
  @Override
  public void onLoadFinished(final Loader<SubmissionResult> arg0, final SubmissionResult result) {

    if (result._game != null) {
      final SubmittedGame game = result._game;
      ((TextView) getActivity().findViewById(R.id.txtBlueName)).setText(game.getBluePlayer());
      ((TextView) getActivity().findViewById(R.id.txtBlueScore)).setText(Integer.toString(game.getBlueScore()));
      ((TextView) getActivity().findViewById(R.id.txtRedName)).setText(game.getRedPlayer());
      ((TextView) getActivity().findViewById(R.id.txtRedScore)).setText(Integer.toString(game.getRedScore()));
      final TextView txtSkillChangeRed = (TextView) getActivity().findViewById(R.id.txtSkillChangeRed);
      final TextView txtSkillChangeBlue = (TextView) getActivity().findViewById(R.id.txtSkillChangeBlue);


      if (game.getSkillChangeDirection() == Player.RED) {
        txtSkillChangeRed.setText(String.format("%+.3f", game.getSkillChange()));
        txtSkillChangeBlue.setVisibility(View.INVISIBLE);
      }
      else {
        txtSkillChangeBlue.setText(String.format("%+.3f", game.getSkillChange()));
        txtSkillChangeRed.setVisibility(View.INVISIBLE);
      }

      final TextView txtRankChangeRed = (TextView) getActivity().findViewById(R.id.txtPosChangeRed);
      final TextView txtRankChangeBlue = (TextView) getActivity().findViewById(R.id.txtPosChangeBlue);

      if (game.getRedRankChange() != 0) {
        txtRankChangeRed.setText(String.format("%+d", game.getRedRankChange()));
        txtRankChangeRed.setVisibility(View.VISIBLE);
      }
      else {
        txtRankChangeRed.setVisibility(View.INVISIBLE);
      }

      if (game.getBlueRankChange() != 0) {
        txtRankChangeBlue.setText(String.format("%+d", game.getBlueRankChange()));
        txtRankChangeBlue.setVisibility(View.VISIBLE);
      }
      else {
        txtRankChangeBlue.setVisibility(View.INVISIBLE);
      }

      TableFootballLadder.addRecentPlayer(getActivity(), game.getRedPlayer());
      TableFootballLadder.addRecentPlayer(getActivity(), game.getBluePlayer());

      if (game.getRedScore() == 10 && game.getBlueScore() == 0 || game.getRedScore() == 0 && game.getBlueScore() == 10) {
        //        final Resources resources = getActivity().getResources();
        //        final Drawable baseState = resources.getDrawable(R.drawable.red_blue_gradient);
        //        final Drawable middleState = new GradientDrawable((game.getRedScore() == 10) ? Orientation.LEFT_RIGHT : Orientation.RIGHT_LEFT, new int[] {Color.YELLOW, Color.BLACK});
        //        final TransitionDrawable td = new TransitionDrawable(new Drawable[] { baseState, middleState });
        //        getActivity().findViewById(R.id.scoresBox).setBackgroundDrawable(td);
        //        td.startTransition(2000);
      }

      setContentShown(true);
    }
    else {
      final Intent intent = new Intent("com.corefiling.ScoreSubmission");
      intent.putExtra("exception", result._exception);
      getActivity().setResult(Activity.RESULT_CANCELED, intent);
      getActivity().finish();
    }
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

  private static class ScoreSubmitter extends AsyncTaskLoader<SubmissionResult> {

    private final Game _game;

    public ScoreSubmitter(final Game game, final Context context) {
      super(context);
      _game = game;
    }

    @Override
    public SubmissionResult loadInBackground() {
      final SubmissionResult result = new SubmissionResult();
      try {
        result._game = TableFootballLadder.submitGame(getContext(), _game);
      }
      catch (final SubmissionException e) {
        result._exception = e;
      }

      return result;
    }

    @Override
    protected void onStartLoading() {
      forceLoad();
    }

  }

  public static class SubmissionResult {
    public SubmittedGame _game;
    public Exception _exception;
  }

  public static interface Dismissable {
    public void dismiss();
  }

}
