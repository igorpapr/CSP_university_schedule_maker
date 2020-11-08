package entity.solver;

import entity.ScheduleSlot;
import entity.UniversityClass;
import entity.constraints.AbstractConstraint;

import java.util.*;
import java.util.stream.Collectors;

//Minimum remaining values solver with forward checking
public class MRVForwardCheckingSolver extends ForwardCheckingScheduleSolver{

	public MRVForwardCheckingSolver(List<UniversityClass> variables, List<AbstractConstraint> constraints) {
		this.variables = variables;
		this.constraints = constraints;
		this.initializeNeighbors();
	}

	@Override
	public void solve() {
		//TODO returning back?? mb stack or smth
		for (int i = 0; i < variables.size(); i++){
			int index = findVariableIndexWithMinimumRemainingValues();
			if (index == -1){
				break;
			}
			UniversityClass item = variables.get(index);
			if (item != null){
				assignValue(item);
			}
			else{ //TODO fix this shit
				throw new RuntimeException("Null pointer");
			}
		}
		System.out.println("END");
	}

	protected int findVariableIndexWithMinimumRemainingValues(){
		 return this.variables.stream()
				 .filter(x -> !x.isAssigned())
				.min(Comparator.comparingInt(o -> o.getAvailableSlots().size()))
				.map(el -> variables.indexOf(el))
				.orElse(-1);
	}

	@Override
	protected boolean assignValue(UniversityClass variable) {
		boolean passedAllConstraints;
		for (int i = 0; i < variable.getAvailableSlots().size(); i++){
			ScheduleSlot value = variable.getAvailableSlots().get(i);
			//finding the number of dissatisfied constraints //TODO maybe use it in future for the case
						                                     //TODO where you can't find the schedule without collisions
			//int restrictionsFailed = 0;
			passedAllConstraints = true;
			for (AbstractConstraint constraint: constraints){
				if(!constraint.canAssign(variables, variable, value)){
					passedAllConstraints = false;
				}
			}
			if (passedAllConstraints){
				variable.setScheduleSlot(value);
				deleteSlotFromAllNeighbors(variable, value);
				return true;
			}
		}
		return false; 	//TODO RETURNING BACK BECAUSE NO AVAILABLE VALUES LEFT
	}
}
