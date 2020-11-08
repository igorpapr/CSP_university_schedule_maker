package entity.solver;

import entity.ScheduleSlot;
import entity.UniversityClass;
import entity.constraints.AbstractConstraint;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class ScheduleSolver {

	protected List<UniversityClass> variables;

	protected List<AbstractConstraint> constraints;

	/**
	 * A general function to start solving the problem
	 */
	public abstract void solve();

	/**
	 * Chooses a value to the variable, checking all the constraints. Then assigns it.
		@return - True if the value was successfully assigned
	            - False if there's no available values left for the variable
	 */
	protected abstract boolean assignValue(UniversityClass variable);


	//TODO maybe add returning mechanism

}
