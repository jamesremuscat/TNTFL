package com.corefiling.tntfl.ui.fragment;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.corefiling.tntfl.Player;
import com.corefiling.tntfl.R;
import com.corefiling.tntfl.TableFootballLadder;

public class NameSelectionFragment extends Fragment {

  private NameReceiver _receiver;
  private Player _player;

  public void setPlayer(final Player player) {
    _player = player;
  }

  @Override
  public void onAttach(final Activity activity) {
    super.onAttach(activity);
    try {
      _receiver = (NameReceiver) activity;
    }
    catch (final ClassCastException e) {
      throw new ClassCastException(activity.toString() + " must implement NameReceiver");
    }

  }
  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_name_selection, container, false);

    final Button btnOK = (Button) view.findViewById(R.id.btnOK);
    btnOK.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(final View v) {
        final EditText txtName = (EditText) view.findViewById(R.id.txtName);
        final String playerName = txtName.getText().toString().toLowerCase(Locale.ENGLISH);

        if (!playerName.isEmpty()) {
          final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
          imm.hideSoftInputFromWindow(txtName.getWindowToken(), 0);
          _receiver.setName(_player, playerName);
        }
      }
    });

    final GridView namesGrid = (GridView) view.findViewById(R.id.namesGrid);
    namesGrid.setAdapter(new NameButtonsAdapter(getActivity(), TableFootballLadder.getRecentPlayers(getActivity())));

    return view;
  }

  private class NameButtonsAdapter extends ArrayAdapter<String> {

    public NameButtonsAdapter(final Context context, final List<String> names) {
      super(context, 0, names);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
      final String name = getItem(position);

      final Context context = getContext();
      final Button t = new Button(context);
      t.setText(name);
      t.setTextAppearance(context, android.R.style.TextAppearance_Large);
      t.setTextSize(context.getResources().getDimension(R.dimen.big_font_size));
      t.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(final View v) {
          _receiver.setName(_player, name);
        }
      });
      return t;
    }

  }

  public static interface NameReceiver {
    public void setName(final Player player, final String name);
  }

}
