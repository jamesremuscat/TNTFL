package com.corefiling.tntfl.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * Based on http://stackoverflow.com/a/14783152/11643
 */
public abstract class AsyncFragment extends Fragment {

  public static final int INTERNAL_PROGRESS_CONTAINER_ID = 0x00ff0001; // public for use in testing
  private static final int INTERNAL_CONTENT_CONTAINER_ID = 0x00ff0002;

  private View mProgressContainer;
  private View mContentContainer;
  private boolean mContentShown = true;

  @Override
  public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
      final Bundle savedInstanceState) {
    final Context context = getActivity();

    final FrameLayout root = new FrameLayout(context);

    // ------------------------------------------------------------------

    final LinearLayout pframe = new LinearLayout(context);
    pframe.setId(INTERNAL_PROGRESS_CONTAINER_ID);
    pframe.setOrientation(LinearLayout.VERTICAL);
    pframe.setVisibility(View.GONE);
    pframe.setGravity(Gravity.CENTER);

    final ProgressBar progress = new ProgressBar(context, null,
        android.R.attr.progressBarStyleLarge);
    pframe.addView(progress, new FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    root.addView(pframe, new FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    // ------------------------------------------------------------------

    final FrameLayout lframe = new FrameLayout(context);
    lframe.setId(INTERNAL_CONTENT_CONTAINER_ID);

    root.addView(lframe, new FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    // ------------------------------------------------------------------

    root.setLayoutParams(new FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    lframe.addView(onCreateViewInternal(inflater, container, savedInstanceState), new FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    return root;
  }

  protected abstract View onCreateViewInternal(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState);

  public void setView(final View v) {

    final FrameLayout lframe = (FrameLayout) getView().findViewById(INTERNAL_CONTENT_CONTAINER_ID);
    lframe.addView(v, new FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

  }

  @Override
  public void onViewCreated(final View view, final Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mProgressContainer = getView().findViewById(INTERNAL_PROGRESS_CONTAINER_ID);
    mContentContainer = getView().findViewById(INTERNAL_CONTENT_CONTAINER_ID);
    setContentShown(false, false);
  }

  @Override
  public void onDestroyView() {
    mContentShown = false;
    mProgressContainer = mContentContainer = null;
    super.onDestroyView();
  }


  public void setContentShown(final boolean shown) {
    setContentShown(shown, true);
  }

  public void setContentShownNoAnimation(final boolean shown) {
    setContentShown(shown, false);
  }

  private void setContentShown(final boolean shown, final boolean animate) {

    mProgressContainer = getView().findViewById(INTERNAL_PROGRESS_CONTAINER_ID);
    mContentContainer = getView().findViewById(INTERNAL_CONTENT_CONTAINER_ID);

    if (mContentShown == shown) {
      return;
    }
    mContentShown = shown;
    if (shown) {
      if (animate) {
        mProgressContainer.startAnimation(AnimationUtils.loadAnimation(
            getActivity(), android.R.anim.fade_out));
        mContentContainer.startAnimation(AnimationUtils.loadAnimation(
            getActivity(), android.R.anim.fade_in));
      } else {
        mProgressContainer.clearAnimation();
        mContentContainer.clearAnimation();
      }
      mProgressContainer.setVisibility(View.GONE);
      mContentContainer.setVisibility(View.VISIBLE);
    } else {
      if (animate) {
        mProgressContainer.startAnimation(AnimationUtils.loadAnimation(
            getActivity(), android.R.anim.fade_in));
        mContentContainer.startAnimation(AnimationUtils.loadAnimation(
            getActivity(), android.R.anim.fade_out));
      } else {
        mProgressContainer.clearAnimation();
        mContentContainer.clearAnimation();
      }
      mProgressContainer.setVisibility(View.VISIBLE);
      mContentContainer.setVisibility(View.GONE);
    }
  }
}