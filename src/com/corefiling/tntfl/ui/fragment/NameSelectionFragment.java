package com.corefiling.tntfl.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.corefiling.tntfl.R;

public class NameSelectionFragment extends Fragment {

  private NameReceiver _receiver;

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
        final TextView txtName = (TextView) view.findViewById(R.id.txtName);
        _receiver.setName(txtName.getText().toString());
      }
    });

    return view;
  }

  public static interface NameReceiver {
    public void setName(final String name);
  }

}
