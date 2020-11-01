package entity.constraints;

import entity.ScheduleSlot;
import entity.UniversityClass;

public abstract class AbstractConstraint {

	public abstract boolean canAssign(UniversityClass variable, ScheduleSlot value);

}
