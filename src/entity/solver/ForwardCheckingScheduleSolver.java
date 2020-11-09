package entity.solver;

import entity.ScheduleSlot;
import entity.UniversityClass;
import entity.constraints.AbstractConstraint;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ForwardCheckingScheduleSolver extends ScheduleSolver {

	public ForwardCheckingScheduleSolver(List<UniversityClass> variables, List<AbstractConstraint> constraints)  {
		this.variables = variables;
		this.constraints = constraints;
		this.initializeNeighbors();
		this.random = new Random();
	}

	/**
	 *  Initializes a neighbors list for every variable with the connected UniversityClasses.
	 *  "Connected" in this context means having common students of teachers.
	 */
	protected void initializeNeighbors(){
		if (variables != null){
			for (UniversityClass lesson : variables){
				//finding those lessons, which have at least one common student
				// or have common teacher with the current one
				List<UniversityClass> neighborsToAdd = variables.stream()
						.filter(v -> !lesson.getUuid().equals(v.getUuid()) &&
								(v.getStudents().stream().anyMatch(lesson.getStudents()::contains)
										|| v.getTeacher().equals(lesson.getTeacher())))
						.collect(Collectors.toList());
				lesson.setNeighbors(neighborsToAdd);
			}
		}
	}

	/**
	 * delete this slot from remaining variables for all the neighbors for each of them
	 **/
	 protected void deleteSimilarSlotsFromAllNeighbors(UniversityClass variable, ScheduleSlot slot){
		variable.getNeighbors()
			.forEach(neighbor -> {
				List<ScheduleSlot> toBeRemoved = neighbor.getAvailableSlots()
						.stream().filter(
								availableSlot -> availableSlot.classTime.equals(slot.classTime)
										&& availableSlot.dayOfTheWeek.equals(slot.dayOfTheWeek))
						.collect(Collectors.toList());
				neighbor.getRemovedByForwardCheckingValuesMap().put(variable.getUuid(), toBeRemoved);
				neighbor.getAvailableSlots().removeAll(toBeRemoved);
			});
	}

	/**
	 * For all neighbors of given variable restores all values
	 * that have been removed with the last filtering of ForwardChecking
	 **/
	 protected void restoreLastRemovedByFCValuesFromAllNeighbors(UniversityClass variable){
		variable.getNeighbors()
			.forEach(neighbor -> {
				List<ScheduleSlot> toBeRestored = neighbor.getRemovedByForwardCheckingValuesMap().get(variable.getUuid());
				if (toBeRestored != null){
					neighbor.getAvailableSlots().addAll(toBeRestored);
					neighbor.getRemovedByForwardCheckingValuesMap().remove(variable.getUuid());
				}else{
					System.err.println("Couldn't restore values from " + neighbor + " by the specified key: " + variable);
				}
			});
	}

	@Override
	public List<UniversityClass> solve() {
	 	boolean res = backtrack();
		if (!res){
			throw new RuntimeException("Couldn't solve the problem. Try fixing initial variables.");
		}
		return variables;
	}

	protected boolean backtrack(){
		if (isAssignmentComplete())
			return true;
		int variableIndex = findIndexOfNextUnassignedVariableToBeProcessed();
		assert variableIndex != -1;

		System.out.println("--------------------------------------------------");
		System.out.println("Processing variable with index " + variableIndex);
		UniversityClass variable = this.variables.get(variableIndex);
		System.out.println("Number of available slots for current variable: " + variable.getAvailableSlots().size());

		while(!variable.getAvailableSlots().isEmpty()){
			ScheduleSlot value = this.getValueToBeAssigned(variable);

			int constraintsFailed = countFailedConstraints(value, variable);
			if (constraintsFailed == 0){
				variable.setScheduleSlot(value);
				System.out.println("Setting slot: " + value);
				deleteSlotFromAll(value);
				deleteSimilarSlotsFromAllNeighbors(variable, value);
				boolean res = backtrack(); //recursive call
				if (res) {
					return true;
				}
				System.out.println("Returned from recursive call with failure, current index: " + variableIndex );
			} else{
				System.out.println("Tried to assign the value: "+value+", but constraints failed: " + constraintsFailed);
				//TODO handling of collisions in the schedule can be added inside this else block,
				// but for our task we avoid this situation due
				// to the low possibility of this situation in the real life and low amount of test data
			}
			//unassign the value
			variable.setScheduleSlot(null);
			restoreSlotForAll(value);
			//delete it from current domain of available values
			variable.removeFromAvailableValues(value);
			restoreLastRemovedByFCValuesFromAllNeighbors(variable);
		}
		return false;
	}

	@Override
	protected ScheduleSlot getValueToBeAssigned(UniversityClass variable) {
	    int index = random.nextInt(variable.getAvailableSlots().size());
	 	return variable.getAvailableSlots().get(index);
	 }
}
