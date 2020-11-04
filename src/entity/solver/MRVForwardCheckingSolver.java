package entity.solver;

import entity.ScheduleSlot;
import entity.UniversityClass;
import entity.constraints.AbstractConstraint;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

//Minimum remaining values solver with forward checking
public class MRVForwardCheckingSolver extends ScheduleSolver{

	public MRVForwardCheckingSolver(List<UniversityClass> variables, List<AbstractConstraint> constraints) {
		this.variables = variables;
		this.constraints = constraints;
		this.random = new Random();
		this.initializeNeighbors();
	}

	@Override
	public void solve() {
		//TODO returning back??
		for (int i = 0; i < variables.size(); i++){
			UniversityClass item = variables.get(findVariableIndexWithMinimumRemainingValues());
			assignValue(item);
		}
	}

	protected int findVariableIndexWithMinimumRemainingValues(){
		 return this.variables.stream()
				.min(Comparator.comparingInt(o -> o.getAvailableSlots().size()))
				.map(el -> variables.indexOf(el))
				.orElse(-1);
	}

	@Override
	protected void assignValue(UniversityClass variable) {
		//find random value

		//finding the number of dissatisfied constraints
		int restrictionsFailed = 0;
		for (AbstractConstraint constraint: constraints){
			if(!constraint.canAssign(variable)){
				//TODO. .. . . .
			}
		}
	}

	//delete this slot from remaining variables for all the neighbors for each of them
	//TODO DO NOT FORGET TO MANAGE THIS SITUATION if we return to previous values IN THE FUTURE
	protected void deleteSlotFromAllNeighbors(ScheduleSlot slot){
		variables.forEach(uc -> uc.getNeighbors()
				.forEach(neighbor -> {
					List<ScheduleSlot> filtered = neighbor.getAvailableSlots()
							.stream().filter(availableSlot -> !availableSlot.equals(slot)).collect(Collectors.toList());
					uc.setAvailableSlots(filtered);
				}));
	}




}
