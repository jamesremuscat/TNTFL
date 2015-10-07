package com.corefiling.tntfl;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable {

  private String _redPlayer;
  private int _redScore;
  private String _bluePlayer;
  private int _blueScore;

  public Game() {
    // no-args constructor is what we use most of the time
  }

  public Game(final Parcel in) {
    _redPlayer = in.readString();
    _redScore = in.readInt();
    _bluePlayer = in.readString();
    _blueScore = in.readInt();
  }

  public String getRedPlayer() {
    return _redPlayer;
  }
  public void setRedPlayer(final String redPlayer) {
    _redPlayer = redPlayer;
  }
  public int getRedScore() {
    return _redScore;
  }
  public void setRedScore(final int redScore) {
    _redScore = redScore;
  }
  public String getBluePlayer() {
    return _bluePlayer;
  }
  public void setBluePlayer(final String bluePlayer) {
    _bluePlayer = bluePlayer;
  }
  public int getBlueScore() {
    return _blueScore;
  }
  public void setBlueScore(final int blueScore) {
    _blueScore = blueScore;
  }

  @Override
  public String toString() {
    return _redPlayer + " " + _redScore + "-" + _blueScore + " " + _bluePlayer;
  }

  @Override
  public int describeContents() {
    return 0;
  }
  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    dest.writeString(_redPlayer);
    dest.writeInt(_redScore);
    dest.writeString(_bluePlayer);
    dest.writeInt(_blueScore);
  }

  public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
    @Override
    public Game createFromParcel(final Parcel in) {
      return new Game(in);
    }

    @Override
    public Game[] newArray(final int size) {
      return new Game[size];
    }
  };



}
