package com.corefiling.tntfl.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

public abstract class SingleLoaderAsyncFragment<T> extends AsyncFragment implements LoaderManager.LoaderCallbacks<T> {

  @Override
  public void onActivityCreated(final Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    final LoaderManager loaderManager = getLoaderManager();
    loaderManager.initLoader(0, null, this);
  }

  @Override
  public void onLoaderReset(final Loader<T> arg0) {
    // Do nothing
  }
}