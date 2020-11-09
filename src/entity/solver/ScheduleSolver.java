package entity.solver;

import entity.ScheduleSlot;
import entity.UniversityClass;
import entity.constraints.AbstractConstraint;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ScheduleSolver {

	protected List<UniversityClass> variables;

	protected List<AbstractConstraint> constraints;

	protected Random random;

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

	/**
	 * Finds index of the next variable to be processed.
	 * Can combine a bunch of heuristics.
	 * @return index of the next variable to be processed
	 */
	protected int findIndexOfNextUnassignedVariableToBeProcessed(){
		List<UniversityClass> unassigned = variables.stream().filter(v -> !v.isAssigned()).collect(Collectors.toList());
		//getting random unassigned variable
		UniversityClass item = unassigned.get(random.nextInt(unassigned.size()));

		return this.variables.stream()
				.filter(x -> x.getUuid().equals(item.getUuid()))
				.map(el -> variables.indexOf(el)).findFirst().orElse(-1);
	}

	/**
	 * Counts a number of constraints that will fail if the given value will be assigned to the given variable
	 * @param value value to be assigned
	 * @param variable variable, to which the value is going to be assigned
	 * @return a number of constraints to be failed in case asssigning the given value
	 */
	protected int countFailedConstraints(ScheduleSlot value, UniversityClass variable){
		int constraintsFailed = 0;
		for (AbstractConstraint constraint: constraints){
			if(!constraint.canAssign(variables, variable, value)){
				constraintsFailed++;
			}
		}
		return constraintsFailed;
	}

	/**
	 * Picks value to be assigned to the variable from the list of available values
	 * @param variable
	 * @return found value
	 */
	protected abstract ScheduleSlot getValueToBeAssigned(UniversityClass variable);
}
