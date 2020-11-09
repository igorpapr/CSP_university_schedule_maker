package entity.solver;

import entity.ScheduleSlot;
import entity.UniversityClass;
import entity.constraints.AbstractConstraint;

import java.util.*;

//Minimum remaining values solver with forward checking
public class MRVForwardCheckingSolver extends ForwardCheckingScheduleSolver{

	public MRVForwardCheckingSolver(List<UniversityClass> variables, List<AbstractConstraint> constraints) {
		this.variables = variables;
		this.constraints = constraints;
		this.initializeNeighbors();
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
		int variableIndex = findUnassignedVariableIndexWithMinimumRemainingValues();
		System.out.println("--------------------------------------------------");
		System.out.println("Processing variable with index " + variableIndex);
		if (variableIndex == -1){
			return false;
		}
		UniversityClass variable = variables.get(variableIndex);
		System.out.println("Number of available slots for current variable: " + variable.getAvailableSlots().size());
		for(ScheduleSlot value : variable.getAvailableSlots()){
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

	protected int countFailedConstraints(ScheduleSlot value, UniversityClass variable){
		int constraintsFailed = 0;
		for (AbstractConstraint constraint: constraints){
			if(!constraint.canAssign(variables, variable, value)){
				constraintsFailed++;
			}
		}
		return constraintsFailed;
	}

	protected int findUnassignedVariableIndexWithMinimumRemainingValues(){
		 return this.variables.stream()
				 .filter(x -> !x.isAssigned())
				.min(Comparator.comparingInt(o -> o.getAvailableSlots().size()))
				.map(el -> variables.indexOf(el))
				.orElse(-1);
	}

}
