package com.corefiling.tntfl;

import java.util.Date;

public class SubmittedGame extends Game {

  private float _skillChange;
  private Player _skillChangeDirection;
  private Date _dateTime;

  public float getSkillChange() {
    return _skillChange;
  }
  public void setSkillChange(final float skillChange) {
    _skillChange = skillChange;
  }
  public Player getSkillChangeDirection() {
    return _skillChangeDirection;
  }
  public void setSkillChangeDirection(final Player skillChangeDirection) {
    _skillChangeDirection = skillChangeDirection;
  }
  public Date getDateTime() {
    return _dateTime;
  }
  public void setDateTime(final Date dateTime) {
    _dateTime = dateTime;
  }



}
