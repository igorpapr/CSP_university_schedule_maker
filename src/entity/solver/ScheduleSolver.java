package entity.solver;

import entity.ScheduleSlot;
import entity.UniversityClass;
import entity.constraints.AbstractConstraint;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class ScheduleSolver {

	protected List<UniversityClass> variables;

	protected List<AbstractConstraint> constraints;

	protected Random random;

	//solve the schedule
	public abstract void solve();

	//heuristics to assign the value to the variable
	protected abstract void assignValue(UniversityClass variable);

	protected void initializeNeighbors(){
		if (variables != null){
			for (UniversityClass lesson : variables){
				//finding those lessons, which have at least one common student or have common teacher with the current one
				List<UniversityClass> neighborsToAdd = variables.stream()
						.filter(v -> !lesson.equals(v) &&
								(v.getStudents().stream().anyMatch(lesson.getStudents()::contains)
										|| v.getTeacher().equals(lesson.getTeacher())))
						.collect(Collectors.toList());
				lesson.setNeighbors(neighborsToAdd);
			}
		}
	}

	//TODO maybe add returning mechanism
}
