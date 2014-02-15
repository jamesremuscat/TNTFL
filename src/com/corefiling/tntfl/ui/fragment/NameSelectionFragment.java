package com.corefiling.tntfl.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.corefiling.tntfl.Player;
import com.corefiling.tntfl.R;

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

    final Button btnOK = (Button) view.findViewById(R.id.button1);
    btnOK.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(final View v) {
        final EditText txtName = (EditText) view.findViewById(R.id.txtName);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtName.getWindowToken(), 0);
        _receiver.setName(_player, txtName.getText().toString());
      }
    });

    return view;
  }

  public static interface NameReceiver {
    public void setName(final Player player, final String name);
  }

}
