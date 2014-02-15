package com.corefiling.tntfl.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.corefiling.tntfl.Player;
import com.corefiling.tntfl.R;

public class ScoreSelectionFragment extends Fragment {

  private ScoreReceiver _receiver;

  private Player _player = Player.RED;

  public void setPlayer(final Player player) {
    _player = player;
  }

  @Override
  public void onAttach(final Activity activity) {
    super.onAttach(activity);
    try {
      _receiver = (ScoreReceiver) activity;
    }
    catch (final ClassCastException e) {
      throw new ClassCastException(activity.toString() + " must implement ScoreReceiver");
    }
  }

  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_score_selection, container, false);
    ((Button) view.findViewById(R.id.button0)).setOnClickListener(new ScoreButtonListener(0));
    ((Button) view.findViewById(R.id.button1)).setOnClickListener(new ScoreButtonListener(1));
    ((Button) view.findViewById(R.id.button2)).setOnClickListener(new ScoreButtonListener(2));
    ((Button) view.findViewById(R.id.button3)).setOnClickListener(new ScoreButtonListener(3));
    ((Button) view.findViewById(R.id.button4)).setOnClickListener(new ScoreButtonListener(4));
    ((Button) view.findViewById(R.id.button5)).setOnClickListener(new ScoreButtonListener(5));
    ((Button) view.findViewById(R.id.button6)).setOnClickListener(new ScoreButtonListener(6));
    ((Button) view.findViewById(R.id.button7)).setOnClickListener(new ScoreButtonListener(7));
    ((Button) view.findViewById(R.id.button8)).setOnClickListener(new ScoreButtonListener(8));
    ((Button) view.findViewById(R.id.button9)).setOnClickListener(new ScoreButtonListener(9));
    ((Button) view.findViewById(R.id.button10)).setOnClickListener(new ScoreButtonListener(10));
    return view;
  }

  private class ScoreButtonListener implements View.OnClickListener {

    private final int _value;

    public ScoreButtonListener(final int score) {
      _value = score;
    }

    @Override
    public void onClick(final View v) {
      _receiver.setScore(_player, _value);
    }

  }

  public static interface ScoreReceiver {
    public void setScore(final Player player, final int score);
  }

}
