package entity.constraints;

import entity.ScheduleSlot;
import entity.UniversityClass;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractConstraint {

	public abstract boolean canAssign(List<UniversityClass> variables, UniversityClass variable, ScheduleSlot value);

	protected boolean slotsOfSameTime(ScheduleSlot a, ScheduleSlot b){
		return a.dayOfTheWeek.equals(b.dayOfTheWeek) && a.classTime.equals(b.classTime);
	}

	protected List<UniversityClass> getAssignedClasses(List<UniversityClass> variables){
		return variables.stream()
				.filter(UniversityClass::isAssigned).collect(Collectors.toList());
	}
}
