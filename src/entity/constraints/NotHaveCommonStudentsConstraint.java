package entity.constraints;

import entity.ScheduleSlot;
import entity.UniversityClass;

import java.util.List;

public class NotHaveCommonStudentsConstraint extends AbstractConstraint{

	@Override
	public boolean canAssign(List<UniversityClass> variables, UniversityClass variable, ScheduleSlot value) {
		List<UniversityClass> assignedLessons = this.getAssignedClasses(variables);
		for (UniversityClass lesson: assignedLessons){
			if (slotsOfSameTime(lesson.getScheduleSlot(), value) &&
					//have at least one common student
					lesson.getStudents().stream().anyMatch(variable.getStudents()::contains)){
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "NotHaveCommonStudentsConstraint";
	}
}
