package entity.constraints;

import entity.ScheduleSlot;
import entity.UniversityClass;

import java.util.List;

public class NotSameTeacherConstraint extends AbstractConstraint{

	@Override
	public boolean canAssign(List<UniversityClass> variables, UniversityClass variable, ScheduleSlot value) {
		List<UniversityClass> assignedLessons = this.getAssignedClasses(variables);
		for (UniversityClass lesson: assignedLessons){
			if (slotsOfSameTime(lesson.getScheduleSlot(), value) && lesson.getTeacher().equals(variable.getTeacher())){
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "NotSameTeacherConstraint";
	}
}
