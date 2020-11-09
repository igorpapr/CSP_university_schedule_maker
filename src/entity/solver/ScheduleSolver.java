package entity.solver;

import entity.ScheduleSlot;
import entity.UniversityClass;
import entity.constraints.AbstractConstraint;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ScheduleSolver {

	protected List<UniversityClass> variables;

	protected List<AbstractConstraint> constraints;

	/**
	 * A general function to solve the problem
	 */
	public abstract List<UniversityClass> solve();

	/**
	 * A helper recursive function that assigns a value to the variable and does all needed checkings.
	 * Implements backtracking algorithm.
	 * Iterates through all available values for the current variable and checks if it's available.
	 * If it is, this function is called by recursion.
	 * If the call from the inner call returned false, then it tries another value.
	 * @return - True if the assignment is complete, or the inner call of this function returned true.
	 *         - False if the variable has no available values left.
	 */
	protected abstract boolean backtrack();

	/**
	 * Deletes the value from all other variables since it is already assigned to another variable.
	 * This method is used due to the given task. In the regular CSP implementation, this approach usually can not be used.
	 * @param value - a value to be deleted
	 */
	protected void deleteSlotFromAll(ScheduleSlot value){
		this.variables.forEach(
			v -> v.setAvailableSlots(
					v.getAvailableSlots().stream().filter(
							s -> !s.equals(value)).collect(Collectors.toList())
			)
		);
	}

	/**
	 * Restores the value deleted before.
	 * @param value - a value to be restored
	 */
	protected void restoreSlotForAll(ScheduleSlot value){
		this.variables.forEach(v -> v.getAvailableSlots().add(value));
	}

	/**
	 * Checks if the asssignment is complete. It means the end of the algorithm.
	 * @return True if the assignment is complete.
	 */
	protected boolean isAssignmentComplete(){
		for (UniversityClass variable: variables){
			if (!variable.isAssigned()){
				return false;
			}
		}
		return true;
	}

}
