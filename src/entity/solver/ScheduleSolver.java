package entity.solver;

import entity.UniversityClass;
import entity.constraints.AbstractConstraint;

import java.util.List;

public abstract class ScheduleSolver {

	protected List<UniversityClass> variables;

	protected List<AbstractConstraint> constraints;

	//solve the schedule
	public abstract void solve();

	//heuristics to assign the value to the variable
	protected abstract void assignValue(UniversityClass variable);

	//TODO maybe add returning mechanism
}
