package com.corefiling.tntfl.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.corefiling.tntfl.Player;
import com.corefiling.tntfl.R;

public class NameAndScoreFragment extends Fragment {

  private Player _player;
  private String _name;
  private int _score;
  private RetryReceiver _receiver;

  public static NameAndScoreFragment getInstance(final Player player, final String name, final int score) {
    final NameAndScoreFragment nasf = new NameAndScoreFragment();
    nasf.setPlayer(player);
    nasf.setName(name);
    nasf.setScore(score);
    return nasf;
  }

  @Override
  public void onAttach(final Activity activity) {
    super.onAttach(activity);
    try {
      _receiver = (RetryReceiver) activity;
    }
    catch (final ClassCastException e) {
      throw new ClassCastException(activity.toString() + " must implement RetryReceiver");
    }
  }

  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_name_and_score, container, false);

    final Button btnEnterAgain = (Button) view.findViewById(R.id.btnEnterAgain);
    btnEnterAgain.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View v) {
        _receiver.retry(_player);
      }
    });

    ((TextView) view.findViewById(R.id.lblName)).setText(_name);
    ((TextView) view.findViewById(R.id.lblScore)).setText(Integer.toString(_score));

    return view;
  }

  public void setPlayer(final Player player) {
    _player = player;
  }

  public void setName(final String name) {
    _name = name;
  }

  public void setScore(final int score) {
    _score = score;
  }

  public static interface RetryReceiver {
    public void retry(final Player player);
  }
}
